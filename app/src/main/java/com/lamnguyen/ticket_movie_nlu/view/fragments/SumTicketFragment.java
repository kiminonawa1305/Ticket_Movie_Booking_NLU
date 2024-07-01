package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.ChairDTO;
import com.lamnguyen.ticket_movie_nlu.dto.PriceBoardDTO;
import com.lamnguyen.ticket_movie_nlu.view.activities.BookingChairActivity;
import com.lamnguyen.ticket_movie_nlu.view.activities.PaymentActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SumTicketFragment extends Fragment {

    // Dummy data for selected seats, replace it with your actual data
    private List<ChairDTO> chairSelected;
    private Button btnPay;
    private TextView tvTotalPrice, tvTotalChair, tvSelectChair;
    private PriceBoardDTO priceBoardDTO;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private int showtimeiId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        chairSelected = new ArrayList<>();
        getParentFragmentManager().setFragmentResultListener(BookingChairFragment.class.getSimpleName(), this, (requestKey, result) -> {
            if (result.get("add") != null)
                chairSelected.add((ChairDTO) result.get("add"));

            if (result.get("remove") != null)
                chairSelected.remove((ChairDTO) result.get("remove"));

            if (result.get("price") != null)
                priceBoardDTO = (PriceBoardDTO) result.get("price");

            if (result.get("showtimeId") != null)
                showtimeiId = result.getInt("showtimeId");

            updateValue();
        });
        return inflater.inflate(R.layout.fragment_sum_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.init(view);
        this.event();
    }

    private void init(View view) {
        btnPay = view.findViewById(R.id.button_pay);
        tvTotalPrice = view.findViewById(R.id.text_view_total_price);
        tvSelectChair = view.findViewById(R.id.text_view_selected_chair);
        tvTotalChair = view.findViewById(R.id.tv_total_chair);
    }

    private void event() {
        btnPay.setOnClickListener(v -> {
            if (chairSelected.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn ghế!", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<Integer> ids = (ArrayList<Integer>) chairSelected.stream().map(ChairDTO::getId).collect(Collectors.toList());
            ArrayList<String> names = (ArrayList<String>) chairSelected.stream().map(ChairDTO::getName).collect(Collectors.toList());
            ArrayList<String> types = (ArrayList<String>) chairSelected.stream().map(type -> type.getType().toString()).collect(Collectors.toList());
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("names", names);
            bundle.putIntegerArrayList("ids", ids);
            bundle.putStringArrayList("types", types);
            bundle.putString("totalPrice", formatter.format(totalPrice()));
            bundle.putInt("showtimeId", showtimeiId);
            Intent intent = new Intent(getContext(), PaymentActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        });
    }

    private void updateValue() {
        tvTotalChair.setText(String.valueOf(chairSelected.size()));
        String selected = chairSelected.stream().map(ChairDTO::getName).collect(Collectors.joining(", "));
        tvSelectChair.setText(selected);
        Double totalPrice = totalPrice();
        tvTotalPrice.setText(formatter.format(totalPrice));
    }

    private Double totalPrice() {
        return chairSelected.stream().mapToDouble(chair -> {
            return switch (chair.getType()) {
                case COUPLE -> priceBoardDTO.getCouple();
                case SINGLE -> priceBoardDTO.getSingle();
                case VIP -> priceBoardDTO.getVip();
                default -> 0;
            };
        }).sum();
    }

    @Override
    public void onStop() {
        super.onStop();
        chairSelected.clear();
    }
}
