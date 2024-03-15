package com.lamnguyen.ticket_movie_nlu.Service.Auth.SignUp.impl;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.Service.Auth.SignUp.SignUpService;
import com.lamnguyen.ticket_movie_nlu.Service.Auth.SignUp.SignUpVerify;
import com.lamnguyen.ticket_movie_nlu.Model.Bean.User;
import com.lamnguyen.ticket_movie_nlu.Model.Utils.ThreadCallBackSign;

import java.util.Properties;

public class SignUpServiceImpl implements SignUpService, SignUpVerify {
    private static SignUpServiceImpl instance;
    private static Properties properties;

    public static SignUpServiceImpl getInstance() {
        if (instance == null) instance = new SignUpServiceImpl();

        return instance;
    }

    @Override
    public void signUp(User user, @NonNull ThreadCallBackSign... callback) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback[0].isSuccess();
                return;
            }

            callback[0].isFail();
        });
    }

    @Override
    public void verify(User user, @NonNull ThreadCallBackSign... callBack) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword());
        if (auth.getCurrentUser() == null) {
            callBack[0].isFail();
            return;
        }

        sendMail(auth, 10, callBack[0]);
    }

    private void sendMail(FirebaseAuth auth, int times, ThreadCallBackSign callBack) {
        if (times < 0) {
            callBack.isFail();
            return;
        }

        int finalTimes = times--;
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callBack.isSuccess();
            }

            if (task.isCanceled()) {
                sendMail(auth, finalTimes, callBack);
            }
        });
    }

}
