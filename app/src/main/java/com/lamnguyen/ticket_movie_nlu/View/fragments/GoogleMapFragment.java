package com.lamnguyen.ticket_movie_nlu.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.databinding.ActivityMapsBinding;

public class GoogleMapFragment extends Fragment {
    private static final String TAG = "GoogleMapFragment";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ValueAnimator valueAnimatorZoom;

    private static final LatLng LAT_LNG_CINESTAR = new LatLng(10.875169012879635, 106.80071966748321);

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(GoogleMapFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(GoogleMapFragment.this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GoogleMapFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                Toast.makeText(GoogleMapFragment.this.getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                return;
            }
            googleMap.setMyLocationEnabled(true);

            /*Tạo check point*/
            googleMap.addMarker(new MarkerOptions().position(LAT_LNG_CINESTAR).title("Nhà văn hóa sinh viên!"));
            /*zoom vào tạo độ mong muốn*/
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LAT_LNG_CINESTAR, 18));

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    animationMarkerClick(marker.getPosition());
                    return true;
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        valueAnimatorZoom = ValueAnimator.ofFloat(0, 18);
        valueAnimatorZoom.setDuration(2000);
    }

    private void animationMarkerClick(LatLng position) {
        valueAnimatorZoom.setFloatValues(mMap.getCameraPosition().zoom, 18);
        valueAnimatorZoom.addUpdateListener(animation -> {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, (float) animation.getAnimatedValue()));
        });

        valueAnimatorZoom.start();
    }
}