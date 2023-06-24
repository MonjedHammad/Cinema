package com.example.movieticketbookingsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.example.movieticketbookingsystem.CategoryRecyclerView.CategorysAdapter;
import com.example.movieticketbookingsystem.Models.CategoryModel;
import com.example.movieticketbookingsystem.Models.MovieModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.databinding.ActivityAddMovieBinding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMovie extends AppCompatActivity {

    ActivityAddMovieBinding binding;
    DbMovie dbMovie;
    Long newDate;
    Bitmap bitmap;
    Uri uri;
    ActivityResultLauncher launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    uri = result.getData().getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.AddMovieIv.setImageBitmap(bitmap);
                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbMovie = new DbMovie(this);

        binding.AddMovieIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });
        binding.AddMovieDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateDialog();
            }
        });
        binding.AddMovieBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.AddMovieNameEt.getText().toString();
                String description = binding.AddMovieDescriptionEt.getText().toString();
                double rate = Double.parseDouble(binding.AddMovieRateEt.getText().toString());
                String date1 = (binding.AddMovieDateEt.getText().toString());
                int numSets = Integer.parseInt(binding.AddMovieSeatsEt.getText().toString());
                String category = binding.addMovieCategorySp.getSelectedItem().toString();

//                Date to Long
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                Date date = null;
                try {
                    date = dateFormat.parse(date1);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                long longDate = date.getTime();

                MovieModel movieModel = new MovieModel(name, category, longDate, description, bitmap, rate, numSets);

                dbMovie.addMovie(movieModel);
            }
        });

        ArrayList<CategoryModel> categoryModels = dbMovie.getAllCategory();

        ArrayAdapter<CategoryModel> adapter = new ArrayAdapter<>(AddMovie.this, android.R.layout.simple_list_item_1,
                categoryModels);
        binding.addMovieCategorySp.setAdapter(adapter);
        Log.d("cat", "onCreate: " + categoryModels);
        ///////////////////////////////////////

//        Intent intent = getIntent();
//        String name = intent.getStringExtra("movieName");
//        String description = intent.getStringExtra("movieDescription");
//        Double rate = intent.getDoubleExtra("movieRate",-1);
//        Long date = intent.getLongExtra("movieDate",-1);
//        int setsNum = intent.getIntExtra("movieSetsNum",-1);
//        byte image = intent.getByteExtra("movieImage",(byte) 11);
//        String category = intent.getStringExtra("movieCategory");
//        int position = intent.getIntExtra("position",-1);
//
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{image});
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//
////        to convert from long to date
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////        Date date1 = new Date(date);
//        String dateString = dateFormat.format(date);
//
//
//        binding.AddMovieNameEt.setText(name);
//        binding.AddMovieDescriptionEt.setText(description);
//        binding.AddMovieRateEt.setText(String.valueOf(rate));
//        binding.AddMovieDateEt.setText(dateString);
//        binding.AddMovieIv.setImageBitmap( bitmap);
//        binding.AddMovieSeatsEt.setText(String.valueOf(setsNum));
//
//
//
//        binding.AddMovieUpdateBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = binding.AddMovieNameEt.getText().toString();
//                String description = binding.AddMovieDescriptionEt.getText().toString();
//                Double rate = Double.parseDouble( binding.AddMovieRateEt.getText().toString());
//                Long date = newDate;
//                String category = binding.addMovieCategorySp.getSelectedItem().toString();
//                int numSets = Integer.parseInt(binding.AddMovieSeatsEt.getText().toString());
//
//                MovieModel movieModel = new MovieModel(name,category,date,description,bitmap,rate,numSets);
//                movieModel.setId(position);
//                dbMovie.updateMovie(movieModel);
//            }
//        });
        binding.AddMovieUpdateBt.setVisibility(View.GONE);

    }

    private void dateDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Date today = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month++, dayOfMonth); // Set the selected date

//                calendar.setTime(today);


                long dateInMillis = calendar.getTimeInMillis();
                newDate = dateInMillis;

                binding.AddMovieDateEt.setText(String.valueOf(year) + "-" + String.valueOf(month++) + "-" + String.valueOf(dayOfMonth));
            }
        }, 2023, 6, 1);
        dialog.show();
    }
}