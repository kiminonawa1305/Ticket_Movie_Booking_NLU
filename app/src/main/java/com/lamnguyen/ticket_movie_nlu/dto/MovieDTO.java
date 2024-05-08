package com.lamnguyen.ticket_movie_nlu.dto;

public class MovieDTO {
    private String title, poster, genre, duration;
    private Integer vote;
    private Double rate;

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getVote() {
        return vote;
    }
}