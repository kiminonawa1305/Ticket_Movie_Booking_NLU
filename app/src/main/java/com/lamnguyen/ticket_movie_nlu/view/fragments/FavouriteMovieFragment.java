package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.MovieAdapter;
import com.lamnguyen.ticket_movie_nlu.api.MovieApi;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.dto.UserDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteMovieFragment extends Fragment {
    private RecyclerView rvDisplayFavoriteMovie;
    private MovieAdapter movieAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDisplayFavoriteMovie = view.findViewById(R.id.recycler_view_display_favorite_movie);

        // Khởi tạo adapter và RecyclerView
        List<MovieDTO> movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList, getActivity());
        rvDisplayFavoriteMovie.setAdapter(movieAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvDisplayFavoriteMovie.setLayoutManager(layoutManager);


        MovieApi movieApi = new MovieApi();
        movieApi.loadListFavoriteMovieDetail(getContext(), new CallAPI.CallAPIListener<List<MovieDetailDTO>>() {
            @Override
            public void completed(List<MovieDetailDTO> movieDetails) {

                List<MovieDTO> movies = new ArrayList<>();
                for (MovieDetailDTO movieDetail : movieDetails) {
                    MovieDTO movie = new MovieDTO();
                    movie.setId(movieDetail.getId());
                    movie.setTitle(movieDetail.getTitle());
                    movie.setPoster(movieDetail.getPoster());
                    movie.setGenre(movieDetail.getGenres().toString());
                    movie.setDuration(movieDetail.getDuration());
                    movie.setRate(movieDetail.getRate());
                    movie.setVote(movieDetail.getVote());
                    movies.add(movie);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapter.setMovies(movies);
                    }
                });
            }

            @Override
            public void error(Object error) {

            }
        });
    }

}
