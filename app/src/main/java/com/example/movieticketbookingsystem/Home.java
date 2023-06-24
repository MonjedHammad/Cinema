package com.example.movieticketbookingsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.movieticketbookingsystem.CategoryRecyclerView.Categorys;
import com.example.movieticketbookingsystem.CategoryRecyclerView.CategorysAdapter;
import com.example.movieticketbookingsystem.Models.CategoryModel;
import com.example.movieticketbookingsystem.Models.MovieModel;
import com.example.movieticketbookingsystem.Models.UserModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.RecyclerView.Movie;
import com.example.movieticketbookingsystem.RecyclerView.MovieAdapter;
import com.example.movieticketbookingsystem.RecyclerView.MovieListener;
import com.example.movieticketbookingsystem.databinding.ActivityHomeBinding;

import java.io.ByteArrayOutputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Home extends AppCompatActivity implements MovieListener {
    ActivityHomeBinding binding;
    DbMovie dbMovie;
    ArrayList<MovieModel> movieModel;
    ArrayList<CategoryModel> categoryModel;
    CategorysAdapter catAdapter;
    MovieAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbMovie = new DbMovie(this);

        preferences = getSharedPreferences("signupData", MODE_PRIVATE);
        editor = preferences.edit();

//        Log.d("getallmovie", "onCreate: " + dbMovie.getAllMovie().toString());


        binding.homeSerchIv.setVisibility(View.GONE);
        binding.homeTv.setVisibility(View.GONE);
        binding.profileIv.setVisibility(View.GONE);


//////////////////////////////////////////////////////////////
        movieModel = dbMovie.getAllMovie();
        adapter = new MovieAdapter((ArrayList<MovieModel>) movieModel, this);
        binding.homeMovieRv.setAdapter(adapter);
        binding.homeMovieRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        notifyMovieChanges();
////////////////////////////////////////////////////////////
        ArrayList<CategoryModel> categoryModel = dbMovie.getAllCategory();
        catAdapter = new CategorysAdapter(categoryModel, this);
        binding.homeMovieCategoryRv.setAdapter(catAdapter);
        binding.homeMovieCategoryRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//////////////////////////////////////////////////////////////

        AppInstallationManager installationManager = new AppInstallationManager(this);

        if (installationManager.isFirstInstall()) {

            dbMovie.addCategory(new CategoryModel("Action"));
            dbMovie.addCategory(new CategoryModel("Romantic"));
            dbMovie.addCategory(new CategoryModel("Horror"));
            dbMovie.addCategory(new CategoryModel("Anime"));
            dbMovie.addCategory(new CategoryModel("Family"));
            dbMovie.addCategory(new CategoryModel("Science fiction"));

            notifyCategoryChanges();

//            dbMovie.addMovie(new MovieModel("monjed","aa",Long.parseLong("1690588800000"),"ljlkjlk",,3,3));
            // Set the first install flag to false
            installationManager.setFirstInstall(false);
        }

//        get image id from drawable
//        int imageResourceId = getResources().getIdentifier("monjed", "drawable", getPackageName());
//        Log.e("image", "onCreate: " + imageResourceId );

//////////////////////////////////////////////////////////////
        registerForContextMenu(binding.homeMovieCategoryRv);

        binding.homeAddCategoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddCategory.class);
                startActivity(intent);
            }
        });

        binding.homeAddMovieTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddMovie.class);
                startActivity(intent);
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////


        binding.homeProfileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditProfile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.HomeSearch).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieModel = dbMovie.searchMovies(query);
                adapter = new MovieAdapter((ArrayList<MovieModel>) movieModel,Home.this);
                binding.homeMovieRv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                movieModel = dbMovie.getAllMovie();
                adapter = new MovieAdapter((ArrayList<MovieModel>) movieModel,Home.this);
                binding.homeMovieRv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.HomeLogout) {
            editor.putBoolean("isLogin",false);
//            editor.putString("userType", "admin");
            editor.apply();
            startActivity(new Intent(getBaseContext(), Login.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDelete(int id, int pos) {

        new AlertDialog.Builder(Home.this)
                .setTitle("Are you sure to delete this category")
                .setMessage("you will delete this category")
                .setIcon(R.drawable.ic_star)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbMovie.deleteCategory(id);
                        notifyCategoryChanges();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }


    @Override
    public void onEdit(int id, int pos) {
        Intent intent = new Intent(this, EditMovie.class);
        intent.putExtra("movieName", movieModel.get(pos).getName());
        intent.putExtra("movieDescription", movieModel.get(pos).getDescription());
        intent.putExtra("movieRate", movieModel.get(pos).getRate());
        intent.putExtra("movieDate", movieModel.get(pos).getDate1());
        intent.putExtra("movieSetsNum", movieModel.get(pos).getNumOfSets());
        intent.putExtra("movieImage", movieModel.get(pos).getImage2());
        intent.putExtra("movieCategory", movieModel.get(pos).getCategory());
        intent.putExtra("id", id);

        startActivity(intent);
        notifyMovieChanges();
    }

    @Override
    public void onItemClick(int pos) {

    }

    public void notifyMovieChanges() {
        movieModel = dbMovie.getAllMovie();
        adapter = new MovieAdapter(movieModel, Home.this);
        binding.homeMovieRv.setAdapter(adapter);
    }

    public void notifyCategoryChanges() {
        categoryModel = dbMovie.getAllCategory();
        catAdapter = new CategorysAdapter(categoryModel, Home.this);
        binding.homeMovieCategoryRv.setAdapter(catAdapter);
    }
}