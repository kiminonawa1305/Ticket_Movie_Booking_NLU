package com.lamnguyen.ticket_movie_nlu.service.auth.sign_in;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;

public interface SignInService {

    void signIn(String email, String password, @NonNull ThreadCallBackSign... callBack);
}
