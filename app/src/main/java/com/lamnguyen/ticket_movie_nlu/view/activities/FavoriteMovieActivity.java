package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.MovieAdapter;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity {
    private RecyclerView rvDisplayFavoriteMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        rvDisplayFavoriteMovie = findViewById(R.id.recycler_view_display_favorite_movie);

        List<MovieDTO> sampleData = createSampleData();
        MovieAdapter movieAdapter = new MovieAdapter(sampleData);
        rvDisplayFavoriteMovie.setAdapter(movieAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvDisplayFavoriteMovie.setLayoutManager(layoutManager);
    }

    private List<MovieDTO> createSampleData() {
        List<MovieDTO> sampleData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            MovieDTO movie = new MovieDTO();
            movie.setName("Movie " + (i + 1));
            movie.setPoster("https://upload.wikimedia.org/wikipedia/en/b/be/Godzilla_x_kong_the_new_empire_poster.jpg");
            movie.setGenre("Genre " + (i + 1));
            movie.setRunTime(120);
            movie.setRate(4.5);
            movie.setVote(1000);

            sampleData.add(movie);
        }

        return sampleData;
    }
}
