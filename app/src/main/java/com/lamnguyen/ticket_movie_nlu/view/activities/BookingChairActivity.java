package com.lamnguyen.ticket_movie_nlu.view.activities;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lamnguyen.ticket_movie_nlu.R;


public class BookingChairActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_moive_chair);

        getSharedPreferences("sign", MODE_PRIVATE).edit().putInt("userId", 2).apply();

        Bundle bundle = new Bundle();
        bundle.putInt("showtimeiId", 1);
        getSupportFragmentManager().setFragmentResult(getClass().getSimpleName(), bundle);
    }
}
