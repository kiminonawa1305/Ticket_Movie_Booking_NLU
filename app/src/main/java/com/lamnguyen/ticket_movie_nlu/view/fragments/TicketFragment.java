package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.ViewPagerTicketAdapter;

public class TicketFragment extends Fragment {
    private TabLayout tabLDisplayTicket;
    private ViewPager2 vpDisplayTicket;

    public TicketFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLDisplayTicket = view.findViewById(R.id.tab_layout_display_ticket);
        vpDisplayTicket = view.findViewById(R.id.view_pager_display_ticket);

        vpDisplayTicket.setAdapter(new ViewPagerTicketAdapter(this.getActivity()));


        new TabLayoutMediator(tabLDisplayTicket, vpDisplayTicket, (tab, i) -> {
            switch (i) {
                case 0 -> {
                    tab.setText("Hiệu lực");
                }

                case 1 -> {
                    tab.setText("Hết hiệu lực");
                }
            }
        }).attach();
    }
}