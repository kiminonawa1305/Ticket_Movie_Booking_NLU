package com.lamnguyen.ticket_movie_nlu.View.Fragments;

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

import com.lamnguyen.ticket_movie_nlu.Service.Auth.SignIn.SignInService;
import com.lamnguyen.ticket_movie_nlu.Service.Auth.SignIn.impl.SignInServiceImpl;
import com.lamnguyen.ticket_movie_nlu.Model.Utils.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.View.Activities.MainActivity;
import com.lamnguyen.ticket_movie_nlu.View.Activities.SignActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    private TextView tvChangeFragment;
    private FragmentManager fragmentManager;
    private EditText edtEmail, edtPassword;
    private Button btnSignIn;
    private SignActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        tvChangeFragment = view.findViewById(R.id.text_view_change_fragment_sign_up);
        fragmentManager = getFragmentManager();
        this.edtEmail = view.findViewById(R.id.edit_text_sign_in_email);
        this.edtPassword = view.findViewById(R.id.edit_text_sign_in_password);
        this.btnSignIn = view.findViewById(R.id.button_sign_in);
        event();
        activity = (SignActivity) getActivity();
        return view;
    }

    private void event() {
        tvChangeFragment.setOnClickListener(v -> {
            changeFragmentSignUp();
        });

        loginEvent();
    }


    private void changeFragmentSignUp() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        fragmentTransaction.replace(R.id.fragment_sign, SignUpFragment.class, null);
        fragmentTransaction.commit();
    }

    private void loginEvent() {
        this.btnSignIn.setOnClickListener(v -> {
            String email = this.edtEmail.getText().toString();
            String password = this.edtPassword.getText().toString();
            SignInHandler(email, password);
        });
    }

    private void SignInHandler(String email, String password) {
        if (!isValidate(email, password)) return;

        activity.showDialogLoading(activity.getString(R.string.sign_in));
        SignInService signInService = SignInServiceImpl.getInstance();
        signInService.signIn(email, password, new ThreadCallBackSign() {
            @Override
            public void isSuccess() {
                activity.dismissDialogLoading();
                signInSuccess();
            }

            @Override
            public void isFail() {
                activity.dismissDialogLoading();
                Toast.makeText(SignInFragment.this.getContext(), getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
            }
        }, new ThreadCallBackSign() {
            @Override
            public void isSuccess() {
                activity.dismissDialogLoading();
                Toast.makeText(SignInFragment.this.getContext(), getString(R.string.verify_is_account), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void isFail() {
                activity.dismissDialogLoading();
                Toast.makeText(SignInFragment.this.getContext(), getString(R.string.request_verify_account), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInSuccess() {
        Toast.makeText(this.getContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        this.getActivity().startActivity(intent);
    }

    private boolean isValidate(String email, String password) {
        if (email.isEmpty()) {
            edtEmail.setError(getString(R.string.request_email));
            return false;
        }

        if (password.isEmpty()) {
            edtPassword.setError(getString(R.string.request_password));
            return false;
        }

        return true;
    }
}