package com.lamnguyen.ticket_movie_nlu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.CareerInfoAdapter;
import com.lamnguyen.ticket_movie_nlu.adapters.GenreAdapter;
import com.lamnguyen.ticket_movie_nlu.bean.CareerInfo;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.service.movie.MovieDetailService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.utils.ItemSpacingDecoration;


import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private RecyclerView directorRecyclerView;
    private RecyclerView actorRecyclerView;
    private RecyclerView genreRecyclerView;
    private LinearLayoutManager diretorLinearLayoutManager;
    private LinearLayoutManager actorLinearLayoutManager;
    private LinearLayoutManager genreLinearLayoutManager;
    private CareerInfoAdapter directorInfoAdapter;
    private CareerInfoAdapter actorInfoAdapter;
    private GenreAdapter genreAdapter;
    private MovieDetailService movieDetailService;
    private ShapeableImageView shapeableImageViewPosterMovieDetail;
    private TextView textViewRatingAmount, textViewDuration, textViewPremiereDate, textViewTitle;
    private Button buttonBack, buttonBooking;
    private ReadMoreTextView readMoreTextViewMovieSynopsis;
    private Dialog dialog;

    private static String TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Integer id = getIntent().getExtras().getInt("id", -1);
        LocalDate date = getIntent().getExtras().getSerializable("date") != null ? (LocalDate) getIntent().getExtras().getSerializable("date") : LocalDate.now();

        init();

        actorRecyclerView.setLayoutManager(actorLinearLayoutManager);
        actorRecyclerView.addItemDecoration(new ItemSpacingDecoration(6));

        directorRecyclerView.setLayoutManager(diretorLinearLayoutManager);
        directorRecyclerView.addItemDecoration(new ItemSpacingDecoration(6));

        genreRecyclerView.setLayoutManager(genreLinearLayoutManager);
        genreRecyclerView.addItemDecoration(new ItemSpacingDecoration(6));

        DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
        movieDetailService.loadMovieDetail(id, date, this, new CallAPI.CallAPIListener<MovieDetailDTO>() {
            @Override
            public void completed(MovieDetailDTO movieDetailDTO) {
                dialog.dismiss();
                loadDetail(movieDetailDTO);
            }

            @Override
            public void error(Object error) {
                dialog.dismiss();
            }
        });

        buttonBack.setOnClickListener((v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        buttonBooking.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowtimeActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("date", date);
            startActivity(intent);
        });
    }

    private void init() {
        movieDetailService = MovieDetailService.getInstance();

        directorRecyclerView = findViewById(R.id.director_rv);
        actorRecyclerView = findViewById(R.id.actor_rv);
        genreRecyclerView = findViewById(R.id.genre_rv);
        textViewRatingAmount = findViewById(R.id.rating_amount);
        textViewDuration = findViewById(R.id.duration_amount);
        textViewPremiereDate = findViewById(R.id.premiere_date);
        textViewTitle = findViewById(R.id.movie_title);
        readMoreTextViewMovieSynopsis = findViewById(R.id.movie_synopsis);
        shapeableImageViewPosterMovieDetail = findViewById(R.id.shapeable_image_view_poster_movie_detail);
        buttonBack = findViewById(R.id.button_back_to_showtime);
        directorInfoAdapter = new CareerInfoAdapter();
        actorInfoAdapter = new CareerInfoAdapter();
        genreAdapter = new GenreAdapter();
        buttonBooking = findViewById(R.id.button_booking);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);

        diretorLinearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        actorLinearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        genreLinearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
    }

    private void loadDetail(MovieDetailDTO movieDetailDTO) {
        loadGenre(movieDetailDTO.getGenres());

        loadActor(movieDetailDTO.getActors());

        loadDirector(movieDetailDTO.getDirectors());

        Glide.with(shapeableImageViewPosterMovieDetail).load(movieDetailDTO.getPoster()).into(shapeableImageViewPosterMovieDetail);

        textViewDuration.setText(movieDetailDTO.getDuration());
        textViewRatingAmount.setText(MessageFormat.format("{0}/5", movieDetailDTO.getRate()));
        textViewPremiereDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyy")));
        textViewTitle.setText(movieDetailDTO.getTitle());
        readMoreTextViewMovieSynopsis.setText(movieDetailDTO.getDescription());

        buttonBooking.setVisibility(movieDetailDTO.getAvail() ? Button.VISIBLE : Button.GONE);
    }

    private void loadGenre(String[] genres) {
        List<String> listGenre = Arrays.asList(genres);
        genreAdapter.setData(listGenre);
        genreRecyclerView.setLayoutManager(genreLinearLayoutManager);
    }

    private void loadDirector(String[] directors) {
        List<CareerInfo> listDirectorInfo = loadCareerInfo("Đạo diễn", directors);
        directorInfoAdapter.setData(listDirectorInfo);
        directorRecyclerView.setAdapter(directorInfoAdapter);
    }

    private void loadActor(String[] actors) {
        List<CareerInfo> listActorInfo = loadCareerInfo("Diễn viên", actors);

        actorInfoAdapter.setData(listActorInfo);
        actorRecyclerView.setAdapter(actorInfoAdapter);
    }

    private List<CareerInfo> loadCareerInfo(String career, String[] names) {
        List<CareerInfo> result = new ArrayList<>();
        Arrays.stream(names).forEach(actor -> {
            result.add(CareerInfo.builder()
                    .career(career)
                    .name(actor)
                    .build());
        });

        return result;
    }
}