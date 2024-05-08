package com.lamnguyen.ticket_movie_nlu.service.UserService;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.bean.User;

public interface SaveInfoUserService {
    void save(Context context, FirebaseAuth auth, User user);
}
