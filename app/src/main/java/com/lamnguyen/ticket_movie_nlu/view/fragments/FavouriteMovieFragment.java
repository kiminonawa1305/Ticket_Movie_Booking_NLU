package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.MovieAdapter;
import com.lamnguyen.ticket_movie_nlu.api.MovieApi;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        movieAdapter = new MovieAdapter(movieList, LocalDate.now(), getActivity());
        rvDisplayFavoriteMovie.setAdapter(movieAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvDisplayFavoriteMovie.setLayoutManager(layoutManager);

        dialog = DialogLoading.newInstance(this.getContext());

        movieApi = MovieApi.getInstance();

        DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
        movieApi.loadListFavoriteMovieDetail(getContext(), new CallAPI.CallAPIListener<List<MovieDTO>>() {
            @Override
            public void completed(List<MovieDTO> movieDTOs) {
                dialog.dismiss();
                movieList.addAll(movieDTOs);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void error(Object error) {
                dialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                    Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(FavouriteMovieFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
