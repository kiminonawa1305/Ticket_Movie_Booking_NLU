package com.lamnguyen.ticket_movie_nlu.service.auth.check_mail.impl;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.lamnguyen.ticket_movie_nlu.model.utils.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.service.auth.check_mail.CheckEmailService;

import java.util.List;

public class CheckEmailServiceImpl implements CheckEmailService {
    private static CheckEmailServiceImpl instance;

    public static CheckEmailServiceImpl getInstance() {
        if (instance == null) instance = new CheckEmailServiceImpl();

        return instance;
    }

    private CheckEmailServiceImpl() {
    }

    @Override
    public void checkEmail(String email, ThreadCallBackSign... callBack) {
        ThreadCallBackSign callBackCheckEmail = callBack[0];
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                SignInMethodQueryResult result = task.getResult();
                List<String> signInMethods = result.getSignInMethods();

                if (signInMethods != null && !signInMethods.isEmpty()) {
                    callBackCheckEmail.isSuccess();
                } else {
                    callBackCheckEmail.isFail();
                }
            } else {
                callBackCheckEmail.isFail();
            }
        });
    }
}
