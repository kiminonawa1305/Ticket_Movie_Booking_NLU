package com.lamnguyen.ticket_movie_nlu.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketResponse implements Serializable {
    private String id;
    private String nameCinema;
    private String nameMovie;
    private String nameChair;
    private LocalDateTime startDate;
    private String nameRoom;
}
