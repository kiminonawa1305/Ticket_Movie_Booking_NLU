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


public class GoogleMapRoutesHttpRequest extends JsonObjectRequest {
    private LatLng origin, destination;
    public static String vehicle = DriverType.TWO_WHEELER.toString();


    public GoogleMapRoutesHttpRequest(Integer method, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, "https://routes.googleapis.com/directions/v2:computeRoutes", null, listener, errorListener);
    }

    public void setBody(LatLng origin, LatLng destination) {
        this.origin = origin;
        this.destination = destination;
    }

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
                /* .append("\"routingPreference\":\"TRAFFIC_AWARE\",")*/
                .append("\"travelMode\": \"")
                .append(vehicle)
                .append("\"")
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

    public void setVehicle(DriverType vehicle) {
        this.vehicle = vehicle.toString();
    }
}
