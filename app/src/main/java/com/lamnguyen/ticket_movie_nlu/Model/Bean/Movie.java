package com.lamnguyen.ticket_movie_nlu.Model.Bean;

import lombok.Data;

public class Movie {
    private String genre, title, poster, runTime;
    private Integer imdbVotes;
    private Double imdbRating;

    public static Movie getMovieTest(){
        Movie movie =  new Movie();
        movie.setTitle("Skyggen i mit øje");
        movie.setGenre("Drama, History, War");
        movie.setPoster("https://m.media-amazon.com/images/M/MV5BNzYzZTgxMTQtYTlhYy00OGJkLThkMGEtNjlhZTRkNDIwZTkyXkEyXkFqcGdeQXVyMTM0NTc2NDgw._V1_SX300.jpg");
        movie.setImdbVotes(16405);
        movie.setRunTime("107 min");
        movie.setImdbRating(7.3);
        return movie;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public Integer getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(Integer imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }
}
