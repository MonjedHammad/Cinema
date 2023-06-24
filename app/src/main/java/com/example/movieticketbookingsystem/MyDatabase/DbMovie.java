package com.example.movieticketbookingsystem.MyDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.movieticketbookingsystem.Models.AddBookingModel;
import com.example.movieticketbookingsystem.Models.BookingModel;
import com.example.movieticketbookingsystem.Models.CategoryModel;
import com.example.movieticketbookingsystem.Models.LoginResult;
import com.example.movieticketbookingsystem.Models.MovieModel;
import com.example.movieticketbookingsystem.Models.UserModel;
import com.example.movieticketbookingsystem.UserActivitys.UserHome;
import com.example.movieticketbookingsystem.Utils;

import java.io.ByteArrayOutputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class DbMovie extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "MovieD";

    // below int is our database version
    private static final int DB_VERSION = 5;

    /////////////////////////////////////////////////////////////
    // USERS
    private static final String USER_TABLE_NAME = "user";

    private static final String USER_ID_COL = "id";

    private static final String USER_NAME_COL = "name";

    private static final String USER_PASSWORD_COL = "password";

    private static final String USER_EMAIL_COL = "email";

    private static final String USER_IMAGE_COL = "image";
    private static final String USER_TYPE_COL = "userType";

    //////////////////////////////////////////
    // MOVIE
    public static final String MOVIE_TABLE_NAME = "movie";
    private static final String MOVIE_ID_COL = "id";
    private static final String MOVIE_NAME_COL = "movieName";
    private static final String MOVIE_DATE_COL = "date";
    private static final String MOVIE_DESCIRPTION_COL = "description";
    private static final String MOVIE_RATE_COL = "rate";
    private static final String MOVIE_SETS_NUM_COL = "setsNumber";
    private static final String MOVIE_CATEGORY_NAME_COL = "categoryName";
    private static final String MOVIE_IMAGE_COL = "image";
    //////////////////////////////////////////
    // BOOKING
    private static final String BOOKING_TABLE_NAME = "bookings";
    private static final String BOOKING_ID_COL = "id";
    private static final String BOOKING_USER_ID_COL = "userId";
    private static final String BOOKING_DATE_COL = "date";
    private static final String BOOKING_MOVIE_ID_COL = "movieId";
    private static final String BOOKING_TICKETS_COL = "tickets";
    ////////////////////////////////////
    // CATEGORY
    private static final String CATEGORY_TABLE_NAME = "category";
    private static final String CATEGORY_ID_COL = "id";
    private static final String CATEGORY_NAME_COL = "name";
    private static final String CATEGORY_USER_ID_COL = "userId";
    Context c;

    public DbMovie(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String UserQuery = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + USER_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME_COL + " TEXT,"
                + USER_PASSWORD_COL + " TEXT,"
                + USER_TYPE_COL + " TEXT,"
                + USER_EMAIL_COL + " TEXT,"
                + USER_IMAGE_COL + " BLOB)";

        String BookingQuery = "CREATE TABLE " + BOOKING_TABLE_NAME + " ("
                + BOOKING_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOKING_TICKETS_COL + " INTEGER,"
                + BOOKING_USER_ID_COL + " INTEGER,"
                + BOOKING_MOVIE_ID_COL + " INTEGER ,"
                + "FOREIGN KEY (" + BOOKING_USER_ID_COL + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_ID_COL + "),"
                + " FOREIGN KEY (" + BOOKING_MOVIE_ID_COL + ") REFERENCES " + MOVIE_TABLE_NAME + " (" + MOVIE_ID_COL + "))";

        String MovieQuery = "CREATE TABLE " + MOVIE_TABLE_NAME + " ("
                + MOVIE_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MOVIE_NAME_COL + " TEXT,"
                + MOVIE_DESCIRPTION_COL + " TEXT,"
                + MOVIE_DATE_COL + " DATE,"
                + MOVIE_CATEGORY_NAME_COL + " TEXT,"
                + MOVIE_SETS_NUM_COL + " INTEGER,"
                + MOVIE_RATE_COL + " DOUBLE,"
                + MOVIE_IMAGE_COL + " BLOB)";

        String CategoryQuery = "CREATE TABLE " + CATEGORY_TABLE_NAME + " ("
                + CATEGORY_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME_COL + " TEXT,"
                + CATEGORY_USER_ID_COL + " INTEGER)";


        db.execSQL(UserQuery);
        db.execSQL(MovieQuery);
        db.execSQL(BookingQuery);
        db.execSQL(CategoryQuery);

    }


    public ArrayList<MovieModel> searchMovies(String filterQuery) {

        ArrayList<MovieModel> movie = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MOVIE_TABLE_NAME + " WHERE " + MOVIE_NAME_COL + " LIKE '%" + filterQuery + "%'", null);

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID_COL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_NAME_COL));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCIRPTION_COL));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(MOVIE_DATE_COL));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_CATEGORY_NAME_COL));
            int setsNum = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_SETS_NUM_COL));
            double rate = cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_RATE_COL));
            Bitmap bitmap = fromByteToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow(MOVIE_IMAGE_COL)));

            MovieModel movieModel = new MovieModel(name, category, date, description, bitmap, rate, setsNum);
            movieModel.setId(id);

            movie.add(movieModel); // هاد السطر مهم
        }
        return movie;
    }

    /////////////////////////////////////////////////////////////
    public void addBooking(BookingModel booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BOOKING_USER_ID_COL, booking.getUserId());
        cv.put(BOOKING_MOVIE_ID_COL, booking.getMovieId());
        cv.put(BOOKING_TICKETS_COL, booking.getTicket());
        long r = db.insert(BOOKING_TABLE_NAME, null, cv);
        if (r == -1) {
            Toast.makeText(c, "faild", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "secceed", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<BookingModel> getBookingsWithDetails(int userId) {
        ArrayList<BookingModel> bookings = new ArrayList<>();

        String query = "SELECT " +
                MOVIE_TABLE_NAME + "." + MOVIE_NAME_COL + ", " +
                MOVIE_TABLE_NAME + "." + MOVIE_DESCIRPTION_COL + ", " +
                MOVIE_TABLE_NAME + "." + MOVIE_DATE_COL + ", " +
                MOVIE_TABLE_NAME + "." + MOVIE_CATEGORY_NAME_COL + ", " +
                MOVIE_TABLE_NAME + "." + MOVIE_SETS_NUM_COL + ", " +
                MOVIE_TABLE_NAME + "." + MOVIE_RATE_COL + ", " +
                MOVIE_TABLE_NAME + "." + MOVIE_IMAGE_COL + ", " +
                BOOKING_TABLE_NAME + "." + BOOKING_TICKETS_COL + ", " +
                USER_TABLE_NAME + "." + USER_NAME_COL + ", " +
                USER_TABLE_NAME + "." + USER_EMAIL_COL +
                " FROM " + BOOKING_TABLE_NAME +
                " INNER JOIN " + MOVIE_TABLE_NAME +
                " ON " + MOVIE_TABLE_NAME + "." + MOVIE_ID_COL + " = " + BOOKING_TABLE_NAME + "." + BOOKING_MOVIE_ID_COL +
                " INNER JOIN " + USER_TABLE_NAME +
                " ON " + USER_TABLE_NAME + "." + USER_ID_COL + " = " + BOOKING_TABLE_NAME + "." + BOOKING_USER_ID_COL +
                " WHERE " + BOOKING_USER_ID_COL + " = " + userId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Process the cursor to retrieve the joined data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve movie data
                String movieName = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_NAME_COL));
                String movieDescription = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCIRPTION_COL));
                Long movieDate = cursor.getLong(cursor.getColumnIndexOrThrow(MOVIE_DATE_COL));
                String movieCategory = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_CATEGORY_NAME_COL));
                int movieSetsNum = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_SETS_NUM_COL));
                int tickets = cursor.getInt(cursor.getColumnIndexOrThrow(BOOKING_TICKETS_COL));
                double movieRate = cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_RATE_COL));
                Bitmap bitmap = fromByteToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow(MOVIE_IMAGE_COL)));

                // Retrieve user data
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME_COL));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL_COL));

                // Create BookingModel object and add it to the list
                BookingModel booking = new BookingModel(movieName, movieDescription, movieDate, movieCategory, tickets, movieRate, bitmap, userEmail);
                bookings.add(booking);

            } while (cursor.moveToNext());
        }

        // Close the cursor and the database
//    cursor.close();
//    db.close();

        return bookings;
    }

    /////////////////////////////////////////////////////////////
    public boolean cheekUserame(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + USER_TABLE_NAME + " where " + USER_NAME_COL + " =?",
                new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public LoginResult cheeckIfUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_NAME_COL +
                " =? AND " + USER_PASSWORD_COL + " =? AND " + USER_TYPE_COL + " = 'user'", new String[]{username, password});
        if (cursor.getCount() > 0) {
            int finalId = -1; // Initialize with a default value
            if (cursor.moveToFirst()) {
                finalId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID_COL));
            }
            cursor.close();
            return new LoginResult(true, finalId);
        } else {
            cursor.close();
            return new LoginResult(false, -1);
        }
    }

    public LoginResult cheeckIfAdmin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + USER_TABLE_NAME + " where " + USER_NAME_COL +
                " =? and " + USER_PASSWORD_COL + " =? and " + USER_TYPE_COL + " ='admin'", new String[]{username, password});
        if (cursor.getCount() > 0) {
            int finalId = -1; // Initialize with a default value
            if (cursor.moveToFirst()) {
                finalId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID_COL));
            }
            cursor.close();
            return new LoginResult(true, finalId);
        } else {
            cursor.close();
            return new LoginResult(false, -1);
        }
    }
    /////////////////////////////////////////////////////////////
    public void addUser(UserModel user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_NAME_COL, user.getName());
        values.put(USER_PASSWORD_COL, user.getPassword());
        values.put(USER_EMAIL_COL, user.getEmail());
        values.put(USER_TYPE_COL, user.getUserType());

        long r = db.insert(USER_TABLE_NAME, null, values);

        if (r == -1) {
            Toast.makeText(c, "faild", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "secceed", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateUserInfo(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_EMAIL_COL, userModel.getEmail());
        cv.put(USER_PASSWORD_COL, userModel.getPassword());
        String[] args = {String.valueOf(userModel.getId())};
        long r = db.update(USER_TABLE_NAME, cv, USER_ID_COL + " = ?", args);
        if (r > 0) {
            Toast.makeText(c, "update succesfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "update faild", Toast.LENGTH_SHORT).show();
        }
    }

    public UserModel getUserInfoById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_ID_COL, USER_NAME_COL, USER_PASSWORD_COL, USER_TYPE_COL, USER_EMAIL_COL, USER_IMAGE_COL};
        String selection = USER_ID_COL + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(USER_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        UserModel userModel = null;

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME_COL));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(USER_PASSWORD_COL));
            String userType = cursor.getString(cursor.getColumnIndexOrThrow(USER_TYPE_COL));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL_COL));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(USER_IMAGE_COL));

            userModel = new UserModel(name, password, email, userType);
            userModel.setId(id);
//            userModel.setImage(image);
        }

//        cursor.close();
        return userModel;
    }

    /////////////////////////////////////////////////////////////
    public void addCategory(CategoryModel cat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CATEGORY_NAME_COL, cat.getCatName());

        long r = db.insert(CATEGORY_TABLE_NAME, null, cv);
        if (r == -1) {
            Toast.makeText(c, "faild", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "secceed", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<CategoryModel> getAllCategory() {

        ArrayList<CategoryModel> category = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CATEGORY_TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String catName = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY_NAME_COL));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY_ID_COL));
            CategoryModel categoryModel = new CategoryModel(catName);
            categoryModel.setId(id);
            category.add(categoryModel);
        }
        return category;
    }

    public void deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long r = db.delete(CATEGORY_TABLE_NAME, CATEGORY_ID_COL + " = " + id, null);
        if (r > 0) {
            Toast.makeText(c, "deleted succesfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "deleted faild", Toast.LENGTH_SHORT).show();
        }
    }

    /////////////////////////////////////////////////////////////
    public byte[] fromBitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap fromByteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /////////////////////////////////////////////////////////////
    public void addMovie(MovieModel movieModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MOVIE_NAME_COL, movieModel.getName());
        cv.put(MOVIE_DATE_COL, movieModel.getDate1());
        cv.put(MOVIE_CATEGORY_NAME_COL, movieModel.getCategory());
        cv.put(MOVIE_DESCIRPTION_COL, movieModel.getDescription());
        cv.put(MOVIE_RATE_COL, String.valueOf(movieModel.getRate()));
        cv.put(MOVIE_SETS_NUM_COL, String.valueOf(movieModel.getNumOfSets()));
        cv.put(MOVIE_IMAGE_COL, fromBitmapToByte(movieModel.getImage()));

        long r = db.insert(MOVIE_TABLE_NAME, null, cv);
        if (r == -1) {
            Toast.makeText(c, "faild", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "secceed", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<MovieModel> getAllMovie() {

        ArrayList<MovieModel> movie = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MOVIE_TABLE_NAME, null);

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID_COL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_NAME_COL));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCIRPTION_COL));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(MOVIE_DATE_COL));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_CATEGORY_NAME_COL));
            int setsNum = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_SETS_NUM_COL));
            double rate = cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_RATE_COL));
            Bitmap bitmap = fromByteToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow(MOVIE_IMAGE_COL)));


            MovieModel movieModel = new MovieModel(name, category, date, description, bitmap, rate, setsNum);
            movieModel.setId(id);

            movie.add(movieModel); // هاد السطر مهم
        }
        return movie;
    }

    public void updateMovie(MovieModel movieModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MOVIE_NAME_COL, movieModel.getName());
        cv.put(MOVIE_DATE_COL, movieModel.getDate1());
        cv.put(MOVIE_DESCIRPTION_COL, movieModel.getDescription());
        cv.put(MOVIE_RATE_COL, String.valueOf(movieModel.getRate()));
        cv.put(MOVIE_SETS_NUM_COL, String.valueOf(movieModel.getNumOfSets()));

//        cv.put(MOVIE_IMAGE_COL, fromBitmapToByte(movieModel.getImage()));

        String[] args = {String.valueOf(movieModel.getId())};
        long r = db.update(MOVIE_TABLE_NAME, cv, MOVIE_ID_COL + " = ?", args);
        if (r > 0) {
            Toast.makeText(c, "update succesfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "update faild", Toast.LENGTH_SHORT).show();
        }
    }

    public MovieModel getMovieDetails(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MOVIE_TABLE_NAME + " WHERE " + MOVIE_ID_COL + " = " + id, null);

        MovieModel movieModel = null;
        while (cursor.moveToNext()) {

//            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID_COL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_NAME_COL));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCIRPTION_COL));
            Long date = cursor.getLong(cursor.getColumnIndexOrThrow(MOVIE_DATE_COL));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_CATEGORY_NAME_COL));
            int setsNum = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_SETS_NUM_COL));
            double rate = cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_RATE_COL));
            Bitmap bitmap = fromByteToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow(MOVIE_IMAGE_COL)));


            movieModel = new MovieModel(name, category, date, description, bitmap, rate, setsNum);
            movieModel.setId(id);
        }
        return movieModel;
    }

    public Boolean updateMovieSeats(String id, String numberOfSeats) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MOVIE_SETS_NUM_COL, numberOfSeats);
        long r = db.update(MOVIE_TABLE_NAME, cv, MOVIE_ID_COL + " = " + id, null);
        if (r > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteMovie(MovieModel movieModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        long r = db.delete(MOVIE_TABLE_NAME, MOVIE_ID_COL + " = ?",
                new String[]{String.valueOf(movieModel.getId())});
        if (r > 0) {
            Toast.makeText(c, "delete succesfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "delete faild", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE_NAME);

        onCreate(db);
    }
}
