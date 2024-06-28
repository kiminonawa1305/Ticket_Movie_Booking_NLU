package com.lamnguyen.ticket_movie_nlu.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private String title, poster, genre, duration;
    private Integer vote, id;
    private Double rate;
}