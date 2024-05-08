package com.lamnguyen.ticket_movie_nlu.service.UserService.impl;

import com.lamnguyen.ticket_movie_nlu.service.UserService.UserService;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;

    public static UserServiceImpl getInstance() {
        if (instance == null) instance = new UserServiceImpl();
        return instance;
    }

    @Override
    public int matchPassword(String password, String rePassword) {
        if (password.isEmpty()) return UserService.EMPTY_PASSWORD;
        if (rePassword.isEmpty()) return UserService.EMPTY_RE_PASSWORD;
        return password.equals(rePassword) ? UserService.MATCH : UserService.NOT_MATCH;
    }
}
