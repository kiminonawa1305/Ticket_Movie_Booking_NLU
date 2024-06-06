package com.lamnguyen.ticket_movie_nlu.service.auth.sign_in.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
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
            if (e.getMessage().startsWith("The password is invalid")) callBackSignIn.isFail();
            else if (e.getMessage().startsWith("There is no user record"))
                callBackUserNotExit.isFail();
            else callBackBlockRequest.isFail();
            Log.e(getClass().getSimpleName(), "error: " + e.getMessage(), e);
        });
    }
}