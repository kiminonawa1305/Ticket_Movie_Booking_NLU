package com.lamnguyen.ticket_movie_nlu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.dto.User;

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

    public static void saveUser(Context context, User user, boolean defaultLogin) {
        Editor editor = getEditor(context, USER);
        editor.putInt(UserKey.ID.key, user.getId());
        editor.putString(UserKey.EMAIL.key, user.getEmail());
        editor.putString(UserKey.FULL_NAME.key, user.getFullName());
        editor.putString(UserKey.PHONE.key, user.getPhone());
        editor.putBoolean(UserKey.DEFAULT_LOGIN.key, defaultLogin);
        editor.apply();
    }

    public static boolean isDefaultLogin(Context context) {
        SharedPreferences sharedPreferences = getInstance(context, USER);
        return sharedPreferences.getBoolean(UserKey.DEFAULT_LOGIN.key, false);
    }

    public static void logOut(Context context) {
        Editor editor = getEditor(context, USER);
        editor.remove(UserKey.ID.key);
        editor.remove(UserKey.EMAIL.key);
        editor.remove(UserKey.FULL_NAME.key);
        editor.remove(UserKey.PHONE.key);
        editor.apply();
        FirebaseAuth.getInstance().signOut();
        GoogleSignIn.getClient(context, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestEmail()
                .build()).signOut();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = getInstance(context, USER);
        int id = sharedPreferences.getInt(UserKey.ID.key, -1);
        if (id == -1) return null;
        return User.builder()
                .id(sharedPreferences.getInt(UserKey.ID.key, -1))
                .email(sharedPreferences.getString(UserKey.EMAIL.key, ""))
                .fullName(sharedPreferences.getString(UserKey.FULL_NAME.key, ""))
                .phone(sharedPreferences.getString(UserKey.PHONE.key, ""))
                .build();
    }

    public static enum UserKey {
        ID("id"),
        FULL_NAME("fullName"),
        EMAIL("email"),
        PHONE("phone"),
        DEFAULT_LOGIN("defaultLogin"),
        ;

        private String key;

        UserKey(String key) {
            this.key = key;
        }
    }
}
