package com.lamnguyen.ticket_movie_nlu.Service.Auth.SignIn;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.Model.Utils.ThreadCallBackSign;

public interface SignInService {

    void signIn(String email, String password, @NonNull ThreadCallBackSign... callBack);
}
