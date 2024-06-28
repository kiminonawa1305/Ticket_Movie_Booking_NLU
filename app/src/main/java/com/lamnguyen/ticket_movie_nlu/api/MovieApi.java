package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeByCinema;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
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
        String body = "/movie/api/";
        if(date != null){
            body += "showtime?date=" + date.toString();
        }
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("status") != 202) {
                                listener.error(jsonObject.getString("message"));
                                return;
                            }

                            MovieDTO[] movieDTOs = new Gson().fromJson(jsonObject.getString("data"), MovieDTO[].class);
                            listener.completed(List.of(movieDTOs));
                        } catch (JSONException e) {
                            listener.error(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error);
                    }
                }
        );
    }



    public void loadMovieDetail(Integer id, LocalDate date, Context context, CallAPI.CallAPIListener<MovieDetailDTO> listener) {
        String body = "/movie/api/detail/" + id + "/" + date.toString();
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE, body, Request.Method.GET, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("status") != 202) {
                                listener.error(jsonObject.getString("message"));
                                return;
                            }

                            MovieDetailDTO movieDetailDTO = new Gson().fromJson(jsonObject.getString("data"), MovieDetailDTO.class);
                            Log.i(getClass().getSimpleName(), "onResponse: " + movieDetailDTO.toString());
                            listener.completed(movieDetailDTO);
                        } catch (JSONException e) {
                            listener.error(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error);
                    }
                }
        );
    }

    public void loadListFavoriteMovieDetail(Context context, CallAPI.CallAPIListener<List<MovieDTO>> listener) {
        int userId = SharedPreferencesUtils.getUserID(context);
        String body = "/movie-favourite/api/" + userId;

        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + body, null, Request.Method.GET, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("status") != 202) {
                        listener.error(jsonObject.getString("message"));
                        return;
                    }

                    MovieDTO[] movieDTOs = new Gson().fromJson(jsonObject.getString("data"), MovieDTO[].class);
                    listener.completed(List.of(movieDTOs));
                } catch (JSONException e) {
                    listener.error(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        });
    }

    public void loadShowtime(int movieId, LocalDate date, Context context, CallAPI.CallAPIListener<List<ShowtimeByCinema>> listener) {
        String body = "/showtime/api/" + movieId + "/" + date.toString();

        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + body, null, Request.Method.GET, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("status") != 202) {
                        listener.error(jsonObject.getString("message"));
                        return;
                    }

                    ShowtimeByCinema[] movieDTOs = new Gson().fromJson(jsonObject.getString("data"), ShowtimeByCinema[].class);
                    listener.completed(List.of(movieDTOs));
                } catch (JSONException e) {
                    listener.error(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        });
    }

    public void addNewMovie(Context context, String idApi, CallAPI.CallAPIListener<MovieDTO> listener) throws JSONException {
        String body = "/movie/api/";
        JSONObject newMovieRequestBodyJSON = new JSONObject().put("idApi", idApi);
        CallAPI.callJsonObjectRequest(context, CallAPI.URL_WEB_SERVICE + body, null, newMovieRequestBodyJSON, null, Request.Method.POST, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("status") != 202){
                        listener.error(response.getString("message"));
                        return;
                    }

                    MovieDTO newMovieDTO = new Gson().fromJson(response.getString("data"), MovieDTO.class);
                    listener.completed(newMovieDTO);
                }catch (JSONException e){
                    listener.error(e.getMessage());
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        });
    }
}
