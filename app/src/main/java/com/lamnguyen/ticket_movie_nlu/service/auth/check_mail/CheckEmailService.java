package com.lamnguyen.ticket_movie_nlu.service.auth.check_mail;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;

public interface CheckEmailService {
    void checkEmail(String email, @NonNull ThreadCallBackSign... callBack);
}
