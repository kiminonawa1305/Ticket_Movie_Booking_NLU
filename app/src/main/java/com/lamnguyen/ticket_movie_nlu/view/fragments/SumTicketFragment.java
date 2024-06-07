package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.ChairDTO;
import com.lamnguyen.ticket_movie_nlu.enums.ChairType;
import com.lamnguyen.ticket_movie_nlu.dto.PriceBoardDTO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SumTicketFragment extends Fragment {

    // Dummy data for selected seats, replace it with your actual data
    private List<ChairDTO> dtos;
    private Button btnPay;
    private TextView tvTotalPrice, tvTotalChair, tvSelectChair;
    private PriceBoardDTO priceBoardDTO;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dtos = new ArrayList<>();
        getParentFragmentManager().setFragmentResultListener(BookingChairFragment.class.getSimpleName(), this, (requestKey, result) -> {
            if (result.get("add") != null)
                dtos.add((ChairDTO) result.get("add"));

            if (result.get("remove") != null)
                dtos.remove((ChairDTO) result.get("remove"));

            if (result.get("price") != null)
                priceBoardDTO = (PriceBoardDTO) result.get("price");

            updateValue();
        });
        return inflater.inflate(R.layout.fragment_sum_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPay = view.findViewById(R.id.button_pay);
        tvTotalPrice = view.findViewById(R.id.text_view_total_price);
        tvSelectChair = view.findViewById(R.id.text_view_selected_chair);
        tvTotalChair = view.findViewById(R.id.tv_total_chair);

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

    private void updateValue() {
        tvTotalChair.setText(String.valueOf(dtos.size()));
        String selected = dtos.stream().map(ChairDTO::getName).collect(Collectors.joining(", "));
        tvSelectChair.setText(selected);
        Double totalPrice = dtos.stream().mapToDouble(chair -> {
            return chair.getType().equals(ChairType.COUPLE) ? priceBoardDTO.getCouple() :
                    chair.getType().equals(ChairType.SINGLE) ? priceBoardDTO.getSingle() :
                            chair.getType().equals(ChairType.VIP) ? priceBoardDTO.getVip() :
                                    0;
        }).sum();
        tvTotalPrice.setText(formatter.format(totalPrice));
    }

    @Override
    public void onStop() {
        super.onStop();
        dtos.clear();
    }
}
