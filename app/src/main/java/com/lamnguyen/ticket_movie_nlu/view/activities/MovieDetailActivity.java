package com.lamnguyen.ticket_movie_nlu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.CareerInfoAdapter;
import com.lamnguyen.ticket_movie_nlu.adapters.GenreAdapter;
import com.lamnguyen.ticket_movie_nlu.bean.CareerInfo;
import com.lamnguyen.ticket_movie_nlu.utils.ItemSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private RecyclerView directorRecyclerView;
    private RecyclerView actorRecyclerView;
    private RecyclerView genreRecyclerView;
    private List<CareerInfo> listDirectorInfo;
    private List<CareerInfo> listActorInfo;
    private List<String> listGenre;
    private LinearLayoutManager diretorLinearLayoutManager;
    private LinearLayoutManager actorLinearLayoutManager;
    private LinearLayoutManager genreLinearLayoutManager;
    private CareerInfoAdapter directorInfoAdapter;
    private CareerInfoAdapter actorInfoAdapter;
    private GenreAdapter genreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        directorRecyclerView = findViewById(R.id.director_rv);
        actorRecyclerView = findViewById(R.id.actor_rv);
        genreRecyclerView = findViewById(R.id.genre_rv);

        listDirectorInfo = new ArrayList<>();
        listDirectorInfo.add(new CareerInfo("Đạo diễn", "Shameik Moore"));
        listDirectorInfo.add(new CareerInfo("Đạo diễn", "Kemp Powers"));
        listDirectorInfo.add(new CareerInfo("Đạo diễn", "Dave Callaham"));

        listActorInfo = new ArrayList<>();
        listActorInfo.add(new CareerInfo("Diễn viên", "Shameik Moore"));
        listActorInfo.add(new CareerInfo("Diễn viên", "Hailee Steinfeld"));
        listActorInfo.add(new CareerInfo("Diễn viên", "Brian Tyree Henry"));
        listActorInfo.add(new CareerInfo("Diễn viên", "Luna Lauren Velez"));
        listActorInfo.add(new CareerInfo("Diễn viên", "Jake Johnson"));

        listGenre = new ArrayList<>();
        listGenre.add("Hành động");
        listGenre.add("Phiêu lưu");
        listGenre.add("Hoạt hình");
        listGenre.add("Khoa học - viễn tưởng");
        listGenre.add("Siêu anh hùng");

        diretorLinearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        actorLinearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        genreLinearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);

        directorInfoAdapter = new CareerInfoAdapter();
        directorInfoAdapter.setData(listDirectorInfo);
        directorRecyclerView.setLayoutManager(diretorLinearLayoutManager);
        directorRecyclerView.setAdapter(directorInfoAdapter);
        directorRecyclerView.addItemDecoration(new ItemSpacingDecoration(6));

        actorInfoAdapter = new CareerInfoAdapter();
        actorInfoAdapter.setData(listActorInfo);
        actorRecyclerView.setLayoutManager(actorLinearLayoutManager);
        actorRecyclerView.setAdapter(actorInfoAdapter);
        actorRecyclerView.addItemDecoration(new ItemSpacingDecoration(6));

        genreAdapter = new GenreAdapter();
        genreAdapter.setData(listGenre);
        genreRecyclerView.setLayoutManager(genreLinearLayoutManager);
        genreRecyclerView.setAdapter(genreAdapter);
        genreRecyclerView.addItemDecoration(new ItemSpacingDecoration(6));
    }
}