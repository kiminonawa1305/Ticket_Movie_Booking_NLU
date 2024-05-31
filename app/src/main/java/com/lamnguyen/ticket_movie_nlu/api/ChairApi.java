package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lamnguyen.ticket_movie_nlu.dto.ChairDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.enums.ChairStatus;
import com.lamnguyen.ticket_movie_nlu.response.ChairResponse;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;


public class ChairApi {
    private static ChairApi instance;

    public static ChairApi getInstance() {
        if (instance == null) instance = new ChairApi();

        return instance;
    }

    private ChairApi() {

    }

    public void loadChair(int showtimeId, Context context, CallAPI.CallAPIListener<ChairResponse>... listeners) {
        String body = "/chair/api/" + showtimeId;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("status") != 202) {
                                listeners[0].error(jsonObject.getString("message"));
                                return;
                            }

                            ChairResponse chairResponses = new Gson().fromJson(jsonObject.getString("data"), ChairResponse.class);
                            listeners[0].completed(chairResponses);
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

    @SneakyThrows
    public void updateChair(int userId, String uuid, int chairId, ChairStatus status, Context context, CallAPI.CallAPIListener<ChairDTO>... listeners) {
        String query = "/chair/api/update";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", uuid);
        jsonObject.put("chairId", chairId);
        jsonObject.put("status", status);
        jsonObject.put("userId", userId);


        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, query, jsonObject, null, Request.Method.POST, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("status") != 202) {
                                listeners[0].error(jsonObject.getString("message"));
                                return;
                            }

                            ChairDTO chairDTO = new Gson().fromJson(jsonObject.toString(), ChairDTO.class);
                            listeners[0].completed(chairDTO);
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
}
