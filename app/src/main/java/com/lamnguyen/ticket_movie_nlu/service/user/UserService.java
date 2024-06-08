package com.lamnguyen.ticket_movie_nlu.service.user;

import android.content.Context;

import com.lamnguyen.ticket_movie_nlu.bean.User;

public interface UserService {
    int EMPTY_PASSWORD = -1, EMPTY_RE_PASSWORD = -2, NOT_MATCH = 0, MATCH = 1;

    int matchPassword(String password, String rePassword);

    void checkRegister(Context context, String apiId, CallBack callBack);

    interface CallBack {
        void run();
    }
}
