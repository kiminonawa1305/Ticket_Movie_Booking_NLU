package com.lamnguyen.ticket_movie_nlu.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
public class DashboardResponse implements Serializable {
    private int totalNumOfTickets;
    private Map<String, Integer> numOfTickets;
    private Map<String, Double> revenue;
    private double totalRevenue;

    public DashboardResponse(){
        this.totalNumOfTickets = 0;
        this.totalRevenue = 0;
        this.numOfTickets = new HashMap<>();
        this.revenue = new HashMap<>();
    }
}
