package me.abhishekraj.showmyshow.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry;

/**
 * Created by ABHISHEK RAJ on 4/20/2017.
 */

public class MovieProvider extends ContentProvider {
    /**
     * URI matcher code for the content URI for the movies table
     */

    private static final int MOVIES = 100;

    /**
     * URI matcher code for the content URI for a single pet in the movie table
     */
    private static final int MOVIE_ID = 200;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.movies/movies" will map to the
        // integer code {@link #MOVIES}. This URI is used to provide access to MULTIPLE rows
        // of the movies table.
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);

        // The content URI of the form "content://com.example.android.movies/movies/#" will map to the
        // integer code {@link #MOVIES}. This URI is used to provide access to ONE single row
        // of the movies table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.movies/movies/3" matches, but
        // "content://com.example.android.movies/movies" (without a number at the end) doesn't match.
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_ID);
    }


    /**
     * Database helper object
     */
    private MovieDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        Log.i("mytaga", "int match = sUriMatcher.match(uri); :"+ match);

        switch (match) {
            case MOVIES:
                // For the MOVIES code, query the movies table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the movies table.
                cursor = database.query(MoviesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case MOVIE_ID:
                // For the MOVIE_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.movies/movies/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = MoviesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the movies table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(MoviesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return insertMovie(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertMovie(Uri uri, ContentValues values) {
       // Log.v("my_tag", "Received Uri to be matched insert is :"+uri.toString());
        Log.i("my_tag", "Received Uri to be matched insert is :"+uri.toString());
        // Check that the product name is not null
        String title = values.getAsString(MoviesEntry.COLUMN_MOVIE_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("Product requires a name");
        }

        // Check that the product name is not null
        String releaseDate = values.getAsString(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE);
        if (releaseDate == null) {
            throw new IllegalArgumentException("Product requires a detail");
        }

        // If the price is provided, check that it's greater than or equal to 0
        String overview = values.getAsString(MoviesEntry.COLUMN_MOVIE_OVERVIEW);
        if (overview == null) {
            throw new IllegalArgumentException("Product requires valid price");
        }

        String posterUrl = values.getAsString(MoviesEntry.COLUMN_MOVIE_POSTER_URL);
        if (posterUrl == null) {
            throw new IllegalArgumentException("Product requires valid quantity");
        }
        String backdropUrl = values.getAsString(MoviesEntry.COLUMN_MOVIE_BACKDROP_URL);
        if (backdropUrl == null) {
            throw new IllegalArgumentException("Product requires valid quantity");
        }
        Integer ratings = values.getAsInteger(MoviesEntry.COLUMN_MOVIE_RATING);
        if (ratings != null && ratings < 0) {
            throw new IllegalArgumentException("Product requires valid quantity");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new product with the given values
        long id = database.insert(MoviesEntry.TABLE_NAME, null, values);

        Log.i("my_tag", "values is :"+values.toString());
       // Log.e("my_tag", "values is :"+values.toString());
       // Log.v("my_tag", "values is :"+values.toString());
        if (id == -1) {
            return null;
        }

        // Notify all listeners that the data has changed for the product content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
      //  Log.v("my_tag", "Received Uri to be matched delete is :"+uri.toString());
        Log.i("my_tag", "Received Uri to be matched delete is :"+uri.toString());
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        Log.i("my_tag", "int match is :"+match);
       // Log.e("my_tag", "int match is :"+match);
       // Log.v("my_tag", "int match is :"+match);
        switch (match) {

            case MOVIES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(MoviesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                // Delete a single row given by the ID in the URI
                selection = MoviesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                Log.i("my_tag", "selection is :"+selection);
             //   Log.e("my_tag", "selection is :"+selection);
             //   Log.v("my_tag", "selection is :"+selection);
                Log.i("my_tag", "selectionArgs is :"+selectionArgs[0]);
              //  Log.e("my_tag", "selectionArgs is :"+selectionArgs[0]);
              //  Log.v("my_tag", "selectionArgs is :"+selectionArgs[0]);
                rowsDeleted = database.delete(MoviesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return updateMovie(uri, contentValues, selection, selectionArgs);
            case MOVIE_ID:
                // For the MOVIE_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = MoviesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateMovie(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update movies in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more movies).
     * Return the number of rows that were successfully updated.
     */
    private int updateMovie(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(MoviesEntry.COLUMN_MOVIE_TITLE)) {
            String title = values.getAsString(MoviesEntry.COLUMN_MOVIE_TITLE);
            if (title == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        if (values.containsKey(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE)) {
            String releaseDate = values.getAsString(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE);
            if (releaseDate == null) {
                throw new IllegalArgumentException("Product requires a detail");
            }
        }
        if (values.containsKey(MoviesEntry.COLUMN_MOVIE_OVERVIEW)) {
            String overview = values.getAsString(MoviesEntry.COLUMN_MOVIE_OVERVIEW);
            if (overview == null) {
                throw new IllegalArgumentException("Product requires a detail");
            }
        }
        if (values.containsKey(MoviesEntry.COLUMN_MOVIE_POSTER_URL)) {
            String posterUrl = values.getAsString(MoviesEntry.COLUMN_MOVIE_POSTER_URL);
            if (posterUrl == null) {
                throw new IllegalArgumentException("Product requires a detail");
            }
        }
        if (values.containsKey(MoviesEntry.COLUMN_MOVIE_BACKDROP_URL)) {
            String backdropUrl = values.getAsString(MoviesEntry.COLUMN_MOVIE_BACKDROP_URL);
            if (backdropUrl == null) {
                throw new IllegalArgumentException("Product requires a detail");
            }
        }
        if (values.containsKey(MoviesEntry.COLUMN_MOVIE_RATING)) {
            // Check that the price is greater than or equal to 0
            Integer ratings = values.getAsInteger(MoviesEntry.COLUMN_MOVIE_RATING);
            if (ratings != null && ratings < 0) {
                throw new IllegalArgumentException("Product requires valid price");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(MoviesEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MoviesEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return MoviesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
