package me.abhishekraj.showmyshow.Utils;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.DataSupplierClasses.Movie;

/**
 * Created by ABHISHEK RAJ on 11/15/2016.
 */

public class DefaultMovieLoader extends AsyncTaskLoader {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link DefaultMovieLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public DefaultMovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        ArrayList<Movie> movies = QueryUtils.fetchMovieData(mUrl);
        return movies;
    }
}