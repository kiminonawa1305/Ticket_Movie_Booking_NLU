package com.lamnguyen.ticket_movie_nlu.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareerInfo {
    private String career;
    private String name;
}
