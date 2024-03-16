package com.lamnguyen.ticket_movie_nlu.view.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.model.bean.Movie;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.model.utils.adapters.DisplayTicketMovieAdapter;

import java.util.ArrayList;
import java.util.List;

//Khởi tạo Fragment cho ViewPager2
public class TicketMovieFragment extends Fragment {
    public static final String TAG = TicketMovieFragment.class.getSimpleName();
    RecyclerView rclDisplayListTicketMovie;

    public TicketMovieFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_movie, container, false);
        rclDisplayListTicketMovie = view.findViewById(R.id.recycler_view_display_ticket_movie);
        rclDisplayListTicketMovie.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        rclDisplayListTicketMovie.setAdapter(new DisplayTicketMovieAdapter(getMovieListTest()));
        return view;
    }

    private List<Movie> getMovieListTest() {
        List<Movie> list = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            list.add(Movie.getMovieTest());

        return list;
    }
}
