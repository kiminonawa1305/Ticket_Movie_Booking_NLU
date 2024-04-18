package com.lamnguyen.ticket_movie_nlu.dto;

import java.time.LocalDate;
import java.time.LocalTime;


public class TicketDTO {
    private Integer roomNumber;
    private String movieImage, movieName, cinemaName, chairNumber;
    private LocalTime time;
    private LocalDate date;

    public TicketDTO(String movieImage, String movieName, String cinemaName, Integer roomNumber, String chairNumber, LocalTime time, LocalDate date) {
        this.roomNumber = roomNumber;
        this.movieImage = movieImage;
        this.movieName = movieName;
        this.cinemaName = cinemaName;
        this.chairNumber = chairNumber;
        this.time = time;
        this.date = date;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getChairNumber() {
        return chairNumber;
    }

    public void setChairNumber(String chairNumber) {
        this.chairNumber = chairNumber;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                ", movieImage='" + movieImage + '\'' +
                ", movieName='" + movieName + '\'' +
                ", cinemaName='" + cinemaName + '\'' +
                ", roomNumber=" + roomNumber +
                ", chairNumber='" + chairNumber + '\'' +
                ", time=" + time +
                ", date=" + date +
                '}';
    }
}
