package com.lamnguyen.ticket_movie_nlu.service.auth.sign_up;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.bean.User;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;

public interface SignUpService {
    void signUp(User user, @NonNull ThreadCallBackSign... callback);
}
