package com.example.movieticketbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.movieticketbookingsystem.Models.UserModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.UserActivitys.UserHome;
import com.example.movieticketbookingsystem.databinding.ActivityLoginBinding;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    DbMovie db;
    ArrayList<UserModel> userModel;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean isLogin = false;
    String userType ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DbMovie(this);
        getSupportActionBar().hide();

        preferences = getSharedPreferences("signupData", MODE_PRIVATE);
        editor = preferences.edit();


        String user = preferences.getString("user", "nodata");
        String admin = preferences.getString("admin", "nodata");

//        if (preferences.getBoolean("isLogin", false)) {
//            if (true){
//                Log.e("user", userType + isLogin +" hhh");
//                startActivity(new Intent(getBaseContext(), Home.class));
//            }
//        }
//        else if (preferences.getBoolean("isLogin", false) && userType == user) {
//            startActivity(new Intent(getBaseContext(), UserHome.class));
//        }


        
        binding.loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.loginUsernameEt.getText().toString();
                String password = binding.loginPasswordEt.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(Login.this, "please fill the data", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.cheeckIfAdmin(username, password).isStatus()) {
                        Toast.makeText(Login.this, "secces", Toast.LENGTH_SHORT).show();
                        Log.e("MONJED", String.valueOf(db.cheeckIfAdmin(username, password).getId()));
                        editor.putString("id", String.valueOf(db.cheeckIfAdmin(username, password).getId()));
                        isLogin = true;
                        editor.apply();
                        Log.e("user", userType + isLogin );
                        startActivity(new Intent(getBaseContext(), Home.class));
                    } else if (db.cheeckIfUser(username, password).isStatus()) {
                        Log.e("MONJED", String.valueOf(db.cheeckIfUser(username, password).getId()));
                        editor.putString("id", String.valueOf(db.cheeckIfUser(username, password).getId()));
                        isLogin = true;
                        editor.putBoolean("isLogin", isLogin);
                        editor.apply();
                        startActivity(new Intent(getBaseContext(), UserHome.class));
                    } else {
                        Toast.makeText(Login.this, "cheek user name or password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        binding.loginSignupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignUp.class));
            }
        });
    }
}