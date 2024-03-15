package com.lamnguyen.ticket_movie_nlu.Service.Auth.SignUp;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.Model.Bean.User;
import com.lamnguyen.ticket_movie_nlu.Model.Utils.ThreadCallBackSign;

public interface SignUpService {
    void signUp(User user, @NonNull ThreadCallBackSign... callback);
}
