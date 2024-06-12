package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.ShowTimeAdapter;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeByCinema;
import com.lamnguyen.ticket_movie_nlu.service.cinema.CinemaService;
import com.lamnguyen.ticket_movie_nlu.service.google.GoogleMapService;
import com.lamnguyen.ticket_movie_nlu.service.movie.MovieService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.view.fragments.GoogleMapFragment;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeActivity extends AppCompatActivity {
    private RecyclerView rvShowTime;
    private ShowTimeAdapter showTimeAdapter;
    private MovieService movieService;
    private Location location;
    private List<CinemaService.CinemaLatLng> cinemaLatLags;
    private GoogleMapService googleMapService;
    private List<ShowtimeByCinema> showtimeList;

    private static final String TAG = "ShowtimeActivity";

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);

        rvShowTime = findViewById(R.id.recycler_view_showtime);
        rvShowTime.setLayoutManager(new LinearLayoutManager(this));

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        movieService = MovieService.getInstance();

        googleMapService = GoogleMapService.getInstance();

        cinemaLatLags = CinemaService.getInstance().getCinemaLatLag(ShowtimeActivity.this);

        getLocation();

        loadShowtimeByCinema();
    }

    private void loadShowtimeByCinema() {
        movieService.loadShowtime(1, this, new CallAPI.CallAPIListener<List<ShowtimeByCinema>>() {
            @Override
            public void completed(List<ShowtimeByCinema> showTimeByCinemas) {
                showTimeByCinemas.stream().forEach(showTimeByCinema -> {
                    cinemaLatLags.stream().filter(cinemaLatLng -> cinemaLatLng.getName().equals(showTimeByCinema.getNameCinema())).forEach(cinemaLatLng -> {
                        getDistance(showTimeByCinema, cinemaLatLng.getLatLng());
                    });
                });

                showTimeAdapter = new ShowTimeAdapter(showTimeByCinemas);
                rvShowTime.setAdapter(showTimeAdapter);
            }

            @Override
            public void error(Object error) {
                Log.e(TAG, "erorr: " + error.toString());
                Toast.makeText(ShowtimeActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            ShowtimeActivity.this.location = location;
                        }
                    }
                });
    }

    public void getDistance(ShowtimeByCinema showtimeByCinema, LatLng destination) {
        googleMapService.getDistanceMeters(ShowtimeActivity.this.getBaseContext(), new LatLng(location.getLatitude(), location.getLongitude()), destination, new CallAPI.CallAPIListener<String>() {
            @Override
            public void completed(String s) {
                showtimeByCinema.setDistance(s);
                showTimeAdapter.notifyDataSetChanged();
            }

            @Override
            public void error(Object error) {

            }
        });
    }
}