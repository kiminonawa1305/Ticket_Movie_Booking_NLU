package com.lamnguyen.ticket_movie_nlu.service.movie;

import android.content.Context;

import com.lamnguyen.ticket_movie_nlu.api.MovieApi;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.time.LocalDate;
import java.util.List;

public class MovieDetailService {
    private MovieApi movieApi;
    private static MovieDetailService instance;

    public static MovieDetailService getInstance() {
        if (instance == null) instance = new MovieDetailService();
        return instance;
    }

    private MovieDetailService() {
        movieApi = MovieApi.getInstance();
    }

    public void loadMovieDetail(Integer id, LocalDate date, Context context, CallAPI.CallAPIListener<MovieDetailDTO> listener) {
        movieApi.loadMovieDetail(id, date, context, listener);
    }

    public void loadAllMovieDetail(Context context, CallAPI.CallAPIListener<List<MovieDetailDTO>> callAPIListener) {
        movieApi.loadAllMovieDetail(context, callAPIListener);
    }
}
