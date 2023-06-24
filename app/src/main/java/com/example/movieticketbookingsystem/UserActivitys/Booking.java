package com.example.movieticketbookingsystem.UserActivitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.movieticketbookingsystem.Models.BookingModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.RecyclerView.MovieAdapter;
import com.example.movieticketbookingsystem.databinding.ActivityBookingBinding;

import java.util.ArrayList;

public class Booking extends AppCompatActivity {

    DbMovie dbMovie;
    BookingAdapter adapter;
    ActivityBookingBinding binding;
    ArrayList<BookingModel> bookingModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = this.getSharedPreferences("signupData", MODE_PRIVATE);
        int usreId = Integer.parseInt(preferences.getString("id", "no data"));

        dbMovie = new DbMovie(this);
        bookingModel = dbMovie.getBookingsWithDetails(usreId);
        adapter = new BookingAdapter(bookingModel);
        binding.bookingRv.setAdapter(adapter);
        binding.bookingRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,true));
//        Log.e("booking", "onCreate: " +  dbMovie.getAllMovie().toString());
        adapter.notifyDataSetChanged();
    }
}