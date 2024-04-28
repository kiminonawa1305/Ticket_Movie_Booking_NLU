package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lamnguyen.ticket_movie_nlu.bean.Movie;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.List;

public class MovieInfoApi {
    private static final String TAG = "MovieInfoApi";
    public static MovieInfoApi instance;

    public static MovieInfoApi getInstance() {
        if (instance == null) instance = new MovieInfoApi();

        return instance;
    }

    private MovieInfoApi() {
    }

    public List<Movie> getMovie(Context context, String title, int page) {
        // String url = HTTP_API + "&s=" + title + "&page" + page;

        String body = "&s=" + title + "&page=1";
        CallAPI.callStringRequest(context, CallAPI.URL_OMDB, body, Request.Method.GET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
        return null;
    }
}
