package me.abhishekraj.showmyshow.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry;
/**
 * Created by ABHISHEK RAJ on 4/20/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "inventory.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link MovieDbHelper}.
     *
     * @param context of the app
     */
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the inventory table
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " ("
                + MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + MoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL DEFAULT '', "
                + MoviesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL DEFAULT '', "
                + MoviesEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL DEFAULT '', "
                + MoviesEntry.COLUMN_MOVIE_POSTER_URL + " TEXT NOT NULL DEFAULT '', "
                + MoviesEntry.COLUMN_MOVIE_BACKDROP_URL + " TEXT NOT NULL DEFAULT '',"
                + MoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL DEFAULT 0 UNIQUE , "
                + MoviesEntry.COLUMN_MOVIE_RATING + " FLOAT NOT NULL DEFAULT 0.0, "
                + MoviesEntry.COLUMN_FAVORITE_STATUS + " INTEGER NOT NULL DEFAULT 0 ); ";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}