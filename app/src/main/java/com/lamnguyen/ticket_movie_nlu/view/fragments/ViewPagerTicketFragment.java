package com.lamnguyen.ticket_movie_nlu.view.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.TicketAdapter;
import com.lamnguyen.ticket_movie_nlu.api.TicketApi;
import com.lamnguyen.ticket_movie_nlu.dto.TicketDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import java.util.List;

public class ViewPagerTicketFragment extends Fragment {
    public static final String TAG = ViewPagerTicketFragment.class.getSimpleName();
    private RecyclerView rclTicket;
    private Dialog dialog;
    private TicketApi ticketApi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ticketApi = TicketApi.getInstance();
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

        dialog = DialogLoading.newInstance(this.getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        boolean avail = bundle.getBoolean(getString(R.string.avail_ticket));
        loadTicket(avail);
    }

    private void loadTicket(boolean avail) {
        DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
        ticketApi.call(this.getContext(), avail, new CallAPI.CallAPIListener<List<TicketDTO>>() {

            @Override
            public void completed(List<TicketDTO> ticketDTOS) {
                dialog.dismiss();
                if (ticketDTOS.isEmpty())
                    Toast.makeText(getContext(), getString(R.string.no_ticket), Toast.LENGTH_SHORT).show();
                rclTicket.setAdapter(new TicketAdapter(ticketDTOS, avail));
            }

            @Override
            public void error(Object error) {
                dialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                    Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
