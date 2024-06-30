package com.lamnguyen.ticket_movie_nlu.view.fragments;


import android.app.Dialog;
import android.os.Bundle;
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
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.service.movie.MovieService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import java.time.LocalDate;
import java.util.List;

//Khởi tạo Fragment cho ViewPager2
public class ViewPagerMovieFragment extends Fragment {
    public static final String TAG = ViewPagerMovieFragment.class.getSimpleName();
    private RecyclerView rclDisplayListMovieShowtime;
    private Dialog dialog;
    private MovieService movieService;

    public ViewPagerMovieFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieService = MovieService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rclDisplayListMovieShowtime = view.findViewById(R.id.recycler_view_display_movie);
        rclDisplayListMovieShowtime.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMovieShowtime(LocalDate.now().plusDays(getArguments().getInt("position")));
    }

    private void loadMovieShowtime(LocalDate dateTime) {
        DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
        movieService.loadMovie(dateTime, this.getContext(), new CallAPI.CallAPIListener<>() {

            @Override
            public void completed(List<MovieDTO> movieDTOs) {
                dialog.dismiss();
                if (movieDTOs.isEmpty())
                    Toast.makeText(getContext(), getString(R.string.no_movie_showtime), Toast.LENGTH_SHORT).show();
                rclDisplayListMovieShowtime.setAdapter(new MovieAdapter(movieDTOs, dateTime, ViewPagerMovieFragment.this.getActivity()));
            }

            @Override
            public void error(Object error) {
                dialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                    Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
