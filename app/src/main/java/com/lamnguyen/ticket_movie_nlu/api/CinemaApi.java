package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.view.PixelCopy;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.CinemaDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CinemaApi {
    private static CinemaApi instance;

    public static CinemaApi getInstance() {
        if (instance == null) {
            instance = new CinemaApi();
        }
        return instance;
    }

    public void loadCinemas(Context context, CallAPI.CallAPIListener<List<CinemaDTO>> listener) {
        String path = "/cinema/api/";
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        CinemaDTO[] cinemaDTOS = new Gson().fromJson(response.getString("data"), CinemaDTO[].class);
                        listener.completed(List.of(cinemaDTOS));
                    } catch (JSONException e) {
                        listener.error(e.getMessage());
                    }
                }, error -> {
                    if (error.fillInStackTrace().toString().equalsIgnoreCase("com.android.volley.TimeoutError"))
                        Toast.makeText(context, "Lỗi server!", Toast.LENGTH_SHORT).show();
                    else
                        listener.error(error);
                });
    }
}
