package com.lamnguyen.ticket_movie_nlu.service.Ticket;

import android.util.Log;

import com.lamnguyen.ticket_movie_nlu.dto.TicketDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private static TicketService instance;

    public static TicketService getInstance() {
        if (instance == null) instance = new TicketService();
        return instance;
    }

    private TicketService() {
    }

    public List<TicketDTO> getTicketsDemo() {
        List<TicketDTO> ticketDTOList = new ArrayList<TicketDTO>();
        TicketDTO ticket = null;
        for (int i = 0; i < 10; i++) {
            ticket = new TicketDTO("", "Phim đánh lộn  " + i, "Cinestar", 1, "2B", LocalTime.of(12, 30, 0), LocalDate.of(2024, 5, 13));
            ticketDTOList.add(ticket);
            Log.i(TicketService.class.getSimpleName(), ticket.toString());
        }

        return ticketDTOList;
    }
}
