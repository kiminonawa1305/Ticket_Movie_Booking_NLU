package com.lamnguyen.ticket_movie_nlu.service.user.impl;

import static com.android.volley.Request.Method.POST;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
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
    public void checkRegister(Context context, String email, boolean googleSignIn, CallBack callBackSuccess, CallBack callBackFail) {
        JSONObject jsonObject = createJsonObjectEmail(context, email);
        if (jsonObject == null) return;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + "/user/api/check", "", jsonObject, new HashMap<>(), POST, response -> {
            User user = new Gson().fromJson(response.toString(), User.class);
            SharedPreferencesUtils.saveUser(context, user, googleSignIn);
            callBackSuccess.run();
        }, error -> {
            if (error.toString().equalsIgnoreCase("com.android.volley.TimeoutError"))
                Toast.makeText(context, "Lỗi server!", Toast.LENGTH_SHORT).show();
            else callBackFail.run();
        });
    }


    public void register(Context context, User user, boolean googleSignIn, CallBack callBackSuccess, CallBack callBackFail) {
        JSONObject jsonObject = createJsonObjectUser(context, user);
        if (jsonObject == null) return;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + "/user/api/register", "", jsonObject, new HashMap<>(), POST, response -> {
            User register;
            try {
                register = new Gson().fromJson(response.getJSONObject("data").toString(), User.class);
            } catch (JSONException error) {
                Log.e(TAG, "register: " + error.getMessage(), error);
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferencesUtils.saveUser(context, register, googleSignIn);
            callBackSuccess.run();
        }, error -> {
            callBackFail.run();
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "register: " + error.getMessage(), error);
        });
    }

    private JSONObject createJsonObjectUser(Context context, User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", user.getEmail());
            jsonObject.put("fullName", user.getFullName());
            jsonObject.put("phone", user.getPhone());
        } catch (JSONException e) {
            Toast.makeText(context, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "register: ", e);

            return null;
        }
        return jsonObject;
    }

    private JSONObject createJsonObjectEmail(Context context, String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
        } catch (JSONException e) {
            return null;
        }

        return jsonObject;
    }
}
