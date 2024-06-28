package com.lamnguyen.ticket_movie_nlu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private int stt;
    private String name;
    private String phone;
    private String email;
    private String password;
    private boolean locked;
}
