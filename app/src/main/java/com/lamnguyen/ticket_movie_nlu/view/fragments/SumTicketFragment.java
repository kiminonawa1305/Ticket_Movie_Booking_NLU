package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lamnguyen.ticket_movie_nlu.R;

import java.util.ArrayList;

public class SumTicketFragment extends Fragment {

    // Dummy data for selected seats, replace it with your actual data
    private ArrayList<String> selectedSeats = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sum_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnPay = view.findViewById(R.id.button_pay);
        // Handle payment button click
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your payment logic here
                // For example, navigate to payment screen
                // Or initiate payment process
            }
        });
    }
}
