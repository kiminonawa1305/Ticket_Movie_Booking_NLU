package com.lamnguyen.ticket_movie_nlu.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.lamnguyen.ticket_movie_nlu.service.LatLngService;
import com.lamnguyen.ticket_movie_nlu.view.fragments.GoogleMapFragment;

public class OnMapReadyCallbackImpl implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMapFragment googleMapFragment;

    public OnMapReadyCallbackImpl(GoogleMapFragment googleMapFragment) {
        this.googleMapFragment = googleMapFragment;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMapFragment.setGoogleMap(googleMap);
        googleMap.setMapStyle(new MapStyleOptions(this.googleMapFragment.getStyle()));
        if (ActivityCompat.checkSelfPermission(this.googleMapFragment.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.googleMapFragment.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.googleMapFragment.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

            Toast.makeText(this.googleMapFragment.getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        googleMap.setOnMyLocationChangeListener(location -> {
            this.googleMapFragment.setCurrentLocation(LatLngService.createLatLng(location));

            if (this.googleMapFragment.getDirect())
                this.googleMapFragment.getGoogleMapService().drawWay(this.googleMapFragment, googleMap, this.googleMapFragment.getCurrentLocation(), this.googleMapFragment.getDestination());
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                clickMarket(marker.getPosition());
                return true;
            }
        });

        if (this.googleMapFragment.getCurrentLocation() != null)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.googleMapFragment.getCurrentLocation(), 18));
    }

    private void clickMarket(LatLng position) {
        this.googleMapFragment.getValueAnimatorZoom().setFloatValues(this.googleMapFragment.getGoogleMap().getCameraPosition().zoom, 18);
        this.googleMapFragment.getValueAnimatorZoom().addUpdateListener(animation -> {
            this.googleMapFragment.getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(position, (float) animation.getAnimatedValue()));
        });

        this.googleMapFragment.getValueAnimatorZoom().start();
    }
}
