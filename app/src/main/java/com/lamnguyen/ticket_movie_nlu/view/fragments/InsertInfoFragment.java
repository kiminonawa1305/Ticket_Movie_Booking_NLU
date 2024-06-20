package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.bean.User;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.service.user.UserService;
import com.lamnguyen.ticket_movie_nlu.service.user.impl.UserServiceImpl;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.view.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class InsertInfoFragment extends Fragment {
    private FragmentManager fragmentManager;
    private EditText edtFullName, edtPhone;
    private Button btnNext;
    private User user;
    private Dialog dialog;

    private UserService userService;

    private final static String EMAIL_ARG = "email", API_ID_ARG = "apiId", PASSWORD_ARG = "password";
    private String email, password, apiId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userService = UserServiceImpl.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_info, container, false);
        fragmentManager = getParentFragmentManager();
        init(view);
        event();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        if (bundle == null) return;

        email = bundle.getString(EMAIL_ARG);
        apiId = bundle.getString(API_ID_ARG);
        password = bundle.getString(PASSWORD_ARG);
    }

    private void init(View view) {
        edtFullName = view.findViewById(R.id.edit_text_full_name);
        edtPhone = view.findViewById(R.id.edit_text_phone);
        btnNext = view.findViewById(R.id.button_next);

        dialog = DialogLoading.newInstance(getContext());
    }


    private void event() {
        btnNext.setOnClickListener(v -> {
            DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
            String phone = edtPhone.getText().toString();
            String fullName = edtFullName.getText().toString();
            if (phone.isEmpty()) {
                edtPhone.setError(getString(R.string.phone_empty));
                return;
            }
            if (fullName.isEmpty()) {
                edtFullName.setError(getString(R.string.full_name_empty));
                return;
            }

            userService.register(getContext(),
                    User.builder()
                            .apiId(apiId)
                            .email(email)
                            .fullName(edtFullName.getText().toString())
                            .phone(edtPhone.getText().toString())
                            .build(),
                    () -> {
                        dialog.dismiss();
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(command -> {
                            if (command.isSuccessful()) {
                                Toast.makeText(this.getContext(), getString(R.string.sign_in_success), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(this.getContext(), MainActivity.class);
                                this.getActivity().startActivity(intent);
                                this.getActivity().finish();
                            } else {
                                Toast.makeText(this.getContext(), getString(R.string.sign_in_fail), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }, () -> {
                        dialog.dismiss();
                    });

        });
    }
}