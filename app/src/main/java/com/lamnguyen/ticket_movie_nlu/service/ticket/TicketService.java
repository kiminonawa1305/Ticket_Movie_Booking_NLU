package com.lamnguyen.ticket_movie_nlu.service.ticket;

import com.lamnguyen.ticket_movie_nlu.api.TicketApi;

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
