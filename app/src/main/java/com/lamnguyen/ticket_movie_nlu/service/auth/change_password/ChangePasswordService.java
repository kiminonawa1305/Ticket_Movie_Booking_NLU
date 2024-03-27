package com.lamnguyen.ticket_movie_nlu.service.auth.change_password;

import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;

public interface ChangePasswordService {
    void changePassword(String newPassword, ThreadCallBackSign... callBack);
}
