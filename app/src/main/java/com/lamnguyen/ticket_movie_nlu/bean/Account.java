package com.lamnguyen.ticket_movie_nlu.bean;

public class Account {
    private int stt;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String createdDate;
    private boolean locked;

    public Account(int stt, String name, String phone, String email, String password, String createdDate, boolean locked) {
        this.stt = stt;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.locked = locked;
    }

    public int getStt() {
        return stt;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
