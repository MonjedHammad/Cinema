package com.example.movieticketbookingsystem.UserActivitys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketbookingsystem.Models.BookingModel;
import com.example.movieticketbookingsystem.databinding.ItemBookingBinding;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    AbstractList<BookingModel> bookingModel ;

    public BookingAdapter(AbstractList<BookingModel> bookingModel) {
        this.bookingModel = bookingModel;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookingBinding binding = ItemBookingBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BookingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        BookingModel m = bookingModel.get(index);

        holder.name.setText(m.getMovieName());
        holder.rate.setText(String.valueOf(m.getMovieRate()));
        holder.ticekt.setText(String.valueOf(m.getMovieSetsNum()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define the desired date format
        String dateString = dateFormat.format(new Date(m.getMovieDate()));
        holder.date.setText(dateString);
        holder.image.setImageBitmap(m.getImage());
    }

    @Override
    public int getItemCount() {
        return bookingModel.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView name,rate,date,ticekt;
        ImageView image;
        public BookingViewHolder(ItemBookingBinding binding) {
            super(binding.getRoot());
            name = binding.itemBookingMovieNameTv;
            rate = binding.itemBookingMovieRateTv;
            date = binding.itemBookingMovieDateTv;
            ticekt = binding.itemBookingTicektTv;
            image = binding.itemBookingIv;
        }
    }
}
