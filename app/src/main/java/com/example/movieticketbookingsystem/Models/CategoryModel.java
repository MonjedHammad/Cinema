package com.example.movieticketbookingsystem.Models;

public class CategoryModel {
    private int id ;
    private String catName ;

    public CategoryModel(String catName) {
        this.catName = catName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public String toString() {
        return catName ;
    }
}
