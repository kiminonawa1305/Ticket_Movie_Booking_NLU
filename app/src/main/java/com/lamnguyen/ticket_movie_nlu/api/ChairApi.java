package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.ChairDTO;
import com.lamnguyen.ticket_movie_nlu.enums.ChairStatus;
import com.lamnguyen.ticket_movie_nlu.response.ChairResponse;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.SneakyThrows;


public class ChairApi {
    private static ChairApi instance;

    public static ChairApi getInstance() {
        if (instance == null) instance = new ChairApi();

        return instance;
    }

    private ChairApi() {
    }

    public void loadChair(int showtimeId, Context context, CallAPI.CallAPIListener<ChairResponse> listener) {
        String path = "/chair/api/" + showtimeId;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, Request.Method.GET,
                jsonObject -> {
                    try {
                        if (jsonObject.getInt("status") != 202) {
                            listener.error(jsonObject.getString("message"));
                            return;
                        }

                        ChairResponse chairResponses = new Gson().fromJson(jsonObject.getString("data"), ChairResponse.class);
                        listener.completed(chairResponses);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error
        );
    }

    @SneakyThrows
    public void updateChair(int userId, String uuid, int chairId, ChairStatus status, Context context, CallAPI.CallAPIListener<ChairDTO> listener) {
        String path = "/chair/api/update";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", uuid);
        jsonObject.put("chairId", chairId);
        jsonObject.put("status", status);
        jsonObject.put("userId", userId);


        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, jsonObject, null, Request.Method.POST,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        ChairDTO chairDTO = new Gson().fromJson(response.getString("data"), ChairDTO.class);
                        listener.completed(chairDTO);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error
        );
    }
}
