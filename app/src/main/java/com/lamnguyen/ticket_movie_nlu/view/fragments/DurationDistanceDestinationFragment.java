package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.lamnguyen.ticket_movie_nlu.R;

public class DurationDistanceDestinationFragment extends Fragment {
    private Button btnCloseFragment, btnGo;
    private TextView tvTime, tvDistance;
    public static final String ARG_PARAM1 = "distanceMeters";
    public static final String ARG_PARAM2 = "duration";
    private String distanceMeters;
    private String duration;

    public DurationDistanceDestinationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(GoogleMapFragment.class.getSimpleName(), this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                distanceMeters = bundle.getString(ARG_PARAM1);
                duration = bundle.getString(ARG_PARAM2);
                updateStatus();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_duration_distance_destination, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCloseFragment = view.findViewById(R.id.button_close_fragment);
        tvDistance = view.findViewById(R.id.text_view_distance_destination);
        tvTime = view.findViewById(R.id.text_view_time_destination);
        btnGo = view.findViewById(R.id.button_go);
        btnCloseFragment.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("close", true);
            getParentFragmentManager().setFragmentResult(this.getClass().getSimpleName(), bundle);
            btnGo.setVisibility(View.VISIBLE);
        });

        btnGo.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("go", true);
            getParentFragmentManager().setFragmentResult(this.getClass().getSimpleName(), bundle);
            btnGo.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("SetTextI18n")
    private void updateStatus() {
        duration = duration.replace("s", "");

        int time = Integer.parseInt(duration);
        double distance = Double.parseDouble(distanceMeters);
        if (time < 60) tvTime.setText(time + " Giấy");
        else if (time >= 60 * 60) tvTime.setText(time / (60 * 60) + " Giờ");
        tvTime.setText(time / 60 + " Phút");
        if (distance < 1000) tvDistance.setText(distance + " M");
        else tvDistance.setText(((int) (distance / 100)) / 10.0 + " Km");
    }
}