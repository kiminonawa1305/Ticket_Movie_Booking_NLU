package com.lamnguyen.ticket_movie_nlu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceManageDTO {
    private String cinemaName;
    private Integer single;
    private Integer couple;
    private Integer vip;
    private Integer cinema_Id;
}
