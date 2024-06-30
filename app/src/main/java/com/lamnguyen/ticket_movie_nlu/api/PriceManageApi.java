package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class PriceManageApi {

    private static final String URL_API = CallAPI.URL_WEB_SERVICE + "/admin/api/price";

    public static void getPriceManageList(Context context, final CallAPI.CallAPIListener<List<PriceManageDTO>> listener) {
        CallAPI.callJsonArrayRequest(context, URL_API, null, Request.Method.GET, response -> {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<PriceManageDTO>>() {
            }.getType();
            List<PriceManageDTO> priceManageDTOList = gson.fromJson(response.toString(), listType);
            listener.completed(priceManageDTOList);
        }, listener::error);
    }

    public static void updatePrice(Context context, PriceManageDTO priceManageDTO, CallAPI.CallAPIListener<JSONObject> listener) {
        Gson gson = new Gson();
        String json = gson.toJson(priceManageDTO);

        try {
            JSONObject jsonObject = new JSONObject(json);

            CallAPI.callJsonObjectRequest(context, URL_API + "/update", null, jsonObject, null, Request.Method.POST, listener::completed, listener::error);
        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
