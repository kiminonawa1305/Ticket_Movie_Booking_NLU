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
    private List<CareerInfo> listDirectorInfo;
    private LinearLayoutManager linearLayoutManager;

    private CareerInfoAdapter careerInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        directorRecyclerView = findViewById(R.id.director_rv);

        listDirectorInfo = new ArrayList<>();
        listDirectorInfo.add(new CareerInfo("Director", "Shameik Moore"));
        listDirectorInfo.add(new CareerInfo("Director", "Kemp Powers"));
        listDirectorInfo.add(new CareerInfo("Director", "Dave Callaham"));

        linearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        careerInfoAdapter = new CareerInfoAdapter();
        careerInfoAdapter.setData(listDirectorInfo);
        directorRecyclerView.setLayoutManager(linearLayoutManager);
        directorRecyclerView.setAdapter(careerInfoAdapter);
    }
}