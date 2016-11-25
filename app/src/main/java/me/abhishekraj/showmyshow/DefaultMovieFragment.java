package me.abhishekraj.showmyshow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.API_KEY_PARAM_VALUE;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final int DEFAULT_MOVIE_LOADER_ID = 1;
    ArrayList<Movie> movies;
    DefaultMovieAdapter mAdapter;
    RecyclerView mRecyclerView;

    public DefaultMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("############", "onCreateView called");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_default_movie, container, false);
        Movie movie = new Movie("ram", 2, "path");
        if (savedInstanceState==null){
            movies = new ArrayList<>();
        }

        //First of all check if network is connected or not then only start the loader
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

          /* fetch data. Get a reference to the LoaderManager, in order to interact with loaders. */
            startLoaderManager();
            Log.v("############", "startLoaderManager called");
        }

        // Lookup the recyclerview in activity layout
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovies);

        // Create mAdapter passing in the sample user data
        mAdapter = new DefaultMovieAdapter(getActivity(), movies);
        // Attach the mAdapter to the recyclerview to populate items
        mRecyclerView.setAdapter(mAdapter);

        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        final StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        mRecyclerView.setLayoutManager(gridLayoutManager);
        // That's all!

        return rootView;
    }

    private void startLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(DEFAULT_MOVIE_LOADER_ID, null, this);
        Log.v("############", "startLoaderManager finished");
    }


    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        Log.v("############", "onCreateLoader called");
        Uri baseUri = Uri.parse(UrlsAndConstants.DefaultQuery.DEFAULT_URL);
        Log.v("############", "baseUri is "+baseUri.toString());
        Uri.Builder uriBuilder = baseUri.buildUpon();
        Log.v("############", "uriBuilder is "+uriBuilder.toString());
        uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
        Log.v("############", "uriBuilder.toString() is "+uriBuilder.toString());
        String urls = "https://api.themoviedb.org/3/discover/movie?api_key=4182aa25bab27d06344e404f65c4ae76";
        return new DefaultMovieLoader(getActivity().getApplicationContext(), urls);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movie) {
        Log.v("############", "startLoaderManager finished");
        if (movie.isEmpty()) {
            Log.v("******************", "movies isEmpty");
            return;
        } else {
            Log.v("############", "movies are"+movie);
            // Attach the mAdapter to the recyclerview to populate items

            mAdapter.setWeatherData(movie);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        Log.v("############", "onLoaderReset called");
    }
}
