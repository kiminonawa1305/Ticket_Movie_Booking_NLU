package com.lamnguyen.ticket_movie_nlu.service.auth.sign_up;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.dto.User;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;

public interface SignUpVerify {
    void sendMailVerify(User user, @NonNull ThreadCallBackSign... callBack);
}
