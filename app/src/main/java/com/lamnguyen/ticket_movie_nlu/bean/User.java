package com.lamnguyen.ticket_movie_nlu.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor

@AllArgsConstructor
public class User implements Serializable {
    private String email, password, apiId, phone, fullName;
    private Integer id;
}
