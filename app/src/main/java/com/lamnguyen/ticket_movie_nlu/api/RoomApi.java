package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.RoomDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;

import java.util.List;

public class RoomApi {
    private static RoomApi instance;

    public static RoomApi getInstance() {
        if (instance == null) {
            instance = new RoomApi();
        }
        return instance;
    }

    public void loadRoomsOfCinema(Context context, Integer cinemaId, CallAPI.CallAPIListener<List<RoomDTO>> listener) {
        String path = "/room/api/cinema/" + cinemaId;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        RoomDTO[] roomDTOS = new Gson().fromJson(response.getString("data"), RoomDTO[].class);
                        listener.completed(List.of(roomDTOS));
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }
}
