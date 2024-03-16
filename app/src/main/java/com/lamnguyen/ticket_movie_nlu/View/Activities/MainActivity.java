package com.lamnguyen.ticket_movie_nlu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.view.fragments.CinemaFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.MovieFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.ProfileFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.TicketFragment;


public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;
    private FragmentManager fragmentManager;
    private Fragment frmDisplayMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
        this.eventNavigation();
    }

    private void init() {
        bottomNavigation = findViewById(R.id.meow_bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_movie));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_ticket));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_cinema));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_profile));
        fragmentManager = getSupportFragmentManager();
    }

    private void eventNavigation() {
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                changeFragment(item.getId());
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4: {
                        break;
                    }
                }
            }
        });

        bottomNavigation.show(1, true);
    }

    private void changeFragment(int id) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (id) {
            case 1: {
                transaction.replace(R.id.fragment_display_main, MovieFragment.class, null);
                break;
            }
            case 2: {
                transaction.replace(R.id.fragment_display_main, TicketFragment.class, null);
                break;
            }
            case 3: {
                transaction.replace(R.id.fragment_display_main, CinemaFragment.class, null);
                break;
            }
            case 4: {
                transaction.replace(R.id.fragment_display_main, ProfileFragment.class, null);
                break;
            }
        }
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Xử lý khi nút back được nhấn
            finish();  // Ví dụ: Đóng Activity hiện tại
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}