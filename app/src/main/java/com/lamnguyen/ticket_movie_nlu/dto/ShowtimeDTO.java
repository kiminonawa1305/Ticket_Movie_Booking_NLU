package com.lamnguyen.ticket_movie_nlu.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO {
    private Integer id, movieId, roomId;
    private LocalDateTime start;;
}
