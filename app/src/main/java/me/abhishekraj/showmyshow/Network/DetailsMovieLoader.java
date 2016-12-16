package me.abhishekraj.showmyshow.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import me.abhishekraj.showmyshow.model.movie.MovieDetailsBundle;
import me.abhishekraj.showmyshow.utils.MovieDetailsQueryUtils;

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
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */

    @Override
    public MovieDetailsBundle loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        /* Perform the network request, parse the response, and extract a list of news. */
        MovieDetailsBundle movies = MovieDetailsQueryUtils.fetchMovieData(mUrl);
        return movies;
    }
}