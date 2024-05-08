package com.lamnguyen.ticket_movie_nlu.service.Ticket;

import android.util.Log;

import com.lamnguyen.ticket_movie_nlu.api.TicketApi;
import com.lamnguyen.ticket_movie_nlu.dto.TicketDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private static TicketService instance;
    private static final String TAG = TicketService.class.getSimpleName();
    private TicketApi ticketApi;

    public static TicketService getInstance() {
        if (instance == null) instance = new TicketService();
        return instance;
    }

    private TicketService() {
        ticketApi = TicketApi.getInstance();
    }
}
