package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class PriceManageApi {

    private static final String URL_API = CallAPI.URL_WEB_SERVICE + "/api/priceManage";

    public interface PriceManageApiListener {
        void onSuccess(List<PriceManageDTO> priceManageDTOList);
        void onError(String message);
    }

    public static void getPriceManageList(Context context, final PriceManageApiListener listener) {
        CallAPI.callJsonArrayRequest(context, URL_API, "", Request.Method.GET, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<PriceManageDTO>>() {}.getType();
                List<PriceManageDTO> priceManageDTOList = gson.fromJson(response.toString(), listType);
                listener.onSuccess(priceManageDTOList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public interface UpdatePriceListener {
        void onUpdateSuccess();
        void onUpdateError(String message);
    }

    public static void updatePrice(Context context, PriceManageDTO priceManageDTO, final UpdatePriceListener listener) {
        Gson gson = new Gson();
        String json = gson.toJson(priceManageDTO);

        try {
            JSONObject jsonObject = new JSONObject(json);

            CallAPI.callJsonObjectRequest(context, URL_API + "/update", "", jsonObject, null, Request.Method.POST, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onUpdateSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onUpdateError(error.getMessage());
                }
            });
        } catch (JSONException e) {
            listener.onUpdateError(e.getMessage());
        }
    }
}
