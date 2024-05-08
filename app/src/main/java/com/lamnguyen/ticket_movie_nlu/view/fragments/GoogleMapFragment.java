package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.controller.OnMapReadyCallbackImpl;
import com.lamnguyen.ticket_movie_nlu.enums.DriverType;
import com.lamnguyen.ticket_movie_nlu.request.GoogleMapRoutesHttpRequest;
import com.lamnguyen.ticket_movie_nlu.service.LatLngService;
import com.lamnguyen.ticket_movie_nlu.service.cinema.CinemaService;
import com.lamnguyen.ticket_movie_nlu.service.google.GoogleMapService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapFragment extends Fragment {
    private static final String TAG = "GoogleMapFragment";
    private GoogleMap googleMap;
    private ValueAnimator valueAnimatorZoom;
    private SupportMapFragment mapFragment;
    private LatLng currentLocation, destination;
    private CinemaService cinemaService;
    private GoogleMapService googleMapService;
    private LatLngService latLngService;
    private File fileCache;
    private Boolean direct = false, showDurationDistance = false;
    private Button btnChangeTypeMap, btnListCinema, btnVehicle;
    private Fragment frgDistanceDuration;
    private PopupMenu popupMenuTypeMap, popupMenuVehicle;
    private OnMapReadyCallbackImpl onMapReadyCallback;
    private String style = "[\n" + "  {\n" + "    \"elementType\": \"geometry\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#242f3e\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"elementType\": \"labels.text.fill\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#746855\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"elementType\": \"labels.text.stroke\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#242f3e\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"administrative.locality\",\n" + "    \"elementType\": \"labels.text.fill\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#d59563\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"poi\",\n" + "    \"elementType\": \"labels.text.fill\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#d59563\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"poi.park\",\n" + "    \"elementType\": \"geometry\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#263c3f\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"poi.park\",\n" + "    \"elementType\": \"labels.text.fill\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#6b9a76\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"road\",\n" + "    \"elementType\": \"geometry\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#38414e\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"road\",\n" + "    \"elementType\": \"geometry.stroke\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#212a37\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"road\",\n" + "    \"elementType\": \"labels.text.fill\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#9ca5b3\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"road.highway\",\n" + "    \"elementType\": \"geometry\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#746855\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"road.highway\",\n" + "    \"elementType\": \"geometry.stroke\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#1f2835\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"road.highway\",\n" + "    \"elementType\": \"labels.text.fill\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#f3d19c\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"transit\",\n" + "    \"elementType\": \"geometry\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#2f3948\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"transit.station\",\n" + "    \"elementType\": \"labels.text.fill\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#d59563\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"water\",\n" + "    \"elementType\": \"geometry\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#17263c\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"water\",\n" + "    \"elementType\": \"labels.text.fill\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#515c6d\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"featureType\": \"water\",\n" + "    \"elementType\": \"labels.text.stroke\",\n" + "    \"stylers\": [\n" + "      {\n" + "        \"color\": \"#17263c\"\n" + "      }\n" + "    ]\n" + "  }\n" + "]";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cinemaService = CinemaService.getInstance();
        latLngService = LatLngService.getInstance();
        cinemaService.getLatLngs(this.getActivity());

        valueAnimatorZoom = ValueAnimator.ofFloat(0, 18);
        valueAnimatorZoom.setDuration(2000);


        File cacheDir = getActivity().getCacheDir();
        fileCache = new File(cacheDir.getAbsolutePath(), "location.cache");
        if (fileCache.exists()) {
            try {
                fileCache.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        currentLocation = latLngService.loadCurrentLocation(fileCache);

        destination = cinemaService.getLatLngs(GoogleMapFragment.this.getActivity()).get(0);

        googleMapService = GoogleMapService.getInstance();

        getChildFragmentManager().setFragmentResultListener(DurationDistanceDestinationFragment.class.getSimpleName(), this, (requestKey, result) -> {
            showDurationDistance = !result.getBoolean("close");
            direct = result.getBoolean("go");
            if (showDurationDistance != null && !showDurationDistance) {
                googleMapService.clearPolyLine();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.remove(frgDistanceDuration);
                transaction.commit();
                direct = false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        onMapReadyCallback = new OnMapReadyCallbackImpl(this);

        btnChangeTypeMap = view.findViewById(R.id.button_type_map);
        btnListCinema = view.findViewById(R.id.button_list_cinema);
        btnVehicle = view.findViewById(R.id.button_vehicle_google_map);

        frgDistanceDuration = new DurationDistanceDestinationFragment();

        popupMenuTypeMap = new PopupMenu(this.getActivity(), btnChangeTypeMap);
        popupMenuTypeMap.getMenuInflater().inflate(R.menu.menu_type_google_map, popupMenuTypeMap.getMenu());


        popupMenuVehicle = new PopupMenu(this.getActivity(), btnChangeTypeMap);
        popupMenuVehicle.getMenuInflater().inflate(R.menu.menu_vehicle_google_map, popupMenuVehicle.getMenu());
        popupMenuVehicle.setForceShowIcon(true);


        this.event();
    }

    private void event() {
        mapFragment.getMapAsync(onMapReadyCallback);

        popupMenuTypeMap.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_item_google_map_type_normal)
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            else if (id == R.id.menu_item_google_map_type_terrain)
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            else if (id == R.id.menu_item_google_map_type_hybrid)
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            else googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        });
        popupMenuVehicle.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_item_google_map_vehicle_car) {
                GoogleMapRoutesHttpRequest.vehicle = DriverType.DRIVE.toString();
                btnVehicle.setBackgroundResource(R.mipmap.ic_car);
            } else if (id == R.id.menu_item_google_map_vehicle_walk) {
                GoogleMapRoutesHttpRequest.vehicle = DriverType.WALK.toString();
                btnVehicle.setBackgroundResource(R.mipmap.ic_walk);
            } else {
                GoogleMapRoutesHttpRequest.vehicle = DriverType.TWO_WHEELER.toString();
                btnVehicle.setBackgroundResource(R.mipmap.ic_motorbike);
            }

            if (showDurationDistance) {
                googleMapService.drawWay(this, googleMap, currentLocation, destination);
            }
            return true;
        });
        btnChangeTypeMap.setOnClickListener(v -> {
            popupMenuTypeMap.show();
        });
        btnListCinema.setOnClickListener(v -> {
            PopupMenu popupMenuListCinema = new PopupMenu(this.getActivity(), btnListCinema);
            Menu menu = popupMenuListCinema.getMenu();
            List<MenuItem> menuItems = new ArrayList<>();
            List<CinemaService.CinemaLatLng> cinemaLatLags = cinemaService.getCinemaLatLag(GoogleMapFragment.this.getActivity());
            for (CinemaService.CinemaLatLng cinemaLatLng : cinemaLatLags) {
                MenuItem menuItem = menu.add(cinemaLatLng.getName());
                menuItems.add(menuItem);
            }

            popupMenuListCinema.setOnMenuItemClickListener(item -> {
                if (showDurationDistance == null || !showDurationDistance) {
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_distance_duration_destination, frgDistanceDuration, null);
                    transaction.commit();
                }
                showDurationDistance = true;
                for (int i = 0; i < menuItems.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    if (!item.equals(menuItem)) continue;

                    Log.i(TAG, cinemaLatLags.get(i).getName() + "; " + cinemaLatLags.get(i).getLatLng().toString());
                    destination = cinemaLatLags.get(i).getLatLng();
                    googleMapService.drawWay(this, googleMap, currentLocation, destination);
                }
                return true;
            });

            popupMenuListCinema.show();
        });
        btnVehicle.setOnClickListener(v -> {
            popupMenuVehicle.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        latLngService.saveCurrentLocation(fileCache, currentLocation);
    }


    public void transferData(String distanceMeters, String duration) {
        Bundle bundle = new Bundle();
        bundle.putString(DurationDistanceDestinationFragment.ARG_PARAM1, distanceMeters);
        bundle.putString(DurationDistanceDestinationFragment.ARG_PARAM2, duration);

        getChildFragmentManager().setFragmentResult(this.getClass().getSimpleName(), bundle);
    }


    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public LatLng getDestination() {
        return destination;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public GoogleMapService getGoogleMapService() {
        return googleMapService;
    }

    public ValueAnimator getValueAnimatorZoom() {
        return valueAnimatorZoom;
    }

    public String getStyle() {
        return style;
    }

    public Boolean getDirect() {
        return direct;
    }
}