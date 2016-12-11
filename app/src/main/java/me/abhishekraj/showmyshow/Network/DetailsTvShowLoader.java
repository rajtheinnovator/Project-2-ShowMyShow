package me.abhishekraj.showmyshow.Network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import me.abhishekraj.showmyshow.Model.TvShow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.Utils.TvShowDetailsQueryUtils;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class DetailsTvShowLoader extends AsyncTaskLoader {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link DetailsTvShowLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */

    public DetailsTvShowLoader(Context context, String url) {
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
    public TvShowDetailsBundle loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.v("############", "loadInBackground called");
        // Perform the network request, parse the response, and extract a list of news.
        TvShowDetailsBundle tvShows = TvShowDetailsQueryUtils.fetchTvShowData(mUrl);
        Log.v("############", "loadInBackground finished");
        return tvShows;
    }
}