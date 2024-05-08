package com.lamnguyen.ticket_movie_nlu.hepler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MapsHelper {

    public static void openDirectionsInGoogleMaps(Context context, LatLng originLatLng, LatLng destLatLng) {
        String uri = "https://www.google.com/maps/dir/?api=1&origin="
                + originLatLng.latitude
                + "," + originLatLng.longitude
                + "&destination=" + destLatLng.latitude + ","
                + destLatLng.longitude + "&key=" +
                "AIzaSyBLEW2wgwgSBcGwXVW52_ZoNqfjHMtsF-s";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            Log.i(MapsHelper.class.getName(), context.getPackageManager().toString());
//            context.startActivity(intent);
        }
    }
}