package com.lamnguyen.ticket_movie_nlu.service.user;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.dto.User;

public interface SaveInfoUserService {
    void save(Context context, FirebaseAuth auth, User user);
}
