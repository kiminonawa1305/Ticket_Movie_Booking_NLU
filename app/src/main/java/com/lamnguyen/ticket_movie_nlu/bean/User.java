package com.lamnguyen.ticket_movie_nlu.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String email, password, apiId, phone, fullName;
}
