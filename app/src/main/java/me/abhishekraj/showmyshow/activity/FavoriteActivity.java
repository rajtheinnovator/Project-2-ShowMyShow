package me.abhishekraj.showmyshow.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.adapter.FavoriteCursorAdapter;
import me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry;

import static me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry.COLUMN_FAVORITE_STATUS;
import static me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry.COLUMN_MOVIE_ID;
import static me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry.COLUMN_MOVIE_RATING;
import static me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE;
import static me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Which loader is running can be known using this static variable
    private static final int MOVIE_LOADER = 0;

    FavoriteCursorAdapter mCursorAdapter;

    /*Empty State Views*/
    RelativeLayout emptyView;
    GridView favoriteGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // array of database column names
        String[] columns = new String[]{
                COLUMN_MOVIE_ID,
                COLUMN_MOVIE_TITLE,
                COLUMN_MOVIE_RATING,
                COLUMN_MOVIE_RELEASE_DATE,
                COLUMN_FAVORITE_STATUS};

        // array of views to display database values
        int[] viewIds = new int[]{
                R.id.movie_detail_title_image_view,
                R.id.moivie_detail_title_text_view,
                R.id.movieRatingInsideMovieDetailsFragment,
                R.id.movie_release_date_text_view,
                R.id.favorite
        };

        //instantiate the Views
        favoriteGridView = (GridView) findViewById(R.id.favoriteGridView);
        emptyView = (RelativeLayout) findViewById(R.id.emptyView);

        // CursorAdapter to load data from the Cursor into the GridView
        mCursorAdapter = new FavoriteCursorAdapter(this,
                R.layout.list_item_favorite_movie_poster,
                null,
                columns,
                viewIds,
                0
        );

        getSupportLoaderManager().initLoader(MOVIE_LOADER, null, this);
        favoriteGridView.setAdapter(mCursorAdapter);
    }

    /* Use of Loaders referenced from the @link:
    * "http://www.appstoremarketresearch.com/articles/android-tutorial-loader-simple-cursor-adapter/"
    * as well as from my previous work/project, link at: "https://github.com/rajtheinnovator/InventoryApp"
    */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MoviesEntry._ID,
                MoviesEntry.COLUMN_MOVIE_TITLE,
                MoviesEntry.COLUMN_MOVIE_RELEASE_DATE,
                MoviesEntry.COLUMN_MOVIE_OVERVIEW,
                MoviesEntry.COLUMN_MOVIE_POSTER_URL,
                MoviesEntry.COLUMN_MOVIE_BACKDROP_URL,
                MoviesEntry.COLUMN_MOVIE_ID,
                MoviesEntry.COLUMN_MOVIE_RATING,
                MoviesEntry.COLUMN_FAVORITE_STATUS};
        // this method will execute the Content Providers query method on a background thread
        return new CursorLoader(
                this,   // Parent activity context
                MoviesEntry.CONTENT_URI,        // Table to query
                projection,     // Projection to return
                null,            // No selection clause
                null,            // No selection arguments
                null             // Default sort order
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        /*Check if any data is there. If nothing is found then set the empty state view*/
        if (data.getCount()!=0){
            favoriteGridView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else {
            favoriteGridView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        // Update {@link FavoriteCursorAdapter} with this new cursor containing updated movie data
        mCursorAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // callback called when the data needs to be deleted
        mCursorAdapter.changeCursor(null);
    }
}
