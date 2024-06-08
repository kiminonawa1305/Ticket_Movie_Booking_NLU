package com.lamnguyen.ticket_movie_nlu.service.auth.sign_up.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lamnguyen.ticket_movie_nlu.service.auth.sign_up.SignUpService;
import com.lamnguyen.ticket_movie_nlu.service.auth.sign_up.SignUpVerify;
import com.lamnguyen.ticket_movie_nlu.bean.User;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpServiceImpl implements SignUpService, SignUpVerify {
    private static SignUpServiceImpl instance;
    private final static String TAG = "SignUpServiceImpl";


    private SignUpServiceImpl() {
    }

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

    public void sendMailVerify(User user, @NonNull ThreadCallBackSign... callBack) {
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
