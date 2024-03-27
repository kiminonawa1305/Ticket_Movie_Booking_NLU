package com.lamnguyen.ticket_movie_nlu.service.auth.forget_password.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.service.auth.forget_password.ForgetPasswordService;

public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    private FirebaseAuth auth;
    private static ForgetPasswordServiceImpl instance;

    private ForgetPasswordServiceImpl() {
        auth = FirebaseAuth.getInstance();
    }

    public static ForgetPasswordServiceImpl getInstance() {
        if (instance == null) instance = new ForgetPasswordServiceImpl();
        return instance;
    }

    @Override
    public void sendForgetPassword(String email, ThreadCallBackSign... callBack) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(taskResentPassword -> {
            if (taskResentPassword.isSuccessful()) {
                callBack[1].isSuccess();
            } else {
                callBack[1].isFail();
            }
        });
    }
}
