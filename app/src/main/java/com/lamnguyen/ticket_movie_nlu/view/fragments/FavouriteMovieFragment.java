package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.MovieAdapter;
import com.lamnguyen.ticket_movie_nlu.api.MovieApi;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.dto.UserDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteMovieFragment extends Fragment {
    private RecyclerView rvDisplayFavoriteMovie;
    private MovieAdapter movieAdapter;
    private Dialog dialog;
    private MovieApi movieApi;


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

        dialog = DialogLoading.newInstance(this.getContext());

        movieApi = MovieApi.getInstance();
        movieApi.loadListFavoriteMovieDetail(getContext(), new CallAPI.CallAPIListener<List<MovieDTO>>() {
            @Override
            public void completed(List<MovieDTO> movieDTOs) {
                dialog.dismiss();
                rvDisplayFavoriteMovie.setAdapter(new MovieAdapter(movieDTOs, FavouriteMovieFragment.this.getActivity()));
            }

            @Override
            public void error(Object error) {
                dialog.dismiss();
                Log.e(ViewPagerMovieFragment.class.getSimpleName(), error.toString());
                Toast.makeText(FavouriteMovieFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
