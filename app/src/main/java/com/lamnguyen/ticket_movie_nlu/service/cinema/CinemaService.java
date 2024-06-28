package com.lamnguyen.ticket_movie_nlu.service.cinema;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.api.CinemaApi;
import com.lamnguyen.ticket_movie_nlu.dto.CinemaDTO;
import com.lamnguyen.ticket_movie_nlu.service.LatLngService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.ArrayList;
import java.util.List;

public class CinemaService {
    private CinemaApi cinemaApi;
    private static final String TAG = CinemaService.class.getSimpleName();

    private static CinemaService instance;


    public static CinemaService getInstance() {
        if (instance == null) instance = new CinemaService();

        return instance;
    }

    private CinemaService() {
        cinemaApi = CinemaApi.getInstance();
    }

    public List<LatLng> getLatLngs(Context context) {
        List<LatLng> locations = new ArrayList<>();
        String[] locationCineStar = context.getResources().getStringArray(R.array.CINESTAR);
        String[] locationGalaxy = context.getResources().getStringArray(R.array.GALAXY);
        String[] locationGigaMall = context.getResources().getStringArray(R.array.GIGALMALL);
        String[] locationCoopMart = context.getResources().getStringArray(R.array.COOP_MART);
        locations.add(LatLngService.createLatLng(locationCineStar));
        locations.add(LatLngService.createLatLng(locationGalaxy));
        locations.add(LatLngService.createLatLng(locationGigaMall));
        locations.add(LatLngService.createLatLng(locationCoopMart));
        return locations;
    }

    public List<CinemaLatLng> getCinemaLatLag(Context context) {
        List<CinemaLatLng> locations = new ArrayList<>();
        String[] locationCineStar = context.getResources().getStringArray(R.array.CINESTAR);
        String[] locationGalaxy = context.getResources().getStringArray(R.array.GALAXY);
        String[] locationGigaMall = context.getResources().getStringArray(R.array.GIGALMALL);
        String[] locationCoopMart = context.getResources().getStringArray(R.array.COOP_MART);
        locations.add(createCinemaLatLng(locationCineStar));
        locations.add(createCinemaLatLng(locationGalaxy));
        locations.add(createCinemaLatLng(locationGigaMall));
        locations.add(createCinemaLatLng(locationCoopMart));
        return locations;
    }

    private static CinemaLatLng createCinemaLatLng(String[] data) {
        return new CinemaLatLng(data[2], LatLngService.createLatLng(data));
    }

    public void loadCinemas(Context context, CallAPI.CallAPIListener<List<CinemaDTO>> listener){
        cinemaApi.loadCinemas(context, listener);
    }

    public static class CinemaLatLng {
        private LatLng latLng;
        private String name;

        public CinemaLatLng(String name, LatLng latLng) {
            this.latLng = latLng;
            this.name = name;
        }

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
