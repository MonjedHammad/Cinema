package com.example.movieticketbookingsystem.Models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class MovieModel implements Serializable {
    private int id;
    private String name;
    //    private Date date;
    private Long date1;
    private String description;
    private Bitmap image;
    private byte image2;
    private double rate;
    private int numOfSets;
    private String category;

    public MovieModel(String name, String category, Long date1, String description, Bitmap image, double rate, int numOfSets) {
        this.category = category;
        this.name = name;
        this.date1 = date1;
        this.description = description;
        this.image = image;
        this.rate = rate;
        this.numOfSets = numOfSets;

    }
 public MovieModel(String name, String category, Long date1, String description, double rate, int numOfSets) {
        this.category = category;
        this.name = name;
        this.date1 = date1;
        this.description = description;
        this.image = image;
        this.rate = rate;
        this.numOfSets = numOfSets;

    }

    public byte getImage2() {
        return image2;
    }

    public void setImage2(byte image2) {
        this.image2 = image2;
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


    public Long getDate1() {
        return date1;
    }

    public void setDate1(Long date1) {
        this.date1 = date1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getNumOfSets() {
        return numOfSets;
    }

    public void setNumOfSets(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public MovieModel(String name, String category , Date date, String description, double rate, int numOfSets) {
//        this.name = name;
//        this.date = date;
//        this.category = category;
//        this.description = description;
//        this.rate = rate;
//        this.numOfSets = numOfSets;
//    }
//
//    public MovieModel(String name, String category , String date1, String description, double rate, int numOfSets) {
//        this.name = name;
//        this.date1 = date1;
//        this.category = category;
//        this.description = description;
//        this.rate = rate;
//        this.numOfSets = numOfSets;
//    }


    @Override
    public String toString() {
        return "MovieModel{" +
                "\nid='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date1='" + date1 + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", rate=" + rate +
                ", numOfSets=" + numOfSets +
                '}';
    }
}
