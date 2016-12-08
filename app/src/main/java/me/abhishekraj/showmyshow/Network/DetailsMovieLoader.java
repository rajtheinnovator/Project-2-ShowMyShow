package me.abhishekraj.showmyshow.Network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import me.abhishekraj.showmyshow.Model.MovieDetailsBundle;
import me.abhishekraj.showmyshow.Utils.MovieDetailsQueryUtils;

/**
 * Created by ABHISHEK RAJ on 12/1/2016.
 */

public class DetailsMovieLoader extends AsyncTaskLoader {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link DetailsMovieLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */

    public DetailsMovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.v("############", "url is " + mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.v("############", "onStartLoading called");
    }

    /**
     * This is on a background thread.
     */

    @Override
    public MovieDetailsBundle loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.v("############", "loadInBackground called");
        // Perform the network request, parse the response, and extract a list of news.
        MovieDetailsBundle movies = MovieDetailsQueryUtils.fetchMovieData(mUrl);
        Log.v("############", "loadInBackground finished");
        return movies;
    }
}