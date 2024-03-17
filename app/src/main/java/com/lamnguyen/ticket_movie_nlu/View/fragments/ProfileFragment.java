package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lamnguyen.ticket_movie_nlu.R;

public class ProfileFragment extends Fragment {
    private Button btnChangePassword, btnSetting, btnInformation, btnSignOut;
    Dialog dlgChangePassword;

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
    }

    private void event() {
        btnChangePassword.setOnClickListener(v -> {
            dialogChangePassword();
        });
    }

    private void dialogChangePassword() {
        dlgChangePassword = new Dialog(this.getContext());
        dlgChangePassword.setContentView(R.layout.dialog_change_password);
        dlgChangePassword.show();
    }

    private void getPassword() {
        TextView edtNewPassword = dlgChangePassword.findViewById(R.id.edit_text_new_password);
        TextView edtReNewPassword = dlgChangePassword.findViewById(R.id.edit_text_re_new_password);


    }
}