package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.TicketDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONException;

import java.util.ArrayList;
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

    public void call(Context context, boolean avail, CallAPI.CallAPIListener<List<TicketDTO>> listener) {
        String path;
        if (avail) path = "/ticket/api/avail";
        else path = "/ticket/api/non-avail";
        Map<String, String> header = new HashMap<>();
        int userId = SharedPreferencesUtils.getUserID(context);
        header.put("user-id", String.valueOf(userId));
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, null, header, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        List<TicketDTO> ticketDTOList = new ArrayList<>();
                        if (response.has("data"))
                            ticketDTOList.addAll(List.of(new Gson().fromJson(response.getString("data"), TicketDTO[].class)));
                        listener.completed(ticketDTOList);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error
        );
    }
}
