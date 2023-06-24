package com.example.movieticketbookingsystem.Models;

public class AddBookingModel {

    //  بدنا نحذفه ملوش لازمة
    private int id;
    private int userId;
    private int movieId;
    private int ticket;

    public AddBookingModel(int userId, int movieId, int ticket) {
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

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }
}
