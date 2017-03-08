package me.abhishekraj.showmyshow;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry;
import me.abhishekraj.showmyshow.model.movie.Movie;

/**
 * Created by ABHISHEK RAJ on 5/3/2017.
 */

public class DatabaseHandler  {
    private static Context mContext;
    private static int mMovieId;
    private static boolean myFavoriteStatus;
    private static Uri mCurrentMovieUri;
    private static Movie mMovie;

    public DatabaseHandler() {

    }

    public static boolean myDatabaseHandler(Context context, int movieId, Uri currentMovieUri, Movie movie, int fragmentLoadOrClick) {
        mContext = context;
        mMovieId = movieId;
        mCurrentMovieUri = currentMovieUri;
        mMovie = movie;
        return extractFavoriteStatusFromDatabase(mMovieId, fragmentLoadOrClick);

    }

    public static boolean extractFavoriteStatusFromDatabase(int movieId, int fragmentLoadOrFavOrUnFavClick) {

        if (fragmentLoadOrFavOrUnFavClick == 8888) {

            String selection = MoviesEntry._ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(movieId)};

            int rowsDeleted = mContext.getContentResolver().delete(MoviesEntry.CONTENT_URI, selection, selectionArgs);
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(mContext, "deletion failed",
                        Toast.LENGTH_SHORT).show();

            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(mContext, "deletion successfull",
                        Toast.LENGTH_SHORT).show();
                myFavoriteStatus = true;
            }
        } else if (fragmentLoadOrFavOrUnFavClick == 9999) {
            // and data attributes from the editor are the values.
            ContentValues values = new ContentValues();
            values.put(MoviesEntry.COLUMN_MOVIE_TITLE, mMovie.getMovieId());
            values.put(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE, mMovie.getMovieId());
            values.put(MoviesEntry.COLUMN_MOVIE_OVERVIEW, mMovie.getMovieId());
            values.put(MoviesEntry.COLUMN_MOVIE_POSTER_URL, mMovie.getMovieId());
            values.put(MoviesEntry.COLUMN_MOVIE_BACKDROP_URL, mMovie.getMovieId());
            values.put(MoviesEntry.COLUMN_MOVIE_ID, mMovie.getMovieId());
            values.put(MoviesEntry.COLUMN_MOVIE_RATING, mMovie.getMovieId());

            // Insert a new data into the provider, returning the content URI for the new data.
            Uri newUri = mContext.getContentResolver().insert(MoviesEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                Toast.makeText(mContext, "insert failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "insert successfull",
                        Toast.LENGTH_SHORT).show();
                myFavoriteStatus = false;
            }
        }
        return myFavoriteStatus;
    }
}