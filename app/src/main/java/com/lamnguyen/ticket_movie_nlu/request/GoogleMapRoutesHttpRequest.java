package com.lamnguyen.ticket_movie_nlu.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.android.gms.maps.model.LatLng;
import com.lamnguyen.ticket_movie_nlu.enums.DriverType;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GoogleMapRoutesHttpRequest {
    private LatLng origin, destination;
    public static String vehicle = DriverType.TWO_WHEELER.toString();
    private Response.Listener<JSONObject> listener;
    private Response.ErrorListener errorListener;
    private int method;
    private Context context;

    public GoogleMapRoutesHttpRequest(Context context, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this.context = context;
        this.method = Request.Method.POST;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public void call(LatLng origin, LatLng destination) {
        String body = getBody(origin, destination);
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_GOOGLE_MAP_COMPUTE_ROUTES, "", body, getHeaders(), method, listener, errorListener);
    }

    private String getBody(LatLng origin, LatLng destination) {
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
                /* .append("\"routingPreference\":\"TRAFFIC_AWARE\",")*/
                .append("\"travelMode\": \"")
                .append(vehicle)
                .append("\"")
                .append("}");
        return stringBuilder.toString();
    }

    private Map<String, String> getHeaders() {
        Map<String, String> result = new HashMap<>();
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
