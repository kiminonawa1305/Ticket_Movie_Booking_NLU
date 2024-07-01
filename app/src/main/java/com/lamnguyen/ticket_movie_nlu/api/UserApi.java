package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.User;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class UserApi {
    private static UserApi instance;

    private UserApi() {
        // private constructor to enforce Singleton pattern
    }

    public static UserApi getInstance() {
        if (instance == null) instance = new UserApi();
        return instance;
    }

    public void loadUsers(Context context, CallAPI.CallAPIListener<List<User>> listener) {
        String body = "/admin/user/api/" + SharedPreferencesUtils.getUserID(context);
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }
                        List<User> users = new ArrayList<>();
                        if (response.has("data"))
                            users.addAll(List.of(new Gson().fromJson(response.getString("data"), User[].class)));
                        listener.completed(users);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error
        );
    }
}
