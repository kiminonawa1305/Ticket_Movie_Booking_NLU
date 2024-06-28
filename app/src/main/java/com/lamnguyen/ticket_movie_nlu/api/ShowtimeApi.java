package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.media.Image;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.lamnguyen.ticket_movie_nlu.dto.RoomDTO;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowtimeApi {

    private static ShowtimeApi instance;

    public static ShowtimeApi getInstance(){
        if(instance == null){
            instance = new ShowtimeApi();
        }
        return instance;
    }

    public void addShowtime(Context context, List<RoomDTO> selectedRoomDTOS, LocalDateTime schedule, Integer movieId, Integer roomId, CallAPI.CallAPIListener<ShowtimeDTO> listener) throws JSONException {
        String body = "/showtime/api/";
        JSONObject newShowtimeRequestBodyJSON = new JSONObject();
        newShowtimeRequestBodyJSON.put("movieId", movieId);
        newShowtimeRequestBodyJSON.put("start", schedule);
        for (RoomDTO selectedRoomDTO: selectedRoomDTOS) {
            newShowtimeRequestBodyJSON.put("roomId", selectedRoomDTO.getId());
            CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + body, null, newShowtimeRequestBodyJSON, null, Request.Method.POST, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("status") != 202){
                            listener.error(response.getString("message"));
                            return;
                        }

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {

                                    @Override
                                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_DATE_TIME);
                                    }
                                }).create();

                        ShowtimeDTO newShowtimeDTO = gson.fromJson(response.getString("data"), ShowtimeDTO.class);
                        listener.completed(newShowtimeDTO);
                    }catch (JSONException e){
                        listener.error(e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.error(error);
                }
            });
        }
    }
}
