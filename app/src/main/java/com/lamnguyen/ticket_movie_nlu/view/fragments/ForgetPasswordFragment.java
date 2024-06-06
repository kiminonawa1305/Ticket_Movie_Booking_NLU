package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.service.auth.forget_password.impl.ForgetPasswordServiceImpl;
import com.lamnguyen.ticket_movie_nlu.view.activities.SignActivity;

import java.util.regex.Pattern;

public class ForgetPasswordFragment extends Fragment {
    private TextView tvChangeFragmentSignIn;
    private FragmentManager fragmentManager;
    private EditText edtEmail;
    private Button btnForgetPassword;
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        init(view);
        event();
        return view;
    }

    private void init(View view) {
        fragmentManager = getParentFragmentManager();
        tvChangeFragmentSignIn = view.findViewById(R.id.text_view_change_fragment_sign_in_form_fragment_forget_password);
        btnForgetPassword = view.findViewById(R.id.button_forget_password);
        edtEmail = view.findViewById(R.id.edit_text_forget_password_email);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
    }

    private void event() {
        this.tvChangeFragmentSignIn.setOnClickListener(v -> {
            changeFragmentSignIn();
        });


        btnForgetPassword.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            if (email.isEmpty()) {
                edtEmail.setError(getString(R.string.request_email));
                return;
            }

            if (!Pattern.matches(SignActivity.EMAIL_PATTERN, email)) {
                edtEmail.setError(getString(R.string.error_validate_email));
                return;
            }

            DialogLoading.showDialogLoading(dialog, "Gửi mail...");
            ForgetPasswordServiceImpl.getInstance().sendForgetPassword(email,
                    new ThreadCallBackSign() {
                        @Override
                        public void isSuccess() {
                            dialog.dismiss();
                            Toast.makeText(ForgetPasswordFragment.this.getContext(), "Vui lòng kiểm tra mail...", Toast.LENGTH_LONG).show();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.setReorderingAllowed(true);

                            // Replace whatever is in the fragment_container view with this fragment
                            fragmentTransaction.replace(R.id.fragment_sign, SignInFragment.class, null);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void isFail() {
                            Toast.makeText(ForgetPasswordFragment.this.getContext(), getString(R.string.email_not_register), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
        });
    }

    private void changeFragmentSignIn() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        fragmentTransaction.replace(R.id.fragment_sign, SignInFragment.class, null);
        fragmentTransaction.commit();
    }
}