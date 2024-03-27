package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.DisplayPageMovieShowtimeAdapter;
import com.lamnguyen.ticket_movie_nlu.service.Movie.MovieService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieFragment extends Fragment {
    private TabLayout tlDisplayTicketMovie;
    private ViewPager2 vpgDisplayTicketMovie;
    private MovieService movieInfoService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        Toolbar toolbar = view.findViewById(R.id.tool_bar_back);

        this.init(view);
        setupTabLayoutDisplayTicketMovie(tlDisplayTicketMovie, vpgDisplayTicketMovie);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void init(View view) {
        tlDisplayTicketMovie = view.findViewById(R.id.tab_layout_display_ticket_movie);
        vpgDisplayTicketMovie = view.findViewById(R.id.view_pager_display_ticket_movie);
        vpgDisplayTicketMovie.setAdapter(new DisplayPageMovieShowtimeAdapter(this.getActivity()));
        movieInfoService = MovieService.getInstance();
    }


    private void setupTabLayoutDisplayTicketMovie(TabLayout tlDisplayPageMovieShowtime, ViewPager2 vpgDisplayMovieShowtime) {
        new TabLayoutMediator(tlDisplayPageMovieShowtime, vpgDisplayMovieShowtime,
                (tab, position) -> {
                    tab.setText(LocalDate.now().plusDays(position).format(DateTimeFormatter.ofPattern("dd\nMM")));
                }
        ).attach();
    }
}