package com.lamnguyen.ticket_movie_nlu.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class CallAPI {
    public static final String URL_WEB_SERVICE = "http://10.0.20.216:80";
    public static final String URL_OMDB = "http://www.omdbapi.com/?apikey=c3d0a99f";
    public static final String URL_GOOGLE_MAP_COMPUTE_ROUTES = "https://routes.googleapis.com/directions/v2:computeRoutes";
    public static final String URL_GOOGLE_MAP_DIRECTION = "https://maps.googleapis.com/maps/api/directions/json";

    public static void callStringRequest(Context context, String url, String query, int method, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(method, url + query, responseListener, errorListener);
        //Set TAG
        stringRequest.setTag(context.getClass().getSimpleName());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static void callJsonObjectRequest(Context context, String url, String query, int method, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(method, url + query, null, responseListener, errorListener);
        //Set TAG
        stringRequest.setTag(context.getClass().getSimpleName());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static void callStringRequest(Context context, String url, String query, String body, Map<String, String> header, int method, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(method, url + query, responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                return header;
            }

            @Override
            public byte[] getBody() {
                return body.getBytes();
            }
        };
        //Set TAG
        stringRequest.setTag(context.getClass().getSimpleName());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static void callJsonObjectRequest(Context context, String url, String query, String body, Map<String, String> header, int method, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(method, url + query, null, responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                header.putAll(headers);
                return header;
            }

            @Override
            public byte[] getBody() {
                return body.getBytes();
            }
        };
        //Set TAG
        stringRequest.setTag(context.getClass().getSimpleName());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}