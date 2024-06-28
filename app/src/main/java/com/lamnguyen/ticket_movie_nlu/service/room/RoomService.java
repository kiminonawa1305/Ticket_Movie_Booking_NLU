package com.lamnguyen.ticket_movie_nlu.service.room;

import android.content.Context;

import com.lamnguyen.ticket_movie_nlu.api.RoomApi;
import com.lamnguyen.ticket_movie_nlu.dto.RoomDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.List;

public class RoomService {
    private RoomApi roomApi;
    private static RoomService instance;

    public static RoomService getInstance(){
        if(instance == null){
            instance = new RoomService();
        }
        return instance;
    }

    public RoomService(){
        roomApi = RoomApi.getInstance();
    }

    public void loadRoomsOfCinema(Context context, Integer cinemaId, CallAPI.CallAPIListener<List<RoomDTO>> listener){
        roomApi.loadRoomsOfCinema(context, cinemaId, listener);
    }
}
