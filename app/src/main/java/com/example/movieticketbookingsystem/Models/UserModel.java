package com.example.movieticketbookingsystem.Models;

import android.graphics.Bitmap;

public class UserModel {
    private int id;
    private String name;
    private String password;
    private String email;
    private String userType;
    private Bitmap image;

    public UserModel(String name, String password, String email, String userType) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }

    public UserModel(int id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", image=" + image +
                '}';
    }
}
