package com.lamnguyen.ticket_movie_nlu.service.movie;

import android.content.Context;

import com.lamnguyen.ticket_movie_nlu.api.MovieApi;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeByCinema;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;

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


    public void loadShowtime(int movieId, LocalDate date, Context context, CallAPI.CallAPIListener<List<ShowtimeByCinema>> listener) {
        movieApi.loadShowtime(movieId, date, context, listener);
    }

    public void addNewMovie(Context context, String idApi, CallAPI.CallAPIListener<MovieDTO> listener) throws JSONException {
        movieApi.addNewMovie(context, idApi, listener);
    }

    public void setMovieFavourite(Context context, Integer movieId, CallAPI.CallAPIListener<Void> listener) {
        movieApi.setMovieFavourite(context, movieId, listener);
    }
}
