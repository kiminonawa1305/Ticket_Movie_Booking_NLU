package com.lamnguyen.ticket_movie_nlu.view.activities;

import static com.android.volley.Request.Method.POST;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.bean.User;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private TextView tvPaymentName;
    private TextView tvPaymentPhoneNumber;
    private TextView tvPaymentEmail;
    private TextView tvCinema;
    private TextView tvSingleChairs, tvCoupleChairs, tvVipChairs;
    private TextView tvTotalPay;
    private Button btnPay;
    private String totalPrice;
    private ArrayList<String> names, types;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment); // đổi thành tên file layout của bạn
        init();
        event();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        names = bundle.getStringArrayList("names");
        types = bundle.getStringArrayList("types");
        ids = bundle.getIntegerArrayList("ids");
        totalPrice = bundle.getString("totalPrice");

        loadInfoUser();
        loadNameChairs(names, types);
        loadTotalPrice(totalPrice);
    }

    private void init() {
        tvPaymentName = findViewById(R.id.text_view_payment_name);
        tvPaymentPhoneNumber = findViewById(R.id.text_view_payment_phone_number);
        tvPaymentEmail = findViewById(R.id.text_view_payment_email);
        tvCinema = findViewById(R.id.text_view_payment_cinema);
        tvSingleChairs = findViewById(R.id.text_view_single_chair);
        tvCoupleChairs = findViewById(R.id.text_view_couple_chair);
        tvVipChairs = findViewById(R.id.text_view_vip_chair);
        tvTotalPay = findViewById(R.id.text_view_total_pay);
        btnPay = findViewById(R.id.button_pay);
    }

    private void event() {
        btnPay.setOnClickListener(v -> {
            buyTickets(ids);
            Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
            startActivity(intent);
        });
    }

    private void loadInfoUser() {
        User user = SharedPreferencesUtils.getUser(this);
        tvPaymentEmail.setText(user.getEmail());
        tvPaymentName.setText(user.getFullName());
        tvPaymentPhoneNumber.setText(user.getPhone());
    }

    private void loadNameChairs(ArrayList<String> names, ArrayList<String> types) {
        StringBuilder singleChairs = new StringBuilder();
        StringBuilder coupleChairs = new StringBuilder();
        StringBuilder vipChairs = new StringBuilder();
        for (String name : names) {
            if (types.get(names.indexOf(name)).equalsIgnoreCase("Single".toLowerCase())) {
                singleChairs.append(name).append(", ");
            } else if (types.get(names.indexOf(name)).equalsIgnoreCase("Couple".toLowerCase())) {
                coupleChairs.append(name).append(", ");
            } else if (types.get(names.indexOf(name)).equalsIgnoreCase("VIP".toLowerCase())) {
                vipChairs.append(name).append(", ");
            }
        }

        formatString(singleChairs);
        formatString(coupleChairs);
        formatString(vipChairs);

        tvSingleChairs.setText(singleChairs.toString());
        tvCoupleChairs.setText(coupleChairs.toString());
        tvVipChairs.setText(vipChairs.toString());
    }

    private void loadTotalPrice(String totalPrice) {
        tvTotalPay.setText(totalPrice);
    }

    private void formatString(StringBuilder stringBuilder) {
        if (stringBuilder.toString().endsWith(", "))
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    }

    public void buyTickets(ArrayList<Integer> listId) {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("chairIds", listId);

            jsonObject.put("customerId", SharedPreferencesUtils.getUserID(this));

            CallAPI.callJsonObjectRequest(this, CallAPI.URL_WEB_SERVICE + "/ticket/api/buy", "", jsonObject, null, POST, (response) -> {
                Log.i("SumTicketFragment", "buyTickets: " + response);
            }, error -> {
                Log.i("SumTicketFragment", "buyTickets: " + error.getMessage());
            });

        } catch (JSONException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
