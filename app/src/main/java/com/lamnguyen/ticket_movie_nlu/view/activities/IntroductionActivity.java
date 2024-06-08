package com.lamnguyen.ticket_movie_nlu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.view.fragments.MovieFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.ProfileFragment;

public class IntroductionActivity extends AppCompatActivity {

    private ImageButton backToProfileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        backToProfileBtn = findViewById(R.id.back_to_profile_btn);
        backToProfileBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(IntroductionActivity.this, MainActivity.class);
            intent.putExtra("fragment_id", 5);  // 5 is the ID for ProfileFragment
            startActivity(intent);
            finish();
        });
    }
}