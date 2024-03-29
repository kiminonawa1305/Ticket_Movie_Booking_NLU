package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.content.Intent;
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

import com.lamnguyen.ticket_movie_nlu.model.bean.User;
import com.lamnguyen.ticket_movie_nlu.model.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.service.auth.check_mail.CheckEmailService;
import com.lamnguyen.ticket_movie_nlu.service.auth.check_mail.impl.CheckEmailServiceImpl;
import com.lamnguyen.ticket_movie_nlu.service.auth.sign_in.SignInService;
import com.lamnguyen.ticket_movie_nlu.service.auth.sign_in.impl.SignInServiceImpl;
import com.lamnguyen.ticket_movie_nlu.model.utils.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.service.auth.sign_up.impl.SignUpServiceImpl;
import com.lamnguyen.ticket_movie_nlu.view.activities.MainActivity;
import com.lamnguyen.ticket_movie_nlu.view.activities.SignActivity;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    private TextView tvChangeFragmentSignUp, tvChangeFragmentForgetPassword;
    private FragmentManager fragmentManager;
    private EditText edtEmail, edtPassword;
    private Button btnSignIn;
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init(view);
        event();
        return view;
    }

    private void init(View view) {
        fragmentManager = getParentFragmentManager();
        this.edtEmail = view.findViewById(R.id.edit_text_sign_in_email);
        this.edtPassword = view.findViewById(R.id.edit_text_sign_in_password);
        this.btnSignIn = view.findViewById(R.id.button_sign_in);
        tvChangeFragmentSignUp = view.findViewById(R.id.text_view_change_fragment_sign_up);
        this.tvChangeFragmentForgetPassword = view.findViewById(R.id.text_view_change_fragment_forget_password);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
    }

    private void event() {
        tvChangeFragmentSignUp.setOnClickListener(v -> {
            changeFragmentSignUp();
        });

        tvChangeFragmentForgetPassword.setOnClickListener(v -> {
            changeFragmentForgetPassword();
        });
        this.btnSignIn.setOnClickListener(v -> {
            checkEmailHandler();
        });
    }


    private void changeFragmentSignUp() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        fragmentTransaction.replace(R.id.fragment_sign, SignUpFragment.class, null);
        fragmentTransaction.addToBackStack(this.getClass().getName());
        fragmentTransaction.commit();
    }

    private void changeFragmentForgetPassword() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        fragmentTransaction.replace(R.id.fragment_sign, ForgetPasswordFragment.class, null);
        fragmentTransaction.addToBackStack(this.getClass().getName());
        fragmentTransaction.commit();
    }


    private void signInHandler() {
        String email = this.edtEmail.getText().toString();
        String password = this.edtPassword.getText().toString();

        if (!isValidate(email, password)) return;
        DialogLoading.showDialogLoading(dialog, getString(R.string.sign_in));
        SignInService signInService = SignInServiceImpl.getInstance();
        signInService.signIn(email, password, new ThreadCallBackSign() {
            @Override
            public void isSuccess() {
                dialog.dismiss();
                signInSuccess();
            }

            @Override
            public void isFail() {
                dialog.dismiss();
                Toast.makeText(SignInFragment.this.getContext(), getString(R.string.sign_in_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkEmailHandler() {
        String email = this.edtEmail.getText().toString();
        CheckEmailService checkEmailService = CheckEmailServiceImpl.getInstance();
        checkEmailService.checkEmail(email, new ThreadCallBackSign() {
            @Override
            public void isSuccess() {
                signInHandler();
            }

            @Override
            public void isFail() {
                Toast.makeText(SignInFragment.this.getContext(), getString(R.string.email_not_register), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reSendVerify(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        SignUpServiceImpl.getInstance().verify(user, new ThreadCallBackSign() {
            @Override
            public void isSuccess() {
                dialog.dismiss();
                Toast.makeText(SignInFragment.this.getContext(), getString(R.string.request_verify_account), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void isFail() {
                dialog.dismiss();
                Toast.makeText(SignInFragment.this.getContext(), getString(R.string.send_mail_verify_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInSuccess() {
        Toast.makeText(this.getContext(), getString(R.string.sign_in_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        this.getActivity().startActivity(intent);
        this.getActivity().finish();
    }

    private boolean isValidate(String email, String password) {
        if (email.isEmpty()) {
            edtEmail.setError(getString(R.string.request_email));
            return false;
        }

        if (!Pattern.matches(SignActivity.EMAIL_PATTERN, email)) {
            edtEmail.setError(getString(R.string.error_validate_email));
            return false;
        }

        if (password.isEmpty()) {
            edtPassword.setError(getString(R.string.request_password));
            return false;
        }

        return true;
    }
}