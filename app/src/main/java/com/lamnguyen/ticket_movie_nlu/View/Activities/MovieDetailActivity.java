package com.lamnguyen.ticket_movie_nlu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.CareerInfoAdapter;
import com.lamnguyen.ticket_movie_nlu.bean.CareerInfo;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private RecyclerView directorRecyclerView;
    private RecyclerView actorRecyclerView;
    private List<CareerInfo> listDirectorInfo;

    private List<CareerInfo> listActorInfo;
    private LinearLayoutManager diretorLinearLayoutManager;

    private LinearLayoutManager actorLinearLayoutManager;

    private CareerInfoAdapter directorInfoAdapter;

    private CareerInfoAdapter actorInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        directorRecyclerView = findViewById(R.id.director_rv);
        actorRecyclerView = findViewById(R.id.actor_rv);

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

        diretorLinearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        actorLinearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);

        directorInfoAdapter = new CareerInfoAdapter();
        directorInfoAdapter.setData(listDirectorInfo);
        directorRecyclerView.setLayoutManager(diretorLinearLayoutManager);
        directorRecyclerView.setAdapter(directorInfoAdapter);

        actorInfoAdapter = new CareerInfoAdapter();
        actorInfoAdapter.setData(listActorInfo);
        actorRecyclerView.setLayoutManager(actorLinearLayoutManager);
        actorRecyclerView.setAdapter(actorInfoAdapter);
    }
}