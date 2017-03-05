package me.abhishekraj.showmyshow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry;
import me.abhishekraj.showmyshow.model.movie.Movie;

/**
 * Created by ABHISHEK RAJ on 5/3/2017.
 */

public class DatabaseHandler  {
    private static Context mContext;
    private static int mPosition;
    private static int mId;
    private static boolean myFavoriteStatus;
    private static Uri mCurrentMovieUri;
    private static Movie mMovie;

    public DatabaseHandler() {

    }

    public static boolean myDatabaseHandler(Context context, int position, Uri currentMovieUri, Movie movie) {
        mContext = context;
        mPosition = position;
        mCurrentMovieUri = currentMovieUri;
        mMovie = movie;
        return extractFavoriteStatusFromDatabase(mPosition);

    }

    public static boolean extractFavoriteStatusFromDatabase(int position) {

        String[] projection = {
                MoviesEntry._ID,
                MoviesEntry.COLUMN_MOVIE_TITLE,
                MoviesEntry.COLUMN_MOVIE_RELEASE_DATE,
                MoviesEntry.COLUMN_MOVIE_OVERVIEW,
                MoviesEntry.COLUMN_MOVIE_POSTER_URL,
                MoviesEntry.COLUMN_MOVIE_BACKDROP_URL,
                MoviesEntry.COLUMN_MOVIE_RATING};

        // Perform a query on the provider using the ContentResolver.
        // Use the {@link MovieEntry#CONTENT_URI} to access the movie data.
        Cursor cursor = mContext.getContentResolver().query(
                mCurrentMovieUri,   // The content URI of the movies table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null);
        Log.v("mytaga", "mCurrentMovieUri is :"+mCurrentMovieUri.toString());
        Log.v("mytaga", "cursor is :"+cursor.toString());
        int newPosition = cursor.getPosition();
        Log.v("mytaga", "cursor.getPosition() is :"+cursor.getPosition());


        try {
            Log.i("mytaga", "cursor.moveToPosition() is :"+cursor.moveToPosition(position));
            if (cursor.moveToPosition(position)) {

                cursor.moveToPosition(newPosition);
                Log.v("mytaga", "cursor.moveToPosition(newPosition) is :" + cursor.moveToPosition(newPosition));
                int currentId = cursor.getInt(cursor.getColumnIndex(MoviesEntry._ID));
                Log.v("mytaga", "currentId is :" + currentId);
                if (cursor == null) {
                    return false;
                } else {
                    if (currentId == position) {

                        int rowsDeleted = mContext.getContentResolver().delete(mCurrentMovieUri, null, null);
                        if (rowsDeleted == 0) {
                            // If no rows were deleted, then there was an error with the delete.
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.delete_movie_failed),
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // Otherwise, the delete was successful and we can display a toast.
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.delete_movie_successful),
                                    Toast.LENGTH_SHORT).show();
                            myFavoriteStatus = true;
                        }
                    } else {
                        // Create a ContentValues object where column names are the keys,
                        // and movie attributes from the editor are the values.
                        ContentValues values = new ContentValues();
                        values.put(MoviesEntry.COLUMN_MOVIE_TITLE, mMovie.getMovieTitle());
                        values.put(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE, mMovie.getMovieReleaseDate());
                        values.put(MoviesEntry.COLUMN_MOVIE_OVERVIEW, mMovie.getMovieOverview());
                        values.put(MoviesEntry.COLUMN_MOVIE_POSTER_URL, mMovie.getMoviePosterPath());
                        values.put(MoviesEntry.COLUMN_MOVIE_BACKDROP_URL, mMovie.getMovieBackdropPath());
                        values.put(MoviesEntry.COLUMN_MOVIE_RATING, mMovie.getMovieVoteAverage() / 2);

                        // Insert a new movie into the provider, returning the content URI for the new movie.
                        Uri newUri = mContext.getContentResolver().insert(MoviesEntry.CONTENT_URI, values);

                        // Show a toast message depending on whether or not the insertion was successful
                        if (newUri == null) {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.insert_movie_failed),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.insert_movie_successful),
                                    Toast.LENGTH_SHORT).show();
                            myFavoriteStatus = false;
                        }

                    }
                }
            }
        }finally {
            cursor.close();
        }
        return myFavoriteStatus;
    }
}
