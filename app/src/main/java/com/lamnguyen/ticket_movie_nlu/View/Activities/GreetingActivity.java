package com.lamnguyen.ticket_movie_nlu.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.lamnguyen.ticket_movie_nlu.R;

public class GreetingActivity extends AppCompatActivity {
    private static final String TAG = "GreetingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        TextView tvGreeting = findViewById(R.id.text_view_greeting);
        int[] colors = new int[]{
                getColor(R.color.purple),
                getColor(R.color.red)
        };
        float width = tvGreeting.getLayoutParams().width;
        float height = tvGreeting.getLayoutParams().height;
        Shader shader = new LinearGradient(
                0, height / 2, width, height / 2, colors, null, Shader.TileMode.CLAMP);
        tvGreeting.getPaint().setShader(shader);

        setTimerChangePageSign();
    }

    private void setTimerChangePageSign() {

        new CountDownTimer(1500, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent intent = new Intent(GreetingActivity.this, SignActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}