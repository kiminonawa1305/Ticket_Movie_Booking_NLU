package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.utils.Convert;

import java.util.ArrayList;
import java.util.List;

public class ChooseChairFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = view.findViewById(R.id.grid_view_seats);
        int numberColumns = 12;
        gridView.setNumColumns(numberColumns);
        gridView.getLayoutParams().width = (int) (numberColumns * Convert.convertToPx(this.getContext(), 55));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.item_seat, generateSeatList());
        gridView.setAdapter(adapter);
    }

    private List<String> generateSeatList() {
        // Tạo danh sách ghế dựa trên ma trận ghế trong rạp chiếu phim
        // Ví dụ: ["A1", "A2", "A3", ...]
        List<String> seatList = new ArrayList<>();
        for (char row = 'A'; row <= 'Z'; row++) {
            for (int col = 1; col <= 12; col++) {
                seatList.add(String.valueOf(row) + col);
            }
        }
        return seatList;
    }
}
