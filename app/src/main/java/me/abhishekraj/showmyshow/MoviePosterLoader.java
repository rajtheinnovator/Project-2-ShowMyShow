package me.abhishekraj.showmyshow;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 11/15/2016.
 */

public class MoviePosterLoader extends AsyncTaskLoader {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link MoviePosterLoader}.
     * @param context of the activity
     * @param url     to load data from
     */

    public MoviePosterLoader(Context context, String url) {
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
    public ArrayList<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.v("############", "loadInBackground called");
        // Perform the network request, parse the response, and extract a list of news.
        ArrayList<Movie> movies = DefaultMovieQueryUtils.fetchMovieData(mUrl);
        Log.v("############", "loadInBackground finished");
        return movies;
    }
}