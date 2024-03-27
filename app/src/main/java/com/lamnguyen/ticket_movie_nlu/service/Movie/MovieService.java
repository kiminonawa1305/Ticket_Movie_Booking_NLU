package com.lamnguyen.ticket_movie_nlu.service.Movie;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dao.MovieInfoDao;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class MovieService {
    private static MovieService instance;
    private MovieInfoDao movieInfoDao;

    public static MovieService getInstance() {
        if (instance == null) instance = new MovieService();

        return instance;
    }

    private MovieService() {
        movieInfoDao = MovieInfoDao.getInstance();
    }

    public void getMovieShowtime(LocalDate date, Context context, MovieServiceListener... listeners) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://lamnguyen.pagekite.me/movie/api/movie-showtime?date=" + date.toString();
        String TAG = context.getClass().getSimpleName();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
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
                }
        );

        //Set TAG
        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public interface MovieServiceListener {
        void completed(List<MovieDTO> movieDTOs);

        void error(Object error);
    }
}
