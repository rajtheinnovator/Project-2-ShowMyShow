package me.abhishekraj.showmyshow.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ABHISHEK RAJ on 4/20/2017.
 */

public class MovieContract {
    /*
To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
*/
    private MovieContract() {
    }


    public static final String CONTENT_AUTHORITY = "me.abhishekraj.showmyshow";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    /**
     * Inner class that defines constant values for the inventory database table.
     * Each entry in the table represents a single product.
     */

    public static final class MoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public final static String TABLE_NAME = "movies";


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_MOVIE_TITLE = "movieTitle";
        public final static String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public final static String COLUMN_MOVIE_OVERVIEW = "movieOverview";
        public final static String COLUMN_MOVIE_POSTER_URL = "moviePosterUrl";
        public final static String COLUMN_MOVIE_BACKDROP_URL = "movieBackdropUrl";
        public final static String COLUMN_MOVIE_RATING = "movieRating";
        public final static String COLUMN_MOVIE_ID = "movieId";

        public static String getIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
