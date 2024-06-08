package com.lamnguyen.ticket_movie_nlu.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lamnguyen.ticket_movie_nlu.view.fragments.ViewPagerTicketFragment;

public class ViewPagerTicketAdapter extends FragmentStateAdapter {
    private boolean avail;

    public ViewPagerTicketAdapter(@NonNull FragmentActivity fragmentActivity, boolean avail) {
        super(fragmentActivity);
        this.avail = avail;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle args = new Bundle();
        Fragment fragment;
        fragment = new ViewPagerTicketFragment();
        fragment.setArguments(args);
        switch (position) {
            case 0:
                args.putBoolean("avail", true);
                break;
            case 1:
                args.putBoolean("avail", false);
                break;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
