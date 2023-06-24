package com.example.movieticketbookingsystem.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

public class Movie {
    private String movieName;
    private String movieRate;
    private String movieDate;
    private String movieImage;

    public Movie(String movieName, String movieRate, String movieDate, String movieImage) {
        this.movieName = movieName;
        this.movieRate = movieRate;
        this.movieDate = movieDate;
        this.movieImage = movieImage;
    }
    public Movie(String movieName, String movieRate, String movieDate) {
        this.movieName = movieName;
        this.movieRate = movieRate;
        this.movieDate = movieDate;
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

    public String getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }
}
