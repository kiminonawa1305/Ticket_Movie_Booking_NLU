package com.lamnguyen.ticket_movie_nlu.view.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.TicketAdapter;
import com.lamnguyen.ticket_movie_nlu.service.Ticket.TicketService;

import java.time.LocalDate;

//Khởi tạo Fragment cho ViewPager2
public class ViewPagerTicketFragment extends Fragment {
    public static final String TAG = ViewPagerTicketFragment.class.getSimpleName();
    private RecyclerView rclTicket;
    private Dialog dialog;
    private TicketService ticketService;

    public ViewPagerTicketFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ticketService = TicketService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rclTicket = view.findViewById(R.id.recycler_view_display_ticket);
        rclTicket.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));

        rclTicket.setAdapter(new TicketAdapter(ticketService.getTicketsDemo()));
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMovieShowtime(LocalDate.now().plusDays(getArguments().getInt("position")));
    }

    private void loadMovieShowtime(LocalDate dateTime) {
//        DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
    }
}
