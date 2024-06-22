package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.PriceManageAdapter;
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceManageActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manage);

    }



}
