package com.lamnguyen.ticket_movie_nlu.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lamnguyen.ticket_movie_nlu.bean.Movie;

import java.util.List;

public class MovieInfoDao {
    private static final String TAG = "MovieInfoDao";
    public static final String HTTP_API = "http://www.omdbapi.com/?apikey=c3d0a99f";
    public static MovieInfoDao instance;

    public static MovieInfoDao getInstance() {
        if (instance == null) instance = new MovieInfoDao();

        return instance;
    }

    private MovieInfoDao() {
    }

    public List<Movie> getMovie(Context context, String title, int page) {
        RequestQueue queue = Volley.newRequestQueue(context);
        // String url = HTTP_API + "&s=" + title + "&page" + page;

        String url = "https://www.omdbapi.com/?apikey=c3d0a99f&s=hello&page=1";
        // Request a JsonObject response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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

        //Set TAG
        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return null;
    }
}
