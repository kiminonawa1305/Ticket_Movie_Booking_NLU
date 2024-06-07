package com.lamnguyen.ticket_movie_nlu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private String id, poster, nameRoom, nameMovie, nameCinema, chairNumber, startShowtime;
}
