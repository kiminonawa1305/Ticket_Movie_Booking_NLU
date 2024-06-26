package com.lamnguyen.ticket_movie_nlu.service.chair;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lamnguyen.ticket_movie_nlu.api.ChairApi;
import com.lamnguyen.ticket_movie_nlu.dto.ChairDTO;
import com.lamnguyen.ticket_movie_nlu.enums.ChairStatus;
import com.lamnguyen.ticket_movie_nlu.response.ChairResponse;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;


public class ChairService {
    private ChairApi chairApi;
    private static ChairService instance;
    private FirebaseDatabase firebaseDatabase;

    public static ChairService getInstance() {
        if (instance == null) instance = new ChairService();
        return instance;
    }

    private ChairService() {
        chairApi = ChairApi.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void updateChair(String uuid, int chairId, ChairStatus status, Context context, CallAPI.CallAPIListener<ChairDTO> listener) {
        int userId = SharedPreferencesUtils.getUserID(context);
        chairApi.updateChair(userId, uuid, chairId, status, context, listener);
    }

    public void rollBack(String uuid, int chairId, ChairStatus status, Context context) {
        int userId = SharedPreferencesUtils.getUserID(context);
        chairApi.updateChair(userId, uuid, chairId, status, context, new CallAPI.CallAPIListener<ChairDTO>() {
            @Override
            public void completed(ChairDTO chairDTO) {

            }

            @Override
            public void error(Object error) {

            }
        });
    }

    public void loadChair(int showtimeId, Context context, CallAPI.CallAPIListener<ChairResponse> listener) {
        chairApi.loadChair(showtimeId, context, listener);
    }

    public void openConnectDatabase(String url, ValueEventListener valueEventListener) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(url);
        databaseReference.addValueEventListener(valueEventListener);
    }
}
