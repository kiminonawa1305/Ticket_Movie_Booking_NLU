package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.view.PixelCopy;

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

    public static CinemaApi getInstance(){
        if(instance == null){
            instance = new CinemaApi();
        }
        return instance;
    }

    public void loadCinemas(Context context, CallAPI.CallAPIListener<List<CinemaDTO>> listener){
        String body = "/cinema/api/";
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + body, null, Request.Method.GET, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                  if(response.getInt("status") != 202){
                      listener.error(response.getString("message"));
                      return;
                  }

                  CinemaDTO[] cinemaDTOS = new Gson().fromJson(response.getString("data"), CinemaDTO[].class);
                  listener.completed(List.of(cinemaDTOS));
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
