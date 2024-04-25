package com.lamnguyen.ticket_movie_nlu.request;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;
import com.lamnguyen.ticket_movie_nlu.enums.DriverType;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GoogleMapDirectionsHttpRequest extends JsonObjectRequest {
    private LatLng origin, destination;
    public static String vehicle = DriverType.TWO_WHEELER.toString();


    public GoogleMapDirectionsHttpRequest(Integer method, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, "https://maps.googleapis.com/maps/api/directions/json", null, listener, errorListener);
    }

    public void setBody(LatLng origin, LatLng destination) {
        this.origin = origin;
        this.destination = destination;
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", "AIzaSyAkJ-NbtMqrX05P5LzHQr86aAZeJ6iEmuA");
        params.put("region", "vn");
        params.put("origin", origin.latitude + ", " + origin.longitude);
        params.put("destination", destination.latitude + ", " + destination.longitude);
        return params;
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

    public void setVehicle(DriverType vehicle) {
        this.vehicle = vehicle.toString();
    }
}
