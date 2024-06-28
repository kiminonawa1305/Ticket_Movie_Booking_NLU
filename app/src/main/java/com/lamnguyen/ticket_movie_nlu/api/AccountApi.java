package com.lamnguyen.ticket_movie_nlu.api;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import com.lamnguyen.ticket_movie_nlu.dto.AccountDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.List;

public class AccountApi {
    private static AccountApi instance;

    private AccountApi() {
        // private constructor to enforce Singleton pattern
    }

    public static AccountApi getInstance() {
        if (instance == null) instance = new AccountApi();
        return instance;
    }

    public void loadUsers(Context context, CallAPI.CallAPIListener<List<AccountDTO>> listener) {
        String body = "/user/api/";
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("status") != 202) {
                                listener.error(jsonObject.getString("message"));
                                return;
                            }

                            AccountDTO[] accountDTOs = new Gson().fromJson(jsonObject.getString("data"), AccountDTO[].class);
                            listener.completed(List.of(accountDTOs));
                        } catch (JSONException e) {
                            listener.error(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error);
                    }
                }
        );
    }
}
