package com.lamnguyen.ticket_movie_nlu.service.user;

import android.content.Context;

import com.lamnguyen.ticket_movie_nlu.dto.User;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.List;

public interface UserService {
    int EMPTY_PASSWORD = -1, EMPTY_RE_PASSWORD = -2, NOT_MATCH = 0, MATCH = 1;

    int matchPassword(String password, String rePassword);

    void signIn(Context context, String apiId, boolean defaultLogin, CallBack dismissDialog, CallBack callBackSuccess, CallBack callBackFail);

    void register(Context context, User user, boolean defaultLogin, CallBack callBackSuccess, CallBack callBackFail);


    public void loadUsers(Context context, CallAPI.CallAPIListener<List<User>> listener);

    interface CallBack {
        void run();
    }
}
