package com.lamnguyen.ticket_movie_nlu.dto;

public class MovieDTO {
    private String name, poster, genre;
    private Integer runTime, vote;
    private Double rate;

    public String getName() {
        return name;
    }

    public String getPoster() {
        return poster;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getRunTime() {
        return runTime;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getVote() {
        return vote;
    }
}