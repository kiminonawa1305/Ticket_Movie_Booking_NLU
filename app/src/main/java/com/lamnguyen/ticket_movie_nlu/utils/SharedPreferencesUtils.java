package com.lamnguyen.ticket_movie_nlu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lamnguyen.ticket_movie_nlu.bean.User;

public class SharedPreferencesUtils {
    public static final String SIGN_UP = "SIGN_UP";
    public static final String SIGN_IN = "SIGN_IN";
    public static final String USER = "USER";

    public static SharedPreferences getInstance(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static Editor getEditor(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
    }

    public static void saveUserID(Context context, int userId) {
        getEditor(context, USER).putInt("userId", userId).apply();
    }

    public static int getUserID(Context context) {
        return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getInt(UserKey.ID.key, -1);
    }

    public static void saveUser(Context context, User user) {
        Editor editor = getEditor(context, USER);
        editor.putInt(UserKey.ID.key, user.getId());
        editor.putString(UserKey.API_ID.key, user.getApiId());
        editor.putString(UserKey.EMAIL.key, user.getEmail());
        editor.putString(UserKey.FULL_NAME.key, user.getFullName());
        editor.putString(UserKey.PHONE.key, user.getPhone());
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = getInstance(context, USER);
        return User.builder()
                .id(sharedPreferences.getInt(UserKey.ID.key, -1))
                .apiId(sharedPreferences.getString(UserKey.API_ID.key, ""))
                .email(sharedPreferences.getString(UserKey.EMAIL.key, ""))
                .fullName(sharedPreferences.getString(UserKey.FULL_NAME.key, ""))
                .phone(sharedPreferences.getString(UserKey.PHONE.key, ""))
                .build();
    }

    public static enum UserKey {
        ID("id"),
        API_ID("apiId"),
        FULL_NAME("fullName"),
        EMAIL("email"),
        PHONE("phone"),
        ;

        private String key;

        UserKey(String key) {
            this.key = key;
        }
    }
}
