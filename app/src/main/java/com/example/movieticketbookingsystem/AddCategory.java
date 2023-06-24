package com.example.movieticketbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movieticketbookingsystem.Models.CategoryModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.databinding.ActivityAddCategoryBinding;

public class AddCategory extends AppCompatActivity {
ActivityAddCategoryBinding binding;
DbMovie dbMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbMovie = new DbMovie(AddCategory.this);
        binding.AddCategoryBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catNmae = binding.AddCategoryNameEt.getText().toString();
                CategoryModel cm = new CategoryModel(catNmae);
                dbMovie.addCategory(cm);
            }
        });

    }
}