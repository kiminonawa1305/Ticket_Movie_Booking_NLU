package com.lamnguyen.ticket_movie_nlu.dto;

import java.util.List;

public class UserDTO {
    private Integer id;
    private String name;
    private List<MovieDTO> favoriteMovies;

    public UserDTO() {
    }

    public UserDTO(Integer id, String name, List<MovieDTO> favoriteMovies) {
        this.id = id;
        this.name = name;
        this.favoriteMovies = favoriteMovies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieDTO> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(List<MovieDTO> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }
}
