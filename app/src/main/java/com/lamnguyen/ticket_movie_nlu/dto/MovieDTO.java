package com.lamnguyen.ticket_movie_nlu.dto;

public class MovieDTO {
    private String title, poster, genre, duration;
    private Integer vote;
    private Double rate;


    public String getName() {
        return name;
    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getRunTime() {
        return runTime;
    public String getDuration() {
        return duration;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }
}
