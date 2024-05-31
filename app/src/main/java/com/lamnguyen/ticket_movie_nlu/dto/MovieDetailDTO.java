package com.lamnguyen.ticket_movie_nlu.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailDTO {
    private String title, poster, duration, description;
    private Integer id, vote;
    private Double rate;
    private String[] actors, directors, genres;
}
