package com.example.movieticketbookingsystem.RecyclerView;

import static com.example.movieticketbookingsystem.MyDatabase.DbMovie.MOVIE_TABLE_NAME;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketbookingsystem.Models.BookingModel;
import com.example.movieticketbookingsystem.Models.MovieModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.R;
import com.example.movieticketbookingsystem.databinding.ItemMovieBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>  {


    ArrayList<MovieModel> movie;
    MovieListener listener;

    public MovieAdapter(ArrayList<MovieModel> movie, MovieListener listener) {
        this.movie = movie;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext())
                , parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        MovieModel m = movie.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define the desired date format
        String dateString = dateFormat.format(new Date(m.getDate1()));

        holder.MovieName.setText(m.getName());
        holder.MovieDate.setText(dateString);
        holder.MovieRate.setText(String.valueOf(m.getRate()));
        holder.MovieImage.setImageBitmap(m.getImage());
        holder.MovieNumber.setText(String.valueOf(m.getNumOfSets()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEdit(m.getId(), index);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView MovieName, MovieRate, MovieDate, MovieNumber;
        ImageView MovieImage;

        public MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            MovieName = binding.itemMovieNameEt;
            MovieRate = binding.itemMovieRateEt;
            MovieDate = binding.itemMovieDateEt;
            MovieImage = binding.ItemMovieImageIv;
            MovieNumber = binding.itemMovieNumTv;

        }
    }
}

