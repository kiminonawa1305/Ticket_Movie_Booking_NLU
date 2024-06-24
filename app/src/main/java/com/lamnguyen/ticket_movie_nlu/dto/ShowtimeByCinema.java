package com.lamnguyen.ticket_movie_nlu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeByCinema {
    private String urlImageCinema;
    private String nameCinema;
    private String addressCinema;
    private String detailAddressCinema;
    private String distance;
    private List<TimeSeat> timeSeats;
}