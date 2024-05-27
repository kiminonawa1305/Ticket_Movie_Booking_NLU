package com.lamnguyen.ticket_movie_nlu.dto;


import lombok.Data;

@Data
public class MovieDetailDTO {
    private String title, poster, duration, description;
    private Integer id, vote;
    private Double rate;
    private String[] actors, directors, genres;
}
