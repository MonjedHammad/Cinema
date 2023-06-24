package com.example.movieticketbookingsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.example.movieticketbookingsystem.Models.MovieModel;
import com.example.movieticketbookingsystem.MyDatabase.DbMovie;
import com.example.movieticketbookingsystem.databinding.ActivityEditMovieBinding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditMovie extends AppCompatActivity {
    ActivityEditMovieBinding binding;
    DbMovie dbMovie;

    Bitmap bitmap;
    Uri uri;
    ActivityResultLauncher launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    uri = result.getData().getData();
                    if (uri != null) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        binding.editMovieIv.setImageBitmap(bitmap);
                    } else binding.editMovieIv.setImageBitmap(bitmap);

                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbMovie = new DbMovie(this);


        Intent intent = getIntent();
        String name = intent.getStringExtra("movieName");
        String description = intent.getStringExtra("movieDescription");
        double rate = intent.getDoubleExtra("movieRate", -1);
        Long date = intent.getLongExtra("movieDate", -1);
        int setsNum = intent.getIntExtra("movieSetsNum", -1);
        String category = intent.getStringExtra("movieCategory");
        int id = intent.getIntExtra("id", -1);

        MovieModel movie = dbMovie.getMovieDetails(id);

//        Log.e("MOE",String.valueOf(image));

//        to convert from long to date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(date);
        String dateString = dateFormat.format(date);


        binding.editMovieNameEt.setText(name);
        binding.editMovieDescriptionEt.setText(description);
        binding.editMovieRateEt.setText(String.valueOf(rate));
        binding.editMovieDateEt.setText(dateString);
        binding.editMovieSeatsEt.setText(String.valueOf(setsNum));
        binding.editMovieIv.setImageBitmap(movie.getImage());


        binding.editMovieBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editMovieNameEt.getText().toString();
                String description = binding.editMovieDescriptionEt.getText().toString();
                Double rate = Double.parseDouble(binding.editMovieRateEt.getText().toString());
                String category = binding.editMovieCategorySp.getSelectedItem().toString();
                int numSets = Integer.parseInt(binding.editMovieSeatsEt.getText().toString());
                String date1 = (binding.editMovieDateEt.getText().toString());


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
                movieModel.setId(id);
                dbMovie.updateMovie(movieModel);

            }
        });
//        binding.editMovieIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                launcher.launch(intent);
//            }
//        });
        binding.editMovieDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();
            }
        });
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

                binding.editMovieDateEt.setText(String.valueOf(year) + "-" + String.valueOf(month++) + "-" + String.valueOf(dayOfMonth));
            }
        }, 2023, 6, 1);
        dialog.show();
    }

}
