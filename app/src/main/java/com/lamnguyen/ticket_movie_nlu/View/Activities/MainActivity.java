package com.lamnguyen.ticket_movie_nlu.View.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lamnguyen.ticket_movie_nlu.Controller.Service.MovieInfoService;
import com.lamnguyen.ticket_movie_nlu.View.Adapters.DisplayPageTicketMovieAdapter;
import com.lamnguyen.ticket_movie_nlu.R;

public class MainActivity extends AppCompatActivity {
    private TabLayout tlDisplayTicketMovie;
    private ViewPager2 vpgDisplayTicketMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool_bar_back);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // Hiển thị nút back
        }

        this.init();
        setupTabLayoutDisplayTicketMovie(tlDisplayTicketMovie, vpgDisplayTicketMovie);

    }

    private void init(){
        tlDisplayTicketMovie = findViewById(R.id.tab_layout_display_ticket_movie);
        vpgDisplayTicketMovie = findViewById(R.id.view_pager_display_ticket_movie);
        vpgDisplayTicketMovie.setAdapter(new DisplayPageTicketMovieAdapter(this));
    }

    private void setupTabLayoutDisplayTicketMovie(TabLayout tlDisplayTicketMovie, ViewPager2 vpgDisplayTicketMovie){
        new TabLayoutMediator(tlDisplayTicketMovie, vpgDisplayTicketMovie,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Đang chiếu");
                            break;
                        case 1:
                            tab.setText("Sắp chiếu");
                            break;
                        case 2:
                            tab.setText("Nổi bật");
                            break;
                    }
                }
        ).attach();
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
        MovieInfoService.getInstance().getMovie(this, "Hello", 1);
    }
}