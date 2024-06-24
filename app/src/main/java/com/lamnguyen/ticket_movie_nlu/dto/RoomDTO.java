package com.lamnguyen.ticket_movie_nlu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private String name;
    private Integer id, cinema_id;
}
