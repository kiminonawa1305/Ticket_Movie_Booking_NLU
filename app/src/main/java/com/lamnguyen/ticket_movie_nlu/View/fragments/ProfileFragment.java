package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.model.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.model.utils.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.service.UserService.UserService;
import com.lamnguyen.ticket_movie_nlu.service.UserService.impl.UserServiceImpl;
import com.lamnguyen.ticket_movie_nlu.service.auth.change_password.ChangePasswordService;
import com.lamnguyen.ticket_movie_nlu.service.auth.change_password.impl.ChangePasswordServiceImpl;
import com.lamnguyen.ticket_movie_nlu.view.activities.SignActivity;

public class ProfileFragment extends Fragment {
    private Button btnChangePassword, btnSetting, btnInformation, btnSignOut, btnSubmitChangePassword;
    private TextView edtNewPassword, edtReNewPassword;
    private Dialog dlgChangePassword;
    private DialogLoading dialogLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        this.init(view);
        this.event();
        return view;
    }

    private void init(View view) {
        btnInformation = view.findViewById(R.id.button_infomaton);
        btnChangePassword = view.findViewById(R.id.button_change_pasword);
        btnSetting = view.findViewById(R.id.button_setting);
        btnSignOut = view.findViewById(R.id.button_sign_out);


        dialogLoading = DialogLoading.getInstance(getActivity());
    }

    private Dialog getInstanceDialogChangePassword() {
        if (dlgChangePassword == null) {
            dlgChangePassword = new Dialog(this.getContext());
            dlgChangePassword.setContentView(R.layout.dialog_change_password);
            initViewInDialogChangePassword();
            btnSubmitChangePassword.setOnClickListener(view -> {
                eventChangePassword();
            });
        }

        return dlgChangePassword;
    }

    private void event() {
        btnChangePassword.setOnClickListener(v -> {
            getInstanceDialogChangePassword().show();
        });

        btnSignOut.setOnClickListener(v -> {
            eventSignOut();
        });
    }

    private void eventSignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), SignActivity.class);
        this.startActivity(intent);
        getActivity().finish();
    }

    private void eventChangePassword() {
        String password = edtNewPassword.getText().toString();
        String rePassword = edtNewPassword.getText().toString();

        UserService service = UserServiceImpl.getInstance();
        int result = service.matchPassword(password, rePassword);
        switch (result) {
            case UserService.EMPTY_PASSWORD: {
                edtNewPassword.setError(getString(R.string.request_password));
                break;
            }
            case UserService.EMPTY_RE_PASSWORD: {
                edtNewPassword.setError(getString(R.string.request_re_password));
                break;
            }
            case UserService.NOT_MATCH: {
                edtNewPassword.setError(getString(R.string.not_match_password));
                edtNewPassword.setError(getString(R.string.not_match_password));
                break;
            }
            case UserService.MATCH: {
                dialogLoading.showDialogLoading(getString(R.string.sign_up));
                ChangePasswordService changePasswordService = ChangePasswordServiceImpl.getInstance();

                changePasswordService.changePassword(password, new ThreadCallBackSign() {
                    @Override
                    public void isSuccess() {
                        dialogLoading.dismissDialogLoading();
                        Toast.makeText(getContext(), getString(R.string.change_password_success), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void isFail() {
                        dialogLoading.dismissDialogLoading();
                        Toast.makeText(getContext(), getString(R.string.change_password_fail), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void initViewInDialogChangePassword() {
        edtNewPassword = dlgChangePassword.findViewById(R.id.edit_text_new_password);
        edtReNewPassword = dlgChangePassword.findViewById(R.id.edit_text_re_new_password);
        btnChangePassword = dlgChangePassword.findViewById(R.id.button_submit_change_pasword);
    }
}