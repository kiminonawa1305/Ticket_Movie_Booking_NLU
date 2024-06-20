package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MovieApi {
    private static MovieApi instance;

    public static MovieApi getInstance() {
        if (instance == null) instance = new MovieApi();

        return instance;
    }

    private MovieApi() {
    }

    public void loadMovieShowtime(LocalDate date, Context context, CallAPI.CallAPIListener<List<MovieDTO>>... listeners) {
        String body = "/movie/api/showtime?date=" + date.toString();
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("status") != 202) {
                                listeners[0].error(jsonObject.getString("message"));
                                return;
                            }

                            MovieDTO[] movieDTOs = new Gson().fromJson(jsonObject.getString("data"), MovieDTO[].class);
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

    public void loadMovieDetail(Integer id, Context context, CallAPI.CallAPIListener<MovieDetailDTO>... listeners) {
        String body = "/movie/api/detail/" + id;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("status") != 202) {
                                listeners[0].error(jsonObject.getString("message"));
                                return;
                            }

                            MovieDetailDTO movieDetailDTO = new Gson().fromJson(jsonObject.getString("data"), MovieDetailDTO.class);
                            Log.i(getClass().getSimpleName(), "onResponse: " + movieDetailDTO.toString());
                            listeners[0].completed(movieDetailDTO);
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

    public void loadListFavoriteMovieDetail(Context context, CallAPI.CallAPIListener<List<MovieDTO>>... listeners) {
        int userId = SharedPreferencesUtils.getUserID(context);
        String body = "/movie-favourite/api/" + userId;

        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + body, null, Request.Method.GET, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("status") != 202) {
                        listeners[0].error(jsonObject.getString("message"));
                        return;
                    }

                    MovieDTO[] movieDTOs = new Gson().fromJson(jsonObject.getString("data"), MovieDTO[].class);
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
        });
    }

}
