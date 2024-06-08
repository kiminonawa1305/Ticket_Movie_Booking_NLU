package com.lamnguyen.ticket_movie_nlu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {
    public static final String SIGN_UP = "SIGN_UP";
    public static final String SIGN_IN = "SIGN_IN";
    public static final String USER = "USER";

    public static SharedPreferences getInstance(Context context, String name) {
        return context.getSharedPreferences(SIGN_UP, Context.MODE_PRIVATE);
    }

    public static Editor getEditor(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
    }

    public static void saveUserID(Context context, int userId) {
        getEditor(context, USER).putInt("userId", userId).apply();
    }

    public static int getUserID(Context context) {
        return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getInt(UserKey.USERID.key, -1);
    }

    public static enum UserKey {
        USERID("userId");
        private String key;

        UserKey(String key) {
            this.key = key;
        }
    }
}
