package com.lamnguyen.ticket_movie_nlu.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private int totalNumOfTickets;
    private Map<String, Integer> numOfTickets;
    private Map<String, Double> revenue;
    private double totalRevenue;
}
