package com.lamnguyen.ticket_movie_nlu.service.auth.check_mail;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.model.utils.ThreadCallBackSign;

public interface CheckEmailService {
    void checkEmail(String email, @NonNull ThreadCallBackSign... callBack);
}
