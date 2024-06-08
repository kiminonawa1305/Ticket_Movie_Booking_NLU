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

    public MovieApi() {

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

    public void loadListFavoriteMovieDetail( Context context, CallAPI.CallAPIListener<List<MovieDetailDTO>>... listeners) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sign", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0); // Sử dụng getInt() thay vì getString()
        String body = "/api/customers/favoriteList/" + userId;



        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    int status = jsonObject.getInt("status");
                    if (status != 200) {
                        listeners[0].error(jsonObject.getString("message"));
                        return;
                    }

                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    List<MovieDetailDTO> movieDetails = new ArrayList<>();

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject movieObject = dataArray.getJSONObject(i);

                        int movieStatus = movieObject.getInt("status");
                        if (movieStatus == 200) {
                            MovieDetailDTO movieDetailDTO = new Gson().fromJson(movieObject.getJSONObject("data").toString(), MovieDetailDTO.class);
                            movieDetails.add(movieDetailDTO);
                        }
                    }

                    listeners[0].completed(movieDetails);
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
