package com.lamnguyen.ticket_movie_nlu.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class TicketDTO {
    private String id, poster, nameRoom, nameMovie, nameCinema, chairNumber, startShowtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public String getNameCinema() {
        return nameCinema;
    }

    public void setNameCinema(String nameCinema) {
        this.nameCinema = nameCinema;
    }

    public String getChairNumber() {
        return chairNumber;
    }

    public void setChairNumber(String chairNumber) {
        this.chairNumber = chairNumber;
    }

    public String getStartShowtime() {
        return startShowtime;
    }

    public void setStartShowtime(String startShowtime) {
        this.startShowtime = startShowtime;
    }
}
