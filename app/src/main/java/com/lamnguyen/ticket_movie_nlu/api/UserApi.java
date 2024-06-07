package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.lamnguyen.ticket_movie_nlu.service.user.SaveInfoUserService;
import com.lamnguyen.ticket_movie_nlu.bean.User;

public class UserApi implements SaveInfoUserService {
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
