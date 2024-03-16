package com.lamnguyen.ticket_movie_nlu.service.auth.sign_up;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.model.bean.User;
import com.lamnguyen.ticket_movie_nlu.model.utils.ThreadCallBackSign;

public interface SignUpService {
    void signUp(User user, @NonNull ThreadCallBackSign... callback);
}
