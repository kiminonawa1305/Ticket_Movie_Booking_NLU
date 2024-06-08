package com.lamnguyen.ticket_movie_nlu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
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
    private static final String TAG = "MainActivity";
    public static final int FRAGMENT_MOVIE = 1,
            FRAGMENT_TICKET = 2,
            FRAGMENT_FAVOURITE = 3,
            FRAGMENT_MAP = 4,
            FRAGMENT_PROFILE = 5;
    private static final String FRAGMENT_ID = "fragment_id";

    public static void saveFragmentId(Intent intent, int fragmentId) {
        intent.putExtra(FRAGMENT_ID, fragmentId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_MOVIE, R.drawable.ic_movie));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_TICKET, R.drawable.ic_ticket));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_FAVOURITE, R.drawable.ic_love));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_MAP, R.drawable.ic_map_pin));
        bottomNavigation.add(new MeowBottomNavigation.Model(FRAGMENT_PROFILE, R.drawable.ic_profile));
        fragmentManager = getSupportFragmentManager();

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
                    case FRAGMENT_MOVIE:
                    case FRAGMENT_TICKET:
                    case FRAGMENT_FAVOURITE:
                    case FRAGMENT_MAP:
                    case FRAGMENT_PROFILE: {
                        break;
                    }
                }
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case FRAGMENT_MOVIE:
                    case FRAGMENT_TICKET:
                    case FRAGMENT_FAVOURITE:
                    case FRAGMENT_MAP:
                    case FRAGMENT_PROFILE: {
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
            case FRAGMENT_MOVIE: {
                transaction.replace(R.id.fragment_display_main, MovieFragment.class, null);
                break;
            }
            case FRAGMENT_TICKET: {
                boolean avail = getIntent().getBooleanExtra(getString(R.string.avail_ticket), true);
                Bundle args = new Bundle();
                args.putBoolean(getString(R.string.avail_ticket), avail);
                transaction.replace(R.id.fragment_display_main, TicketFragment.class, args);
                break;
            }
            case FRAGMENT_FAVOURITE: {
                transaction.replace(R.id.fragment_display_main, FavouriteMovieFragment.class, null);
                break;
            }
            case FRAGMENT_MAP: {
                transaction.replace(R.id.fragment_display_main, GoogleMapFragment.class, null);
                break;
            }
            case FRAGMENT_PROFILE: {
                transaction.replace(R.id.fragment_display_main, ProfileFragment.class, null);
                break;
            }
            default:
                break;
        }
        if (id >= 1 && id <= 5) bottomNavigation.show(id, true);
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