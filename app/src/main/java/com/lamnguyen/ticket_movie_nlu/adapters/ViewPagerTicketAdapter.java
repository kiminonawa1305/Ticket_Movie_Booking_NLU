package com.lamnguyen.ticket_movie_nlu.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lamnguyen.ticket_movie_nlu.view.fragments.ViewPagerTicketFragment;

public class ViewPagerTicketAdapter extends FragmentStateAdapter {
    public ViewPagerTicketAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        Bundle args = new Bundle();
        // The object is just an integer.
        args.putBoolean("avail", position != 1);
        fragment = new ViewPagerTicketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
