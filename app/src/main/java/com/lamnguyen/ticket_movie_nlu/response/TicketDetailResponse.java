package com.lamnguyen.ticket_movie_nlu.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailResponse implements Serializable {
    private String id, poster, nameRoom, nameMovie, nameCinema, nameChair, duration;
    private String startShowtime;

}
