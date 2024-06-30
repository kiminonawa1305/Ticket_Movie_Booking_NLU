package com.lamnguyen.ticket_movie_nlu.service.auth.sign_in.impl;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.lamnguyen.ticket_movie_nlu.service.auth.sign_in.SignInService;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;

public class SignInServiceImpl implements SignInService {
    private FirebaseAuth auth;

    private static SignInServiceImpl instance;

    public static SignInServiceImpl getInstance() {
        if (instance == null) instance = new SignInServiceImpl();

        return instance;
    }

    private SignInServiceImpl() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void signIn(String email, String password, @NonNull ThreadCallBackSign... callBack) {
        ThreadCallBackSign callBackSignIn = callBack[0];
        ThreadCallBackSign callBackUserNotExit = callBack[1];
        ThreadCallBackSign callBackBlockRequest = callBack[2];
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callBackSignIn.isSuccess();
            }
        }).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthInvalidCredentialsException) callBackSignIn.isFail();
            else if (e instanceof FirebaseAuthInvalidUserException)
                callBackUserNotExit.isFail();
            else
                callBackBlockRequest.isFail();
        });
    }
}