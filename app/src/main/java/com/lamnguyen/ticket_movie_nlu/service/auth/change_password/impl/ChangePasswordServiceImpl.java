package com.lamnguyen.ticket_movie_nlu.service.auth.change_password.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.model.utils.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.service.auth.change_password.ChangePasswordService;

public class ChangePasswordServiceImpl implements ChangePasswordService {
    FirebaseAuth auth;

    private ChangePasswordServiceImpl() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void changePassword(String newPassword, ThreadCallBackSign... callBack) {
        auth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callBack[0].isSuccess();
            } else {
                callBack[0].isFail();
            }
        });
    }
}
