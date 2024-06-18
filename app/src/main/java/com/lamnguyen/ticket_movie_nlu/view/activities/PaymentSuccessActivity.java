package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.utils.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Random;

public class PaymentSuccessActivity extends AppCompatActivity {
    private Button btnBackToHome;
    private ImageView imageView;
    private TextView tvTimePay, tvCodePay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        btnBackToHome = findViewById(R.id.button_back_to_home);
        imageView = findViewById(R.id.image_view_payment_success);
        tvTimePay = findViewById(R.id.text_view_time_pay);
        tvCodePay = findViewById(R.id.text_view_code_pay);

        Glide.with(this)
                .asGif()
                .load(R.drawable.success)
                .into(imageView);

        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentSuccessActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tvTimePay.setText(DateTimeFormat.getDateTime(LocalDateTime.now()));
        String code = String.valueOf(new Random().nextInt(99999));
        int length = code.length();
        tvCodePay.setText(switch (length) {
            case 1 -> "0000" + code;
            case 2 -> "000" + code;
            case 3 -> "00" + code;
            case 4 -> "0" + code;
            default -> code;
        });
    }
}