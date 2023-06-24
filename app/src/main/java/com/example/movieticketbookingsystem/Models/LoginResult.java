package com.example.movieticketbookingsystem.Models;

public class LoginResult {
    private boolean status;
    private int id;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoginResult(boolean status, int id) {
        this.status = status;
        this.id = id;
    }
}
