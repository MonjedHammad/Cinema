package com.example.movieticketbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.movieticketbookingsystem.Models.CategoryModel;
import com.example.movieticketbookingsystem.Models.UserModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.UserActivitys.UserHome;
import com.example.movieticketbookingsystem.databinding.ActivityEditProfileBinding;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    DbMovie dbMovie;
    ActivityEditProfileBinding binding;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbMovie = new DbMovie(this);
        preferences = getSharedPreferences("signupData", MODE_PRIVATE);
        int id = Integer.parseInt( preferences.getString("id", "no data"));

        UserModel user = dbMovie.getUserInfoById(id);
        if (user != null) {
            String name = user.getName();
            String password = user.getPassword();
            String email = user.getEmail();
            binding.EditProfilePasswordEt.setText(password);
            binding.EditProfileUsernameEt.setText(name);
            binding.EditProfileEmailEt.setText(email);
        }

        binding.EditProfileSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.EditProfileEmailEt.getText().toString();
                String password = binding.EditProfilePasswordEt.getText().toString();
                UserModel model = new UserModel(id,password,email);
                dbMovie.updateUserInfo(model);
            }
        });

    }
}