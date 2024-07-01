package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeByCinema;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MovieApi {
    private static MovieApi instance;

    public static MovieApi getInstance() {
        if (instance == null) instance = new MovieApi();

        return instance;
    }

    private MovieApi() {
    }

    public void loadMovie(LocalDate date, Context context, CallAPI.CallAPIListener<List<MovieDTO>> listener) {
        String path = "/movie/api/";
        if (date != null) {
            path += "showtime?date=" + date;
        }
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        List<MovieDTO> movieDTOs = new ArrayList<>();
                        if (response.has("data"))
                            movieDTOs.addAll(List.of(new Gson().fromJson(response.getString("data"), MovieDTO[].class)));
                        listener.completed(movieDTOs);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }

    public void loadMovieDetail(Integer id, LocalDate date, Context context, CallAPI.CallAPIListener<MovieDetailDTO> listener) {
        String path = "/movie/api/detail/" + SharedPreferencesUtils.getUserID(context) + "/" + id + "/" + date.toString();
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        MovieDetailDTO movieDetailDTO = new Gson().fromJson(response.getString("data"), MovieDetailDTO.class);
                        Log.i(getClass().getSimpleName(), "onResponse: " + movieDetailDTO.toString());
                        listener.completed(movieDetailDTO);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }

    public void loadListFavoriteMovieDetail(Context context, CallAPI.CallAPIListener<List<MovieDTO>> listener) {
        int userId = SharedPreferencesUtils.getUserID(context);
        String path = "/movie-favourite/api/" + userId;

        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        List<MovieDTO> data = new ArrayList<>();
                        if (response.has("data"))
                            data.addAll(List.of(new Gson().fromJson(response.getString("data"), MovieDTO[].class)));
                        listener.completed(data);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }

    public void loadShowtime(int movieId, LocalDate date, Context context, CallAPI.CallAPIListener<List<ShowtimeByCinema>> listener) {
        String path = "/showtime/api/" + movieId + "/" + date.toString();

        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        ShowtimeByCinema[] movieDTOs = new Gson().fromJson(response.getString("data"), ShowtimeByCinema[].class);
                        listener.completed(List.of(movieDTOs));
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }

    public void addNewMovie(Context context, String idApi, CallAPI.CallAPIListener<MovieDTO> listener) throws JSONException {
        String path = "/admin/movie/api/add";
        JSONObject newMovieRequestBodyJSON = new JSONObject().put("idApi", idApi);
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, newMovieRequestBodyJSON, null, Request.Method.POST,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        MovieDTO newMovieDTO = new Gson().fromJson(response.getString("data"), MovieDTO.class);
                        listener.completed(newMovieDTO);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }

    public void setMovieFavourite(Context context, Integer movieId, CallAPI.CallAPIListener<Void> listener) {
        String body = "/movie-favourite/api/" + SharedPreferencesUtils.getUserID(context) + "/" + movieId;
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + body, null, Request.Method.POST,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        listener.completed(null);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }

    public void loadAllMovieDetail(Context context, CallAPI.CallAPIListener<List<MovieDetailDTO>> listener) {
        String path = "/admin/movie/api";
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + path, null, Request.Method.GET,
                response -> {
                    try {
                        if (response.getInt("status") != 202) {
                            listener.error(response.getString("message"));
                            return;
                        }

                        List<MovieDetailDTO> movieDetailDTO = new ArrayList<>();
                        if (response.has("data"))
                            movieDetailDTO.addAll(List.of(new Gson().fromJson(response.getString("data"), MovieDetailDTO[].class)));

                        listener.completed(movieDetailDTO);
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, listener::error);
    }
}
