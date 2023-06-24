package com.example.movieticketbookingsystem.UserActivitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.movieticketbookingsystem.CategoryRecyclerView.CategorysAdapter;
import com.example.movieticketbookingsystem.EditProfile;
import com.example.movieticketbookingsystem.Login;
import com.example.movieticketbookingsystem.Models.CategoryModel;
import com.example.movieticketbookingsystem.Models.MovieModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.R;
import com.example.movieticketbookingsystem.RecyclerView.MovieAdapter;
import com.example.movieticketbookingsystem.RecyclerView.MovieListener;
import com.example.movieticketbookingsystem.databinding.ActivityUserHomeBinding;

import java.util.ArrayList;

public class UserHome extends AppCompatActivity implements MovieListener {

    DbMovie dbMovie;

    ArrayList<MovieModel> movieModel;
    ArrayList<CategoryModel> categoryModel;
    CategorysAdapter catAdapter;
    MovieAdapter adapter;
    ActivityUserHomeBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = getSharedPreferences("signupData", MODE_PRIVATE);
        editor = preferences.edit();


        dbMovie = new DbMovie(this);
        movieModel = dbMovie.getAllMovie();
        adapter = new MovieAdapter( movieModel ,this);
        binding.userHomeMovieRv.setAdapter(adapter);
//notifyMovieChanges();
        binding.userHomeMovieRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
//////////////////////////////////////////////////////////////

        categoryModel = dbMovie.getAllCategory();
        catAdapter = new CategorysAdapter(categoryModel ,this);
        binding.userHomeMovieCategoryRv.setAdapter(catAdapter);
        binding.userHomeMovieCategoryRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//////////////////////////////////////////////////////////////

        binding.homeBookingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Booking.class));
            }
        });

        binding.homeProfileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), EditeUserProfile.class));
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.HomeSearch).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieModel = dbMovie.searchMovies(newText);

                adapter = new MovieAdapter((ArrayList<MovieModel>) movieModel,UserHome.this);
                binding.userHomeMovieRv.setAdapter(adapter);
                notifyMovieChanges();
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.HomeLogout) {
            editor.putBoolean("isLogin",false);
            editor.apply();
            startActivity(new Intent(getBaseContext(), Login.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDelete(int id, int pos) {

    }

    @Override
    public void onEdit(int id ,int pos) {
        Intent intent = new Intent(this, viewMovieDetails.class);
        intent.putExtra("movieName", movieModel.get(pos).getName());
        intent.putExtra("movieDescription", movieModel.get(pos).getDescription());
        intent.putExtra("movieRate", movieModel.get(pos).getRate());
        intent.putExtra("movieDate", movieModel.get(pos).getDate1());
        intent.putExtra("movieSetsNum", movieModel.get(pos).getNumOfSets());
        intent.putExtra("movieImage", movieModel.get(pos).getImage2());
        intent.putExtra("movieCategory", movieModel.get(pos).getCategory());
        intent.putExtra("position", id);
        startActivity(intent);
        notifyMovieChanges();
    }
    public void notifyMovieChanges(){
        movieModel = dbMovie.getAllMovie();
        adapter = new MovieAdapter(movieModel,UserHome.this);
        binding.userHomeMovieRv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int pos) {

    }
}