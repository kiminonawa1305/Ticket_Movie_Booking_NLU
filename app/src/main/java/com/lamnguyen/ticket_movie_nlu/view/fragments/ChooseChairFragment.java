package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.SeatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChooseChairFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_seat_item_list, container, false);

        GridView gridView = rootView.findViewById(R.id.gridView);
        List<String> seatList = generateSeatList(); // Hàm để tạo danh sách ghế
        SeatAdapter adapter = new SeatAdapter(requireContext(), seatList);
        gridView.setAdapter(adapter);

        return rootView;
    }

    private List<String> generateSeatList() {
        // Tạo danh sách ghế dựa trên ma trận ghế trong rạp chiếu phim
        // Ví dụ: ["A1", "A2", "A3", ...]
        List<String> seatList = new ArrayList<>();
        for (char row = 'A'; row <= 'K'; row++) {
            for (int col = 1; col <= 12; col++) {
                seatList.add(String.valueOf(row) + col);
            }
        }
        return seatList;
    }
}
