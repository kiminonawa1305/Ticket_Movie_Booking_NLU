package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.service.auth.sign_up.impl.SignUpServiceImpl;
import com.lamnguyen.ticket_movie_nlu.service.UserService.UserService;
import com.lamnguyen.ticket_movie_nlu.service.UserService.impl.UserServiceImpl;
import com.lamnguyen.ticket_movie_nlu.bean.User;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.view.activities.SignActivity;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    private TextView tvChangeFragment;
    private FragmentManager fragmentManager;
    private EditText edtEmail, edtPassword, edtRePassword;
    private Button btnSignUp;
    private User user;
    private Dialog dialog;
    private ThreadCallBackSign callBack;

    private final static String EMAIL_ARG = "email",
            PASSWORD_ARG = "password";
    private String email, password, rePassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        fragmentManager = getParentFragmentManager();
        init(view);
        event();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        Log.d(SignUpFragment.class.getName(), "onStart: " + bundle);
        if (bundle == null) return;

        email = bundle.getString(EMAIL_ARG);
        password = bundle.getString(PASSWORD_ARG);
        rePassword = bundle.getString(PASSWORD_ARG);

        edtEmail.setText(email);
        edtPassword.setText(password);
        edtRePassword.setText(rePassword);
    }

    private void init(View view) {
        tvChangeFragment = view.findViewById(R.id.text_view_change_fragment_sign_in);
        edtEmail = view.findViewById(R.id.edit_text_sign_up_email);
        edtPassword = view.findViewById(R.id.edit_text_sign_up_password);
        edtRePassword = view.findViewById(R.id.edit_text_sign_up_re_password);
        btnSignUp = view.findViewById(R.id.button_sign_up);

        callBack = new ThreadCallBackSign() {
            @Override
            public void isSuccess() {
                dialog.dismiss();
                Toast.makeText(SignUpFragment.this.getContext(), getString(R.string.request_verify_account), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void isFail() {
                dialog.dismiss();
                Toast.makeText(SignUpFragment.this.getContext(), getString(R.string.send_mail_verify_fail), Toast.LENGTH_SHORT).show();
            }
        };

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
    }


    private void event() {
        tvChangeFragment.setOnClickListener(v -> {
            changeFragmentSignIn();
        });


        btnSignUp.setOnClickListener(v -> {
            signUp();
        });
    }

    private void signUp() {
        UserService userService = UserServiceImpl.getInstance();
        User user = getUser();
        rePassword = edtRePassword.getText().toString();
        if (user.getEmail().isEmpty()) {
            edtEmail.setError(getString(R.string.request_email));
            return;
        }

        if (!Pattern.matches(SignActivity.EMAIL_PATTERN, user.getEmail())) {
            edtEmail.setError(getString(R.string.error_validate_email));
            return;
        }

        int resultMatchPassword = userService.matchPassword(getUser().getPassword(), rePassword);
        switch (resultMatchPassword) {
            case UserService.EMPTY_PASSWORD: {
                edtPassword.setError(getString(R.string.request_password));
                break;
            }
            case UserService.EMPTY_RE_PASSWORD: {
                edtPassword.setError(getString(R.string.request_re_password));
                break;
            }
            case UserService.NOT_MATCH: {
                edtPassword.setError(getString(R.string.not_match_password));
                edtRePassword.setError(getString(R.string.not_match_password));
                break;
            }
            case UserService.MATCH: {
                DialogLoading.showDialogLoading(dialog, getString(R.string.sign_up));
                SignUpServiceImpl signUpService = SignUpServiceImpl.getInstance();
                signUpService.signUp(getUser(), new ThreadCallBackSign() {
                    @Override
                    public void isSuccess() {
                        dialog.dismiss();
//                        verifyHandle();
                        Toast.makeText(SignUpFragment.this.getContext(), getString(R.string.sign_up_success), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void isFail() {
                        dialog.dismiss();
                        Toast.makeText(SignUpFragment.this.getContext(), getString(R.string.email_is_exist), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


    private void changeFragmentSignIn() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        fragmentTransaction.replace(R.id.fragment_sign, SignInFragment.class, null);
        fragmentTransaction.commit();
    }


    private void verifyHandle() {
        SignUpServiceImpl signUpService = SignUpServiceImpl.getInstance();

        signUpService.verify(getUser(), callBack);
    }

    private User getUser() {
        return initUser();
    }

    private User initUser() {
        if (user != null) return user;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}