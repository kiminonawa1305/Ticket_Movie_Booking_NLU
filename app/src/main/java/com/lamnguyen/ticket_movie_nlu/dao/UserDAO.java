package com.lamnguyen.ticket_movie_nlu.dao;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.lamnguyen.ticket_movie_nlu.service.UserService.SaveInfoUserService;
import com.lamnguyen.ticket_movie_nlu.model.bean.User;

public class UserDAO implements SaveInfoUserService {
    @Override
    public void save(Context context, FirebaseAuth auth, User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("users")
                .child(auth.getCurrentUser().getUid())
                .setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        return;
                    }

                    if (task.isCanceled()) {

                    }
                });
    }
}
