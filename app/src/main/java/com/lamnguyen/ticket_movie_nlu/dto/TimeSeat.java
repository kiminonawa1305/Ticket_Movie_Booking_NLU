package com.lamnguyen.ticket_movie_nlu.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSeat {
    private String start, end;
    private int totalSeat, availableSeat;
}