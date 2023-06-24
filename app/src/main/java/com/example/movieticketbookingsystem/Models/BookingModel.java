package com.example.movieticketbookingsystem.Models;

import android.graphics.Bitmap;

public class BookingModel {
    private int id;
    private int userId;
    private int movieId;
    private int ticket;
    //    private String date;
///////////////
    private String movieName;
    private String movieDescription;
    private Long movieDate;
    private String movieCategory;
    private int movieSetsNum;
    private double movieRate;
    private String userName;
    private String userEmail;
    private Bitmap image;

    public BookingModel(int userId, int movieId, int ticket) {
        this.userId = userId;
        this.movieId = movieId;
        this.ticket = ticket;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    //////////////////////////////////////////////


    public BookingModel(String movieName, String movieDescription, Long movieDate, String movieCategory, int movieSetsNum, double movieRate, Bitmap image, String userEmail) {
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieDate = movieDate;
        this.movieCategory = movieCategory;
        this.movieSetsNum = movieSetsNum;
        this.movieRate = movieRate;
        this.image = image;
        this.userEmail = userEmail;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public Long getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(Long movieDate) {
        this.movieDate = movieDate;
    }

    public String getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(String movieCategory) {
        this.movieCategory = movieCategory;
    }

    public int getMovieSetsNum() {
        return movieSetsNum;
    }

    public void setMovieSetsNum(int movieSetsNum) {
        this.movieSetsNum = movieSetsNum;
    }

    public double getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(double movieRate) {
        this.movieRate = movieRate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "movieName='" + movieName + '\'' +
                ", movieDescription='" + movieDescription + '\'' +
                ", movieDate=" + movieDate +
                ", movieCategory='" + movieCategory + '\'' +
                ", movieSetsNum=" + movieSetsNum +
                ", movieRate=" + movieRate +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }





}
