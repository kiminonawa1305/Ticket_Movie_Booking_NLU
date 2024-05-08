package com.lamnguyen.ticket_movie_nlu.service.google;

import android.graphics.Color;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.lamnguyen.ticket_movie_nlu.request.GoogleMapRoutesHttpRequest;
import com.lamnguyen.ticket_movie_nlu.utils.PolylineEncodingUtil;
import com.lamnguyen.ticket_movie_nlu.view.fragments.GoogleMapFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GoogleMapService {
    private static GoogleMapService instance;
    private Polyline polylinePrevious;
    private GoogleMapRoutesHttpRequest request;

    public static GoogleMapService getInstance() {
        if (instance == null) instance = new GoogleMapService();
        return instance;
    }

    private GoogleMapService() {

    }

    public void drawWay(Fragment fragment, GoogleMap googleMap, LatLng origin, LatLng destination) {
        GoogleMapRoutesHttpRequest request = new GoogleMapRoutesHttpRequest(fragment.getContext(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = (JSONObject) response.getJSONArray("routes").get(0);
                    String distanceMeters = object.getString("distanceMeters");
                    String duration = object.getString("duration");
                    Log.i(GoogleMapService.class.getSimpleName(), duration);
                    String encodedPolyline = object.getJSONObject("polyline").getString("encodedPolyline");

                    ((GoogleMapFragment) fragment).transferData(distanceMeters, duration);

                    PolylineOptions polylineOptions = new PolylineOptions();
                    List<LatLng> latLngList = PolylineEncodingUtil.decode(encodedPolyline);
                    for (LatLng latLng : latLngList)
                        polylineOptions.add(latLng);

                    polylineOptions.width(15) // Độ dày của đường đường dẫn
                            .color(Color.BLUE); // Màu sắc của đường đường dẫn

                    clearPolyLine();
                    polylinePrevious = googleMap.addPolyline(polylineOptions);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.call(origin, destination);
    }

    public void clearPolyLine() {
        if (polylinePrevious != null) polylinePrevious.remove();
    }
}
