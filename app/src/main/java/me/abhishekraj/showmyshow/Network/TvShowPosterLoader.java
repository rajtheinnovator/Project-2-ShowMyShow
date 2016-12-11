package me.abhishekraj.showmyshow.Network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.Model.TvShow.TvShow;
import me.abhishekraj.showmyshow.Utils.TvShowPosterQueryDetails;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowPosterLoader extends AsyncTaskLoader {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link TvShowPosterLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */

    public TvShowPosterLoader(Context context, String url) {
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
    public ArrayList<TvShow> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.v("############", "loadInBackground called");
        // Perform the network request, parse the response, and extract a list of news.
        ArrayList<TvShow> tvShows = TvShowPosterQueryDetails.fetchTvShowData(mUrl);
        Log.v("############", "loadInBackground finished");
        return tvShows;
    }
}