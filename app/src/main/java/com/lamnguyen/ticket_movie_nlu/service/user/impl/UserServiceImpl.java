package com.lamnguyen.ticket_movie_nlu.service.user.impl;

import static com.android.volley.Request.Method.POST;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.bean.User;
import com.lamnguyen.ticket_movie_nlu.service.user.UserService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private static final String TAG = "UserServiceImpl";

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

    @Override
    public void checkRegister(Context context, String apiId, CallBack callBackSuccess, CallBack callBackFail) {
        JSONObject jsonObject = createJsonObjectApiId(context, apiId);
        if (jsonObject == null) return;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + "/user/api/check", "", jsonObject, new HashMap<>(), POST, response -> {
            callBackSuccess.run();
            saveUser(context, response);
        }, error -> {
            callBackFail.run();
            Log.e(TAG, "checkRegister: " + error.getMessage(), error);
        });
    }


    public void register(Context context, User user, CallBack callBackSuccess, CallBack callBackFail) {
        JSONObject jsonObject = createJsonObjectUser(context, user);
        if (jsonObject == null) return;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + "/user/api/register", "", jsonObject, new HashMap<>(), POST, response -> {
            callBackSuccess.run();
            saveUser(context, response);
        }, error -> {
            callBackFail.run();
            Toast.makeText(context, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "register: " + error.getMessage(), error);
        });
    }

    private JSONObject createJsonObjectUser(Context context, User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiId", user.getApiId());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("fullName", user.getFullName());
            jsonObject.put("phone", user.getPhone());
        } catch (JSONException e) {
            Toast.makeText(context, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "register: ", e);
            signOut();
            return null;
        }
        return jsonObject;
    }

    private JSONObject createJsonObjectApiId(Context context, String apiId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiId", apiId);
        } catch (JSONException e) {
            Toast.makeText(context, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "register: ", e);
            signOut();
            return null;
        }
        return jsonObject;
    }

    private void saveUser(Context context, JSONObject response) {
        try {
            SharedPreferencesUtils.saveUserID(context, response.getInt("data"));
        } catch (JSONException e) {
            Toast.makeText(context, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
            signOut();
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
