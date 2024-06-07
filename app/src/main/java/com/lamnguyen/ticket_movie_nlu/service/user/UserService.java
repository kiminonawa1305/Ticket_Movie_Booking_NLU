package com.lamnguyen.ticket_movie_nlu.service.user;

public interface UserService {
    int EMPTY_PASSWORD = -1, EMPTY_RE_PASSWORD = -2, NOT_MATCH = 0, MATCH = 1;

    int matchPassword(String password, String rePassword);
}
