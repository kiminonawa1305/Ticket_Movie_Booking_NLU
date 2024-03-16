package com.lamnguyen.ticket_movie_nlu.service.UserService.impl;

import com.lamnguyen.ticket_movie_nlu.service.UserService.UserService;
import com.lamnguyen.ticket_movie_nlu.model.bean.User;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;

    public static UserServiceImpl getInstance() {
        if (instance == null) instance = new UserServiceImpl();
        return instance;
    }

    @Override
    public int matchPassword(User user, String rePassword) {
        if (user.getPassword().isEmpty()) return UserService.EMPTY_PASSWORD;
        if (rePassword.isEmpty()) return UserService.EMPTY_RE_PASSWORD;
        return user.getPassword().equals(rePassword) ? UserService.MATCH : UserService.NOT_MATCH;
    }
}
