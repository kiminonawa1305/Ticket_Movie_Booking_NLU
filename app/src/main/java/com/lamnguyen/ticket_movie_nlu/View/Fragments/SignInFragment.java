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

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.View.Activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    private TextView tvChangeFragment;
    private FragmentManager fragmentManager;
    private EditText edtUserName, edtPassword;
    private Button btnSignIn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        tvChangeFragment = view.findViewById(R.id.text_view_change_fragment_sign_up);
        fragmentManager = getFragmentManager();
        this.edtUserName = view.findViewById(R.id.edit_text_sign_in_user_name);
        this.edtPassword = view.findViewById(R.id.edit_text_sign_in_password);
        this.btnSignIn = view.findViewById(R.id.button_sign_in);
        event();
        return view;
    }

    private void event() {
        tvChangeFragment.setOnClickListener(v -> {
            changeFragmentSignUp();
        });

        login();
    }

    private void changeFragmentSignUp() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        fragmentTransaction.replace(R.id.fragment_sign, SignUpFragment.class, null);
        fragmentTransaction.commit();
    }

    private void login(){
        this.btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), MainActivity.class);
            getActivity().startActivity(intent);
        });
    }

}