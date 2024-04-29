package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class MovieApi {
    private static MovieApi instance;
    private MovieInfoApi movieInfoDao;

    public static MovieApi getInstance() {
        if (instance == null) instance = new MovieApi();

        return instance;
    }

    private MovieApi() {
        movieInfoDao = MovieInfoApi.getInstance();
    }

    public void getMovieShowtime(LocalDate date, Context context, MovieServiceListener... listeners) {
        String body = "/movie/api/movie-showtime?date=" + date.toString();
        CallAPI.callStringRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        MovieDTO[] movieDTOs = gson.fromJson(response, MovieDTO[].class);
                        if (listeners[0] != null) listeners[0].completed(Arrays.asList(movieDTOs));
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (listeners[0] != null) listeners[0].error(error);
                    }
                });
    }


    public interface MovieServiceListener {
        void completed(List<MovieDTO> movieDTOs);

        void error(Object error);
    }
}
