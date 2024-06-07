package com.lamnguyen.ticket_movie_nlu.response;

import com.lamnguyen.ticket_movie_nlu.dto.ChairDTO;
import com.lamnguyen.ticket_movie_nlu.dto.PriceBoardDTO;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChairResponse implements Serializable {
    private String uuid;
    private List<ChairDTO> chairs;
    private PriceBoardDTO price;
}
