package com.example.movieticketbookingsystem.UserActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.movieticketbookingsystem.Models.AddBookingModel;
import com.example.movieticketbookingsystem.Models.BookingModel;
import com.example.movieticketbookingsystem.Models.MovieModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.R;
import com.example.movieticketbookingsystem.databinding.ActivityViewMovieDetailsBinding;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class viewMovieDetails extends AppCompatActivity {

    ActivityViewMovieDetailsBinding binding;
    DbMovie dbMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbMovie = new DbMovie(this);


        Intent intent = getIntent();
        String name = intent.getStringExtra("movieName");
        String description = intent.getStringExtra("movieDescription");
        double rate = intent.getDoubleExtra("movieRate", -1);
        Long date = intent.getLongExtra("movieDate", 1);
        int setsNum = intent.getIntExtra("movieSetsNum", -1);
        byte image = intent.getByteExtra("movieImage", (byte) 11);
        String category = intent.getStringExtra("movieCategory");
        int movieId = intent.getIntExtra("position", -1);

        MovieModel movie = dbMovie.getMovieDetails(movieId);


        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{image});
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

//        to convert from long to date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(date);
        String dateString = dateFormat.format(date);

        binding.detailsNameTv.append(name);
        binding.detailsDescriptionTv.append(description);
        binding.detailsRateTv.append(String.valueOf(rate));
        binding.detailsDateTv.append(dateString);
        binding.detailsMovieIv.setImageBitmap(bitmap);
        binding.detailsSetsNumTv.append(String.valueOf(setsNum));
        binding.detailsCategoryTv.append(category);
        binding.detailsMovieIv.setImageBitmap(movie.getImage());

        SharedPreferences preferences = getSharedPreferences("signupData", MODE_PRIVATE);
        int usreId = Integer.parseInt(preferences.getString("id", "no data"));

        long today = System.currentTimeMillis();

        binding.detailsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (today < date) {
                    int ticket = Integer.parseInt(binding.detailsTicketsTv.getText().toString());
                    if (setsNum >= ticket && ticket > 0) {
                        Integer finalSeats = (setsNum - ticket);
                        if ((dbMovie.updateMovieSeats(String.valueOf(movieId), String.valueOf(finalSeats)))) {
                            BookingModel booking = new BookingModel(usreId, movieId, ticket);
                            dbMovie.addBooking(booking);
                        }
                    } else
                        Toast.makeText(viewMovieDetails.this, "This number of seats is not available", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(viewMovieDetails.this, "You will not be able to book this movie because it has expired",
                            Toast.LENGTH_SHORT).show();
            }
        });


    }
}