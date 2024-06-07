package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lamnguyen.ticket_movie_nlu.R;

public class PaymentActivity extends AppCompatActivity {

    private TextView movieNameTextView;
    private TextView movieTypeTextView;
    private TextView paymentNameTextView;
    private TextView phoneNumberTextView;
    private TextView emailTextView;
    private TextView cinemaTextView;
    private TextView chairTextView;
    private TextView totalPayTextView;
    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment); // đổi thành tên file layout của bạn

        movieNameTextView = findViewById(R.id.text_view_payment_movie_name);
        movieTypeTextView = findViewById(R.id.text_view_payment_movie_type);
        paymentNameTextView = findViewById(R.id.text_view_payment_name);
        phoneNumberTextView = findViewById(R.id.text_view_payment_phone_number);
        emailTextView = findViewById(R.id.text_view_payment_email);
        cinemaTextView = findViewById(R.id.text_view_payment_cinema);
        chairTextView = findViewById(R.id.text_view_payment_chair);
        totalPayTextView = findViewById(R.id.textView23);
        payButton = findViewById(R.id.button_pay);


        // Tính tổng tiền và cập nhật totalPayTextView
        calculateAndDisplayTotalPay();

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
                startActivity(intent);
            }
        });
    }
    private void calculateAndDisplayTotalPay() {
        String chairs = chairTextView.getText().toString();
        int totalPay = calculateTotalPay(chairs, 45000); // Giá mỗi ghế là 45k
        totalPayTextView.setText(String.format("%,d VND", totalPay));
    }

    private int calculateTotalPay(String chairs, int pricePerChair) {
        if (chairs.isEmpty()) {
            return 0;
        }
        String[] chairArray = chairs.split(",");
        int numberOfChairs = chairArray.length;
        return numberOfChairs * pricePerChair;
    }
}
