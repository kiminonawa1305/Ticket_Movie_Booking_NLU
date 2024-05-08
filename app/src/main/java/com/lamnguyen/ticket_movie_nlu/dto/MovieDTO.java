package com.lamnguyen.ticket_movie_nlu.dto;

public class MovieDTO {
    private String title, poster, genre, duration;
    private Integer vote;
    private Double rate;

    public MovieDTO(String title, String poster, String genre, String duration, String name, Integer vote, Double rate) {
        this.title = title;
        this.poster = poster;
        this.genre = genre;
        this.duration = duration;
        this.vote = vote;
        this.rate = rate;
    }

    public MovieDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}