package com.lamnguyen.ticket_movie_nlu.bean;

public class CareerInfo {

    private String career;
    private String name;

    public CareerInfo(String career, String name) {
        this.career = career;
        this.name = name;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
