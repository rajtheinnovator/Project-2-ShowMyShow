package me.abhishekraj.showmyshow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import static me.abhishekraj.showmyshow.UrlsAndConstants.MoviePosterQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.UrlsAndConstants.MoviePosterQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.UrlsAndConstants.MoviePosterQuery.SORT_BY_KEY;
import static me.abhishekraj.showmyshow.UrlsAndConstants.MoviePosterQuery.SORT_BY_POPULARITY_VALUE_DESCENDING;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviePosterFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final int POPULAR_MOVIE_LOADER_ID = 1111;
    private static final int UPCOMING_MOVIE_LOADER_ID = 9999;
    ArrayList<Movie> popularMovies;
    ArrayList<Movie> upcomingMovies;
    PopularMoviesAdapter mPopularMoviesAdapter;
    UpcomingMovieAdapter mUpcomingMovieAdapter;
    RecyclerView mPopularMovieRecyclerView;
    RecyclerView mUpcomingMovieRecyclerView;
    Uri.Builder uriBuilder;
    String savedInstance;
    LinearLayoutManager layoutManagerPopularMoviesPoster;
    LinearLayoutManager layoutManagerUpcomingMoviesPoster;
    ArrayList<Movie> popularMoviesParcelable;
    ArrayList<Movie> upcomingMoviesParcelable;

    public MoviePosterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("############******", "onCreate called");
        if (savedInstanceState != null) {
            savedInstance = "not empty";
            Log.v("############******", "onCreate savedInstance is " + savedInstance);
            popularMovies = savedInstanceState.getParcelableArrayList("popularMovies");
            upcomingMovies = savedInstanceState.getParcelableArrayList("upcomingMovies");

        } else {
            popularMovies = new ArrayList<>();
            upcomingMovies = new ArrayList<>();
            savedInstance = "empty";
            Log.v("############******", "onCreate savedInstance is " + savedInstance);
            //First of all check if network is connected or not then only start the loader
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                /*
                 *fetch data. Get a reference to the LoaderManager, in order to interact with loaders.
                */
                Log.v("############******", "startPopularMoviesLoaderManager called");
                startPopularMoviesLoaderManager();
                Log.v("############******", "startUpcomingMoviesLoaderManager called");
                startUpcomingMoviesLoaderManager();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("############******", "onCreateView savedInstance is " + savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_posters, container, false);

        /* Code referenced from the @link:
         * "https://guides.codepath.com/android/using-the-recyclerview"
         */
        /*
         * Lookup the recyclerView in activity layout
         */
        mPopularMovieRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPopularMovies);
        mUpcomingMovieRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewUpcomingMovies);

         /*
          * Setup layout manager for items with orientation
          * Also supports `LinearLayoutManager.HORIZONTAL`
          */
        layoutManagerPopularMoviesPoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerPopularMoviesPoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mPopularMovieRecyclerView.setLayoutManager(layoutManagerPopularMoviesPoster);

         /*
          * Setup layout manager for items with orientation
          * Also supports `LinearLayoutManager.HORIZONTAL`
          */
        layoutManagerUpcomingMoviesPoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerUpcomingMoviesPoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mUpcomingMovieRecyclerView.setLayoutManager(layoutManagerUpcomingMoviesPoster);

        SnapHelper snapHelperForPopularMovieRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForPopularMovieRecyclerView.attachToRecyclerView(mPopularMovieRecyclerView);

        SnapHelper snapHelperForUpcomingMovieRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForUpcomingMovieRecyclerView.attachToRecyclerView(mUpcomingMovieRecyclerView);

        /* Code referenced from the @link:
        * "https://guides.codepath.com/android/using-the-recyclerview"
        */

        // Create mPopularMoviesAdapter passing in the sample user data
        mPopularMoviesAdapter = new PopularMoviesAdapter(getActivity(), popularMovies);
        mPopularMoviesAdapter.setMovieData(popularMovies);
        // Attach the mPopularMoviesAdapter to the recyclerview to populate items
        mPopularMovieRecyclerView.setAdapter(mPopularMoviesAdapter);

        // Create mUpcomingMoviesAdapter passing in the sample user data
        mUpcomingMovieAdapter = new UpcomingMovieAdapter(getActivity(), upcomingMovies);
        mUpcomingMovieAdapter.setMovieData(upcomingMovies);
        // Attach the mUpcomingMoviesAdapter to the recyclerview to populate items
        mUpcomingMovieRecyclerView.setAdapter(mUpcomingMovieAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        popularMoviesParcelable = popularMovies;
        upcomingMoviesParcelable = upcomingMovies;
        Log.v("############******", "onSaveInstanceState called");
        outState.putParcelableArrayList("popularMovies", popularMoviesParcelable);
        outState.putParcelableArrayList("upcomingMovies", upcomingMoviesParcelable);
        super.onSaveInstanceState(outState);
    }


    private void startPopularMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############******", "initLoader called with id " + POPULAR_MOVIE_LOADER_ID);
        loaderManager.initLoader(POPULAR_MOVIE_LOADER_ID, null, this);
        Log.v("############******", "startPopularMoviesLoaderManager finished");
    }

    private void startUpcomingMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############******", "initLoader called with id " + UPCOMING_MOVIE_LOADER_ID);
        loaderManager.initLoader(UPCOMING_MOVIE_LOADER_ID, null, this);
        Log.v("############******", "startUpcomingMoviesLoaderManager finished");
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        if (id == POPULAR_MOVIE_LOADER_ID) {
            Log.v("############******", "onCreateLoader called with id " + POPULAR_MOVIE_LOADER_ID);
            Uri baseUri = Uri.parse(UrlsAndConstants.MoviePosterQuery.DEFAULT_URL);
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_POPULARITY_VALUE_DESCENDING);

        } else if (id == UPCOMING_MOVIE_LOADER_ID) {
            Log.v("############", "onCreateLoader called with id " + UPCOMING_MOVIE_LOADER_ID);
            Uri baseUri = Uri.parse("https://api.themoviedb.org/3/movie/upcoming");
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
        }
        return new MoviePosterLoader(getActivity().getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> incomingMovieArrayList) {
        switch (loader.getId()) {
            case POPULAR_MOVIE_LOADER_ID:
                Log.v("############******", "onLoadFinished called with id " + POPULAR_MOVIE_LOADER_ID);
                if (incomingMovieArrayList.isEmpty()) {
                    Log.v("******************", "popularMovies isEmpty");
                    return;
                } else {
                    popularMovies = incomingMovieArrayList;
                    mPopularMoviesAdapter = new PopularMoviesAdapter(getActivity(), popularMovies);
                    mPopularMoviesAdapter.setMovieData(popularMovies);
                    mPopularMovieRecyclerView.setAdapter(mPopularMoviesAdapter);

                }
                break;
            case UPCOMING_MOVIE_LOADER_ID:
                Log.v("############******", "onLoadFinished called with id " + UPCOMING_MOVIE_LOADER_ID);
                if (incomingMovieArrayList.isEmpty()) {
                    Log.v("******************", "popularMovies isEmpty");
                    return;
                } else {
                    upcomingMovies = incomingMovieArrayList;
                    mUpcomingMovieAdapter = new UpcomingMovieAdapter(getActivity(), upcomingMovies);
                    mUpcomingMovieAdapter.setMovieData(upcomingMovies);
                    mUpcomingMovieRecyclerView.setAdapter(mUpcomingMovieAdapter);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        upcomingMoviesParcelable = upcomingMovies;
        popularMoviesParcelable = popularMovies;
        Log.v("############******", "onLoaderReset called ");
    }
}