package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.lamnguyen.ticket_movie_nlu.dto.RoomDTO;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowtimeApi {

    private static ShowtimeApi instance;

    public static ShowtimeApi getInstance() {
        if (instance == null) {
            instance = new ShowtimeApi();
        }
        return instance;
    }

    public void addShowtime(Context context, Integer roomId, Integer movieId, LocalDateTime schedule, CallAPI.CallAPIListener<ShowtimeDTO> listener) throws JSONException {
        String path = "/admin/showtime/api/add";
        JSONObject newShowtimeRequestBodyJSON = new JSONObject();
        newShowtimeRequestBodyJSON.put("movieId", movieId);
        newShowtimeRequestBodyJSON.put("start", schedule);
        newShowtimeRequestBodyJSON.put("roomId", roomId);
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, newShowtimeRequestBodyJSON, null, Request.Method.POST,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(LocalDateTime.class,
                                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context1) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_DATE_TIME))
                                .create();

                        ShowtimeDTO newShowtimeDTO = gson.fromJson(response.getString("data"), ShowtimeDTO.class);
                        listener.completed(newShowtimeDTO);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }
}
