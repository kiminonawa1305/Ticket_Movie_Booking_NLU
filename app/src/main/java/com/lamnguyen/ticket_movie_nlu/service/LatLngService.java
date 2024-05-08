package com.lamnguyen.ticket_movie_nlu.service;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class LatLngService {
    private static LatLngService instance;
    private final String TAG = LatLngService.class.getSimpleName();

    public static LatLngService getInstance() {
        if (instance == null) instance = new LatLngService();
        return instance;
    }

    private LatLngService() {
    }

    public static LatLng createLatLng(String[] location) {
        return new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
    }

    public static LatLng createLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public LatLng loadCurrentLocation(File fileCache) {
        try {
            if (!fileCache.exists()) fileCache.createNewFile();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileCache)));
            String lat = reader.readLine();
            String lng = reader.readLine();
            reader.close();

            if (lat != null && lng != null)
                return new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }

        return null;
    }

    public void saveCurrentLocation(File fileCache, LatLng location) {
        try {
            if (!fileCache.exists()) fileCache.createNewFile();
            PrintWriter writer = new PrintWriter(new FileOutputStream(fileCache, false), true);
            writer.println(location.latitude);
            writer.println(location.longitude);
            writer.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
    }
}
