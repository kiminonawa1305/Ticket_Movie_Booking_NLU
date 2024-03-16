package com.lamnguyen.ticket_movie_nlu.service.Movie;

import android.content.Context;

import com.lamnguyen.ticket_movie_nlu.dao.MovieInfoDao;
import com.lamnguyen.ticket_movie_nlu.model.bean.Movie;

import java.util.List;

public class MovieInfoService {
    private static MovieInfoService instance;
    private MovieInfoDao movieInfoDao;

    public static MovieInfoService getInstance() {
        if (instance == null) instance = new MovieInfoService();

        return instance;
    }

    private MovieInfoService() {
        movieInfoDao = MovieInfoDao.getInstance();
    }

    public List<Movie> getMovie(Context context, String title, int page) {
        movieInfoDao.getMovie(context, title, page);

        return null;
    }
}
