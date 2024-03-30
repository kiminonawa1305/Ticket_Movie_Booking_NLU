package com.lamnguyen.ticket_movie_nlu.view.fragments;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.utils.PolylineEncodingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleMapFragment extends Fragment {
    private static final String TAG = "GoogleMapFragment";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ValueAnimator valueAnimatorZoom;
    private Location currentLocation;
    private SupportMapFragment mapFragment;

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

            mMap.setMyLocationEnabled(true);

            mMap.setOnMyLocationChangeListener(location -> {
                if (location == null) return;
                if (currentLocation == null) currentLocation = location;
                direct(googleMap, new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), LAT_LNG_CINESTAR);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 18));
            });

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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
        return inflater.inflate(R.layout.fragment_google_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        valueAnimatorZoom = ValueAnimator.ofFloat(0, 18);
        valueAnimatorZoom.setDuration(2000);
    }

    public void setListMarker(List<LatLng> latLngList, List<String> titles) {
        for (int index = 0; index < latLngList.size(); index++) {
            mMap.addMarker(new MarkerOptions().position(latLngList.get(index)).title(titles.get(index)));
        }
    }

    private void animationMarkerClick(LatLng position) {
        valueAnimatorZoom.setFloatValues(mMap.getCameraPosition().zoom, 18);
        valueAnimatorZoom.addUpdateListener(animation -> {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, (float) animation.getAnimatedValue()));
        });

        valueAnimatorZoom.start();
    }

    public void direct(GoogleMap googleMap, LatLng origin, LatLng destination) {
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = "https://routes.googleapis.com/directions/v2:computeRoutes";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = (JSONObject) response.getJSONArray("routes").get(0);
                    String distanceMeters = object.getString("distanceMeters");
                    String duration = object.getString("duration");
                    String encodedPolyline = object.getJSONObject("polyline").getString("encodedPolyline");
//                    Log.i("MainActivity", "distanceMeters: " + distanceMeters);
//                    Log.i("MainActivity", "duration: " + duration);
//                    Log.i("MainActivity", "encodedPolyline: " + encodedPolyline);
                    PolylineOptions polylineOptions = new PolylineOptions();
                    for (LatLng latLng : PolylineEncodingUtil.decode(encodedPolyline))
                        polylineOptions.add(latLng);
                    polylineOptions.width(5) // Độ dày của đường đường dẫn
                            .color(Color.RED); // Màu sắc của đường đường dẫn


                    googleMap.addPolyline(polylineOptions);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("{")
                        .append("\"origin\":{")
                        .append("\"location\": {")
                        .append("\"latLng\":{")
                        .append("\"latitude\": ")
                        .append(origin.latitude)
                        .append(",")
                        .append("\"longitude\": ")
                        .append(origin.longitude)
                        .append("}")
                        .append("}")
                        .append("},")
                        .append("\"destination\":{")
                        .append("\"location\": {")
                        .append("\"latLng\":{")
                        .append("\"latitude\": ")
                        .append(destination.latitude)
                        .append(",")
                        .append("\"longitude\": ")
                        .append(destination.longitude)
                        .append("}")
                        .append("}")
                        .append("},")
                        .append("\"routingPreference\":\"TRAFFIC_AWARE\",")
                        .append("\"travelMode\":\"DRIVE\"")
                        .append("}");

                return stringBuilder.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> result = new HashMap<>();
                Map<String, String> headers = super.getHeaders();
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    result.put(header.getKey(), header.getValue());
                }
                result.put("User-Agent", "Mozilla/5.0");
                result.put("Content-Type", "application/json");
                result.put("X-Goog-Api-Key", "AIzaSyAkJ-NbtMqrX05P5LzHQr86aAZeJ6iEmuA");
                result.put("X-Goog-FieldMask", "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline");
                return result;
            }
        };


        queue.add(request);
    }
}