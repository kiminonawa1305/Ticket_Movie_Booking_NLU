package com.lamnguyen.ticket_movie_nlu.view.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.User;
import com.lamnguyen.ticket_movie_nlu.enums.RoleUser;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;
import com.lamnguyen.ticket_movie_nlu.view.activities.GreetingActivity;
import com.lamnguyen.ticket_movie_nlu.view.fragments.ProfileFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.admin.AddScheduleActivity;
import com.lamnguyen.ticket_movie_nlu.view.fragments.admin.DashBoardFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.admin.MovieManagerFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.admin.PriceManagerFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.admin.UserManagerFragment;

public class AdminActivity extends AppCompatActivity {
    private MeowBottomNavigation bottomNavigation;
    private FragmentManager fragmentManager;
    private static final String TAG = "AdminActivity";
    public static final int FRAGMENT_DASH_BOARD = 1,
            FRAGMENT_USER_MANAGER = 2,
            FRAGMENT_PRICE_MANAGER = 3,
            FRAGMENT_MOVIE_MANAGER = 4,
            FRAGMENT_SHOWTIME_MANAGER = 5,
            FRAGMENT_PROFILE = 6;

    private static final String FRAGMENT_ID = "fragment_id";

    public static void saveFragmentId(Intent intent, int fragmentId) {
        intent.putExtra(FRAGMENT_ID, fragmentId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferencesUtils.setCurrentMode(this, RoleUser.ADMIN);
        this.init();
        this.eventNavigation();

        Intent intent = getIntent();
        if (intent != null) {
            int fragmentId = intent.getIntExtra(FRAGMENT_ID, -1);
            changeFragment(fragmentId);
        }
    }

    private void init() {
        bottomNavigation = findViewById(R.id.meow_bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_DASH_BOARD, R.drawable.ic_dash_board));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_USER_MANAGER, R.drawable.ic_user_manager));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_PRICE_MANAGER, R.drawable.ic_price_manager));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_MOVIE_MANAGER, R.drawable.ic_movie_manager));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_SHOWTIME_MANAGER, R.drawable.ic_showtime_manager));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_PROFILE, R.drawable.ic_profile));
        fragmentManager = getSupportFragmentManager();
    }

    private void eventNavigation() {
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                changeFragment(item.getId());
            }
        });

        bottomNavigation.setOnShowListener(item -> {
            switch (item.getId()) {
                case FRAGMENT_DASH_BOARD:
                case FRAGMENT_USER_MANAGER:
                case FRAGMENT_MOVIE_MANAGER:
                case FRAGMENT_SHOWTIME_MANAGER:
                case FRAGMENT_PRICE_MANAGER:
                case FRAGMENT_PROFILE: {
                    break;
                }
            }
        });

        bottomNavigation.setOnReselectListener(item -> {
            switch (item.getId()) {
                case FRAGMENT_DASH_BOARD:
                case FRAGMENT_USER_MANAGER:
                case FRAGMENT_MOVIE_MANAGER:
                case FRAGMENT_SHOWTIME_MANAGER:
                case FRAGMENT_PRICE_MANAGER:
                case FRAGMENT_PROFILE: {
                    break;
                }
            }
        });

        bottomNavigation.show(1, true);
    }

    private void changeFragment(int id) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (id) {
            case FRAGMENT_DASH_BOARD ->
                    transaction.replace(R.id.fragment_display_main, DashBoardFragment.class, null);
            case FRAGMENT_USER_MANAGER ->
                    transaction.replace(R.id.fragment_display_main, UserManagerFragment.class, null);
            case FRAGMENT_MOVIE_MANAGER ->
                    transaction.replace(R.id.fragment_display_main, MovieManagerFragment.class, null);
            case FRAGMENT_SHOWTIME_MANAGER -> {
            }
            case FRAGMENT_PRICE_MANAGER ->
                    transaction.replace(R.id.fragment_display_main, PriceManagerFragment.class, null);
            case FRAGMENT_PROFILE ->
                    transaction.replace(R.id.fragment_display_main, ProfileFragment.class, null);
            default -> {
                transaction.replace(R.id.fragment_display_main, DashBoardFragment.class, null);
            }
        }
        if (id >= 1 && id <= 6) bottomNavigation.show(id, true);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = SharedPreferencesUtils.getUser(this);
        if (user == null || user.getRole().equals(RoleUser.USER)) {
            Intent intent = new Intent(this, GreetingActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }
}