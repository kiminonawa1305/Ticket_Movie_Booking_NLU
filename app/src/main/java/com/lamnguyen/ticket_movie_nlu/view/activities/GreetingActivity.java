package com.lamnguyen.ticket_movie_nlu.view.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

import com.lamnguyen.ticket_movie_nlu.R;

public class GreetingActivity extends AppCompatActivity {
    private static final String TAG = "GreetingActivity";

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        TextView tvGreeting = findViewById(R.id.text_view_greeting);
        int[] colors = new int[]{
                getColor(R.color.purple),
                getColor(R.color.red)
        };

        buildAlertDialogNetwork();

        float width = tvGreeting.getLayoutParams().width;
        float height = tvGreeting.getLayoutParams().height;
        Shader shader = new LinearGradient(
                0, height / 2, width, height / 2, colors, null, Shader.TileMode.CLAMP);
        tvGreeting.getPaint().setShader(shader);

        requestPermission();

        setTimerChangePageSign();
    }

    private void buildAlertDialogNetwork() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GreetingActivity.this);

        // Tạo một SpannableString từ tiêu đề
        SpannableString title = new SpannableString(getString(R.string.do_not_connect_to_network));

        // Thiết lập kích thước font cho tiêu đề
        title.setSpan(new AbsoluteSizeSpan(22, true), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        builder.setTitle(title)
                .setMessage(getString(R.string.request_connect_to_network))
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton(getString(R.string.re_try_check_password), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setTimerChangePageSign();
                    }
                })
                .setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        this.alertDialog = builder.create();
    }

    private void setTimerChangePageSign() {

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (isNetworkConnected()) {
                    Intent intent = new Intent(GreetingActivity.this, SignActivity.class);
                    startActivity(intent);
                    cancel();
                    finish();
                }
            }

            public void onFinish() {
                alertDialog.show();
            }
        }.start();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }
}