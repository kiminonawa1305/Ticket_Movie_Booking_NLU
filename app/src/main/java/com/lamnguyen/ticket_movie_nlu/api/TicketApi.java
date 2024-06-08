package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.TicketDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketApi {
    private static TicketApi instance;

    public static TicketApi getInstance() {
        return instance == null ? (instance = new TicketApi()) : instance;
    }

    private TicketApi() {
    }

    public void call(Context context, boolean avail, CallAPI.CallAPIListener<List<TicketDTO>>... listeners) {
        String path;
        if (avail) path = "/ticket/api/avail";
        else path = "/ticket/api/non-avail";
        Map<String, String> header = new HashMap<String, String>();
        int userId = SharedPreferencesUtils.getUserID(context);
        header.put("user-id", String.valueOf(userId));
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, null, header, Request.Method.GET,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("status") != 202) {
                                listeners[0].error(jsonObject.getString("message"));
                                return;
                            }

                            TicketDTO[] movieDTOs = new Gson().fromJson(jsonObject.getString("data"), TicketDTO[].class);
                            listeners[0].completed(List.of(movieDTOs));
                        } catch (JSONException e) {
                            listeners[0].error(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listeners[0].error(error);
                    }
                }
        );
    }

    public interface MovieListener<T> {
        void completed(List<T> movieDTOs);

        void error(Object error);
    }
}
