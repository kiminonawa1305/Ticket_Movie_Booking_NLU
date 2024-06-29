package com.lamnguyen.ticket_movie_nlu.api;

import android.content.Context;
import android.widget.Toast;

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

    private static final String URL_API = CallAPI.URL_WEB_SERVICE + "/admin/api/price";

    public interface PriceManageApiListener {
        void onSuccess(List<PriceManageDTO> priceManageDTOList);

        void onError(String message);
    }

    public static void getPriceManageList(Context context, final PriceManageApiListener listener) {
        CallAPI.callJsonArrayRequest(context, URL_API, null, Request.Method.GET,
                response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<PriceManageDTO>>() {
                    }.getType();
                    List<PriceManageDTO> priceManageDTOList = gson.fromJson(response.toString(), listType);
                    listener.onSuccess(priceManageDTOList);
                },
                error -> {
                    if (error.fillInStackTrace().toString().equalsIgnoreCase("com.android.volley.TimeoutError"))
                        Toast.makeText(context, "Lỗi server!", Toast.LENGTH_SHORT).show();
                    else
                        listener.onError(error.getMessage());
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

            CallAPI.callJsonObjectRequest(context, URL_API + "/update", null, jsonObject, null, Request.Method.POST,
                    response -> {
                        listener.onUpdateSuccess();
                    }, error -> {
                        if (error.fillInStackTrace().toString().equalsIgnoreCase("com.android.volley.TimeoutError"))
                            Toast.makeText(context, "Lỗi server!", Toast.LENGTH_SHORT).show();
                        else
                            listener.onUpdateError(error.getMessage());
                    });
        } catch (JSONException e) {
            listener.onUpdateError(e.getMessage());
        }
    }
}
