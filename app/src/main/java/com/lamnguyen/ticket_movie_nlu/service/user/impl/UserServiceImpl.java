package com.lamnguyen.ticket_movie_nlu.service.user.impl;

import static com.android.volley.Request.Method.POST;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.api.UserApi;
import com.lamnguyen.ticket_movie_nlu.dto.User;
import com.lamnguyen.ticket_movie_nlu.service.user.UserService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private static final String TAG = "UserServiceImpl";
    private UserApi userApi;

    public static UserServiceImpl getInstance() {
        if (instance == null) instance = new UserServiceImpl();
        return instance;
    }

    private UserServiceImpl() {
        userApi = UserApi.getInstance();
    }

    @Override
    public int matchPassword(String password, String rePassword) {
        if (password.isEmpty()) return UserService.EMPTY_PASSWORD;
        if (rePassword.isEmpty()) return UserService.EMPTY_RE_PASSWORD;
        return password.equals(rePassword) ? UserService.MATCH : UserService.NOT_MATCH;
    }

    @Override
    public void signIn(Context context, String email, boolean defaultLogin, CallBack dismissDialog, CallBack callBackSuccess, CallBack callBackFail) {
        JSONObject jsonObject = createJsonObjectEmail(context, email);
        if (jsonObject == null) return;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + "/user/api/sign-in", null, jsonObject, new HashMap<>(), POST,
                response -> {
                    dismissDialog.run();
                    User user = null;
                    try {
                        user = new Gson().fromJson(response.getString("data").toString(), User.class);
                        SharedPreferencesUtils.saveUser(context, user, defaultLogin);
                        callBackSuccess.run();
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    dismissDialog.run();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError)
                        Toast.makeText(context, context.getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                    else if (error.networkResponse != null) {
                        CallAPI.ErrorResponse errorResponse = new Gson().fromJson(new String(error.networkResponse.data), CallAPI.ErrorResponse.class);
                        switch (errorResponse.status()) {
                            case 404 -> {
                                callBackFail.run();
                            }
                            case 405 ->
                                    Toast.makeText(context, errorResponse.message(), Toast.LENGTH_SHORT).show();
                            default ->
                                    Toast.makeText(context, context.getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void register(Context context, User user, boolean defaultLogin, CallBack callBackSuccess, CallBack callBackFail) {
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
            SharedPreferencesUtils.saveUser(context, register, defaultLogin);
            callBackSuccess.run();
        }, error -> {
            callBackFail.run();
            if (error instanceof TimeoutError || error instanceof NoConnectionError)
                Toast.makeText(context, context.getString(R.string.error_server), Toast.LENGTH_SHORT).show();
            else {
                CallAPI.ErrorResponse errorResponse = new Gson().fromJson(new String(error.networkResponse.data), CallAPI.ErrorResponse.class);
                Toast.makeText(context, errorResponse.message(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void loadUsers(Context context, CallAPI.CallAPIListener<List<User>> listener) {
        userApi.loadUsers(context, listener);
    }

    @Override
    public void lock(Context context, Integer id, CallAPI.CallAPIListener<User> listener) {
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + "/admin/user/api/lock/" + id, null, POST, response -> {
            User user = null;
            try {
                user = new Gson().fromJson(response.getJSONObject("data").toString(), User.class);
            } catch (JSONException error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            listener.completed(user);
        }, listener::error);
    }

    private JSONObject createJsonObjectUser(Context context, User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", user.getEmail());
            jsonObject.put("fullName", user.getFullName());
            jsonObject.put("phone", user.getPhone());
        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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
