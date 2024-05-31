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
public class PriceBoardDTO implements Serializable {
    private Double single;
    private Double couple;
    private Double vip;
}
