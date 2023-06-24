package com.example.movieticketbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.movieticketbookingsystem.Models.UserModel;
import com.example.movieticketbookingsystem.MyDatabase.Contact;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.UserActivitys.UserHome;
import com.example.movieticketbookingsystem.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    DbMovie movieDb;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        movieDb = new DbMovie(SignUp.this);


        preferences = getSharedPreferences("signupData",MODE_PRIVATE);
        editor = preferences.edit();




        binding.signupBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.signupUsernameEt.getText().toString();
                String password = binding.signupPasswordEt.getText().toString();
                String email = binding.signupEmailEt.getText().toString();
                String userType;
                editor.putString("name",name);
                editor.putString("password",password);
                editor.putString("email",email);
                editor.apply();

                if (name.isEmpty() || password.isEmpty() || email.isEmpty()){
                    Toast.makeText(SignUp.this, "Please fill the data", Toast.LENGTH_SHORT).show();
                }else {
                    if (movieDb.cheekUserame(name)){
                        Toast.makeText(SignUp.this, "This user is already added! please SignUp", Toast.LENGTH_SHORT).show();
                    }else {
                        if (binding.signupAdminRb.isChecked()) { // to add admin
                            userType = "admin";
                            UserModel c = new UserModel(name, password, email, userType);
                            movieDb.addUser(c);
                            editor.putString("id", String.valueOf(movieDb.cheeckIfAdmin(name, password).getId()));
                            editor.apply();
                            startActivity(new Intent(getBaseContext(), Home.class));
                        } else if (binding.signupUserRb.isChecked()) { // to add user
                            userType = "user";
                            UserModel c = new UserModel(name, password, email, userType);
                            movieDb.addUser(c);
                            editor.putString("id", String.valueOf(movieDb.cheeckIfUser(name, password).getId()));
                            editor.apply();
                            startActivity(new Intent(getBaseContext(), UserHome.class));
                        }
                    }

                }

            }
        });

        binding.signupLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Login.class));
            }
        });

    }
}