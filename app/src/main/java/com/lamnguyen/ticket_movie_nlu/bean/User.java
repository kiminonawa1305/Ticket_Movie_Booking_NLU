package com.lamnguyen.ticket_movie_nlu.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class User {
    private int stt;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String createdDate;
    private boolean locked;

    public User(int stt, String name, String phone, String email, String password, String createdDate, boolean locked) {
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
}
