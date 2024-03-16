package com.lamnguyen.ticket_movie_nlu.service.auth.forget_password;

import com.lamnguyen.ticket_movie_nlu.model.utils.ThreadCallBackSign;

public interface ForgetPasswordService {
    void sendForgetPassword(String email, ThreadCallBackSign... callBack);
}
