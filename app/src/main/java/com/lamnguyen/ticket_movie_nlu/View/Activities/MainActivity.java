package com.lamnguyen.ticket_movie_nlu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.view.fragments.FavouriteMovieFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.GoogleMapFragment;
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
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_love));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_map_pin));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_profile));
        fragmentManager = getSupportFragmentManager();

        getIntent().getBundleExtra("key");
        frmDisplayMain = fragmentManager.findFragmentById(R.id.fragment_display_main);
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
                    case 4:
                    case 5: {
                        break;
                    }
                }
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5: {
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
                transaction.replace(R.id.fragment_display_main, FavouriteMovieFragment.class, null);
                break;
            }
            case 4: {
                transaction.replace(R.id.fragment_display_main, GoogleMapFragment.class, null);
                break;
            }
            case 5: {
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
    protected void onResume() {
        super.onResume();


        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, GreetingActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }
}