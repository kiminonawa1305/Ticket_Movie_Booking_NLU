package com.lamnguyen.ticket_movie_nlu.view.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lamnguyen.ticket_movie_nlu.R;


public class BookingChairActivity extends AppCompatActivity {

    private ImageView ivBackToShowtimeByCinema;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_moive_chair);

        ivBackToShowtimeByCinema = findViewById(R.id.image_view_back_showtime_by_cinema);

        Bundle bundle = new Bundle();
        bundle.putInt("showtimeId", getIntent().getIntExtra("showtimeId", -1));
        getSupportFragmentManager().setFragmentResult(getClass().getSimpleName(), bundle);

        ivBackToShowtimeByCinema.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putBoolean("back", true);
            getSupportFragmentManager().setFragmentResult(getClass().getSimpleName(), b);
            Intent intent = new Intent(this, ShowtimeActivity.class);
            int movieId = getIntent().getIntExtra("id", -1);
            intent.putExtra("id", movieId);
            startActivity(intent);
            finish();
        });
    }
}
