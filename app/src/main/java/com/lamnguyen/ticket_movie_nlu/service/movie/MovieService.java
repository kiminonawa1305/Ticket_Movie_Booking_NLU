package com.lamnguyen.ticket_movie_nlu.service.movie;

import android.content.Context;

import com.lamnguyen.ticket_movie_nlu.api.MovieApi;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeByCinema;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.time.LocalDate;
import java.util.List;

public class MovieService {
    private MovieApi movieApi;
    private static MovieService instance;

    public static MovieService getInstance() {
        if (instance == null) instance = new MovieService();
        return instance;
    }

    private MovieService() {
        movieApi = MovieApi.getInstance();
    }

    public void loadMovie(LocalDate date, Context context, CallAPI.CallAPIListener<List<MovieDTO>> listener) {
        movieApi.loadMovie(date, context, listener);
    }


    public void loadShowtime(int movieId, Context context, CallAPI.CallAPIListener<List<ShowtimeByCinema>> listener) {
        movieApi.loadShowtime(movieId, context, listener);
    }
}
