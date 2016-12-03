package me.abhishekraj.showmyshow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;

import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.SORT_BY_KEY;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.SORT_BY_POPULARITY_VALUE_DESCENDING;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.SORT_BY_TOP_RATED_VALUE_DESCENDING;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final int DEFAULT_MOVIE_LOADER_ID = 1;
    private static final int TOP_RATED_MOVIE_LOADER_ID = 9999;
    ArrayList<Movie> movies;
    DefaultMovieAdapter mDefaultMovieAdapter;
    TopRatedMovieAdapter mTopRatedMovieAdapter;
    RecyclerView mDefaultMovieRecyclerView;
    RecyclerView mTopRatedMovieRecyclerView;
    Uri.Builder uriBuilder;

    public DefaultMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("############", "onCreateView called");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_default_movie, container, false);

        if (savedInstanceState == null) {
            movies = new ArrayList<>();
        }

        //First of all check if network is connected or not then only start the loader
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

          /* fetch data. Get a reference to the LoaderManager, in order to interact with loaders. */
            startPopularMoviesLoaderManager();
            startTopRatedMoviesLoaderManager();
            Log.v("############", "startPopularMoviesLoaderManager called");
        }
        /* Code referenced from the @link:
        * "https://guides.codepath.com/android/using-the-recyclerview"
        */
        // Lookup the recyclerview in activity layout
        mDefaultMovieRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDefaultMovies);
        mTopRatedMovieRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTopMoviesMovies);

        // Create mDefaultMovieAdapter passing in the sample user data
        mDefaultMovieAdapter = new DefaultMovieAdapter(getActivity(), movies);
        // Attach the mDefaultMovieAdapter to the recyclerview to populate items
        mDefaultMovieRecyclerView.setAdapter(mDefaultMovieAdapter);

        // Create mDefaultMovieAdapter passing in the sample user data
        mTopRatedMovieAdapter = new TopRatedMovieAdapter(getActivity(), movies);
        // Attach the mDefaultMovieAdapter to the recyclerview to populate items
        mTopRatedMovieRecyclerView.setAdapter(mTopRatedMovieAdapter);

         /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
        LinearLayoutManager layoutManagerMoviePoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerMoviePoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mDefaultMovieRecyclerView.setLayoutManager(layoutManagerMoviePoster);

             /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
        LinearLayoutManager layoutManagerTopMoviePoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerMoviePoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mTopRatedMovieRecyclerView.setLayoutManager(layoutManagerTopMoviePoster);

        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
        snapHelperStart.attachToRecyclerView(mDefaultMovieRecyclerView);
        snapHelperStart.attachToRecyclerView(mTopRatedMovieRecyclerView);

        return rootView;
    }

    private void startPopularMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############", "startPopularMoviesLoaderManager started");
        loaderManager.initLoader(DEFAULT_MOVIE_LOADER_ID, null, this);
        Log.v("############", "startPopularMoviesLoaderManager finished");
    }

    private void startTopRatedMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############", "startPopularMoviesLoaderManager started");
        loaderManager.initLoader(TOP_RATED_MOVIE_LOADER_ID, null, this);
        Log.v("############", "startPopularMoviesLoaderManager finished");
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        if (id == DEFAULT_MOVIE_LOADER_ID) {
            Log.v("############", "onCreateLoader called");
            Uri baseUri = Uri.parse(UrlsAndConstants.DefaultQuery.DEFAULT_URL);
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_POPULARITY_VALUE_DESCENDING);
            //return new DefaultMovieLoader(getActivity().getApplicationContext(), uriBuilder.toString());
        } else if (id == TOP_RATED_MOVIE_LOADER_ID) {
            Log.v("############", "onCreateLoader called");
            Uri baseUri = Uri.parse(UrlsAndConstants.DefaultQuery.DEFAULT_URL);
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_TOP_RATED_VALUE_DESCENDING);
        }
        return new DefaultMovieLoader(getActivity().getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movie) {
        switch (loader.getId()) {
            case DEFAULT_MOVIE_LOADER_ID:
                Log.v("############", "startPopularMoviesLoaderManager finished");
                if (movie.isEmpty()) {
                    Log.v("******************", "movies isEmpty");
                    return;
                } else {
                    Log.v("############", "movies are" + movie);
                    // Attach the mDefaultMovieAdapter to the recyclerview to populate items
                    mDefaultMovieAdapter.setMovieData(movie);
                    Log.v("############", " mDefaultMovieAdapter.setMovieDetailsBundleData(movie) finished");
                    mDefaultMovieRecyclerView.setAdapter(mDefaultMovieAdapter);
                    Log.v("############", " mMovieReviewRecyclerView.setAdapter(mDefaultMovieAdapter); finished");
                }
            case TOP_RATED_MOVIE_LOADER_ID:
                Log.v("############", "startPopularMoviesLoaderManager finished");
                if (movie.isEmpty()) {
                    Log.v("******************", "movies isEmpty");
                    return;
                } else {
                    Log.v("############******", "movies are" + movie);
                    // Attach the mDefaultMovieAdapter to the recyclerview to populate items
                    mTopRatedMovieAdapter.setMovieData(movie);
                    Log.v("############", " mDefaultMovieAdapter.setMovieDetailsBundleData(movie) finished");
                    mTopRatedMovieRecyclerView.setAdapter(mTopRatedMovieAdapter);
                    Log.v("############", " mMovieReviewRecyclerView.setAdapter(mDefaultMovieAdapter); finished");
                }

        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        Log.v("############", "onLoaderReset called");
    }
}
