package com.lamnguyen.ticket_movie_nlu.service.showtime;

import android.content.Context;

import com.lamnguyen.ticket_movie_nlu.api.ShowtimeApi;
import com.lamnguyen.ticket_movie_nlu.dto.RoomDTO;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.util.List;

public class ShowtimeService {
    private ShowtimeApi showtimeApi;

    private static ShowtimeService instance;

    public static ShowtimeService getInstance() {
        if (instance == null) {
            instance = new ShowtimeService();
        }
        return instance;
    }

    public ShowtimeService() {
        showtimeApi = ShowtimeApi.getInstance();
    }

    public void addShowtime(Context context, Integer roomId, Integer movieId, LocalDateTime schedule, CallAPI.CallAPIListener<ShowtimeDTO> listener) throws JSONException {
        showtimeApi.addShowtime(context, roomId, movieId, schedule, listener);
    }
}
