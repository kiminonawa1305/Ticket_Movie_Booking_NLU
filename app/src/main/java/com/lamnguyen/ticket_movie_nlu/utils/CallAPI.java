package com.lamnguyen.ticket_movie_nlu.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import lombok.SneakyThrows;

public class CallAPI {

    public static final String IP = "172.20.26.177";
    public static final String URL_WEB_SERVICE = "http://" + IP + ":8080";
    public static final String URL_OMDB = "http://www.omdbapi.com/?apikey=c3d0a99f";
    public static final String URL_GOOGLE_MAP_COMPUTE_ROUTES = "https://routes.googleapis.com/directions/v2:computeRoutes";
    public static final String URL_GOOGLE_MAP_DIRECTION = "https://maps.googleapis.com/maps/api/directions/json";
    private static final int TIME_OUT = 1000;

    public static void callStringRequest(Context context, String url, String query, int method, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(method, url + (query == null ? "" : query), responseListener, errorListener);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Set TAG
        stringRequest.setTag(context.getClass().getSimpleName());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @SneakyThrows
    public static void callJsonObjectRequest(Context context, String url, String query, int method, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.i(CallAPI.class.getSimpleName(), url + query);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url + (query == null ? "" : query), null, responseListener, errorListener);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Set TAG
        jsonObjectRequest.setTag(context.getClass().getSimpleName());
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public static void callStringRequest(Context context, String url, String query, String body, Map<String, String> header, int method, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(method, url + (query == null ? "" : query), responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                return header;
            }

            @Override
            public byte[] getBody() {
                return body.getBytes();
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Set TAG
        stringRequest.setTag(context.getClass().getSimpleName());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static void callJsonObjectRequest(Context context, String url, String query, JSONObject jsonObject, Map<String, String> header, int method, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Log.i(CallAPI.class.getSimpleName(), url + (query == null ? "" : query));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url + (query == null ? "" : query), jsonObject, responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header != null) {
                    Map<String, String> headers = super.getHeaders();
                    header.putAll(headers);
                    return header;
                }

                return super.getHeaders();
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Set TAG
        jsonObjectRequest.setTag(context.getClass().getSimpleName());
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    @SneakyThrows
    public static void callJsonArrayRequest(Context context, String url, String query, int method, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.i(CallAPI.class.getSimpleName(), url + query);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(method, url + (query == null ? "" : query), null, responseListener, errorListener);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonArrayRequest.setTag(context.getClass().getSimpleName());
        queue.add(jsonArrayRequest);
    }

    public interface CallAPIListener<T> {
        void completed(T t);

        void error(Object error);
    }
}
