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
        this.signInHandler(email, password, callBack);
    }

    /*
     * signInHandler Phương thức xử lý đăng nhập
     * @param email: địa chỉ email người dùng
     * @param password: mật khẩu người dùng
     * @param callBack: danh dách các callBack hổ trợ việc xử lý các tác vụ trả về khi đăng nhập.
     * callBack[0] xử lý các vấn đề liên quan đến việc đăng nhập thành công hay không thành công.
     * callBack[1] xử lý các vấn đề liên quan đến tài khoản đó có verify chưa.
     *
     * checkCallBack(callBack): kiểm tra 2 callBack chính cho phương thức không
     * */
    private void signInHandler(String email, String password, @NonNull ThreadCallBackSign... callBack) {
        ThreadCallBackSign callBackSignIn = callBack[0];
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
//                if (isVerify()) callBackSignIn.isSuccess();
//                else callBackVerify.isFail();
                callBackSignIn.isSuccess();
                return;
            }

            if (task.isCanceled()) {
                callBackSignIn.isFail();
            }
        }).addOnFailureListener(e -> {
            callBackSignIn.isFail();
            Log.e(getClass().getSimpleName(), "error", e);
        });
    }


    private boolean isVerify() {
        if (auth.getCurrentUser() == null) return false;
        return auth.getCurrentUser().isEmailVerified();
    }
}