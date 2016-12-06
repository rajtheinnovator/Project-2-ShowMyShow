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

import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.SORT_BY_KEY;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DefaultQuery.SORT_BY_POPULARITY_VALUE_DESCENDING;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final int DEFAULT_MOVIE_LOADER_ID = 1111;
    private static final int TOP_RATED_MOVIE_LOADER_ID = 9999;
    ArrayList<Movie> defaultMovies;
    ArrayList<Movie> topRatedMovies;
    DefaultMovieAdapter mDefaultMovieAdapter;
    TopRatedMovieAdapter mTopRatedMovieAdapter;
    RecyclerView mDefaultMovieRecyclerView;
    RecyclerView mTopRatedMovieRecyclerView;
    Uri.Builder uriBuilder;
    ArrayList<Movie> nullChecker;
    String bundleNullOrNot;
    String savedInstance;
    LinearLayoutManager layoutManagerMoviePoster;
    LinearLayoutManager layoutManagerTopMoviePoster;

    public DefaultMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("############******", "onCreate called");
        if (savedInstanceState == null) {
            defaultMovies = new ArrayList<>();
            topRatedMovies = new ArrayList<>();
            nullChecker = new ArrayList<>();
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
                Log.v("############******", "startTopRatedMoviesLoaderManager called");
                startTopRatedMoviesLoaderManager();

            }

        } else {
            savedInstance = "not empty";
            Log.v("############******", "onCreate savedInstance is " + savedInstance);
            defaultMovies = savedInstanceState.getParcelableArrayList("defaultMovies");
            topRatedMovies = savedInstanceState.getParcelableArrayList("topMovies");
            //updateDefaultMovieRecyclerView(defaultMovies);
            //updateTopRatedMovieRecyclerView(topRatedMovies);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("############******", "onCreateView savedInstance is " + savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_default_movie, container, false);

             /* Code referenced from the @link:
        * "https://guides.codepath.com/android/using-the-recyclerview"
        */
        // Lookup the recyclerview in activity layout
        mDefaultMovieRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDefaultMovies);
        mTopRatedMovieRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTopMoviesMovies);

             /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
        layoutManagerMoviePoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerMoviePoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mDefaultMovieRecyclerView.setLayoutManager(layoutManagerMoviePoster);

             /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
        layoutManagerTopMoviePoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerTopMoviePoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mTopRatedMovieRecyclerView.setLayoutManager(layoutManagerTopMoviePoster);

        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
        snapHelperStart.attachToRecyclerView(mTopRatedMovieRecyclerView);

        SnapHelper snapHelperForDefaultMovieRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForDefaultMovieRecyclerView.attachToRecyclerView(mDefaultMovieRecyclerView);

        //Log.v("############******", "onCreateView called and budle is" + bundleNullOrNot);


        /* Code referenced from the @link:
        * "https://guides.codepath.com/android/using-the-recyclerview"
        */

        // Create mDefaultMovieAdapter passing in the sample user data
        mDefaultMovieAdapter = new DefaultMovieAdapter(getActivity(), defaultMovies);
        mDefaultMovieAdapter.setMovieData(defaultMovies);
        // Attach the mDefaultMovieAdapter to the recyclerview to populate items
        mDefaultMovieRecyclerView.setAdapter(mDefaultMovieAdapter);

        // Create mDefaultMovieAdapter passing in the sample user data
        mTopRatedMovieAdapter = new TopRatedMovieAdapter(getActivity(), topRatedMovies);
        mTopRatedMovieAdapter.setMovieData(topRatedMovies);
        // Attach the mDefaultMovieAdapter to the recyclerview to populate items
        mTopRatedMovieRecyclerView.setAdapter(mTopRatedMovieAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDefaultMovieAdapter = new DefaultMovieAdapter(getActivity(), defaultMovies);
        mTopRatedMovieAdapter = new TopRatedMovieAdapter(getActivity(), topRatedMovies);
        mTopRatedMovieAdapter.setMovieData(topRatedMovies);
        mDefaultMovieAdapter.setMovieData(defaultMovies);
        mDefaultMovieRecyclerView.setAdapter(mDefaultMovieAdapter);
        mTopRatedMovieRecyclerView.setAdapter(mTopRatedMovieAdapter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("############******", "onSaveInstanceState called");
        outState.putParcelableArrayList("defaultMovies", defaultMovies);
        outState.putParcelableArrayList("topMovies", topRatedMovies);
    }


    private void startPopularMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############******", "initLoader called with id " + DEFAULT_MOVIE_LOADER_ID);
        loaderManager.initLoader(DEFAULT_MOVIE_LOADER_ID, null, this);
        Log.v("############******", "startPopularMoviesLoaderManager finished");
    }

    private void startTopRatedMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############******", "initLoader called with id " + TOP_RATED_MOVIE_LOADER_ID);
        loaderManager.initLoader(TOP_RATED_MOVIE_LOADER_ID, null, this);
        Log.v("############******", "startTopRatedMoviesLoaderManager finished");
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        if (id == DEFAULT_MOVIE_LOADER_ID) {
            Log.v("############******", "onCreateLoader called with id " + DEFAULT_MOVIE_LOADER_ID);
            Uri baseUri = Uri.parse(UrlsAndConstants.DefaultQuery.DEFAULT_URL);
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_POPULARITY_VALUE_DESCENDING);

        } else if (id == TOP_RATED_MOVIE_LOADER_ID) {
            Log.v("############", "onCreateLoader called");
            Uri baseUri = Uri.parse("https://api.themoviedb.org/3/movie/upcoming");
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
        }
        return new DefaultMovieLoader(getActivity().getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> incomingMovieArrayList) {
        switch (loader.getId()) {
            case DEFAULT_MOVIE_LOADER_ID:
                Log.v("############******", "onLoadFinished called with id " + DEFAULT_MOVIE_LOADER_ID);
                if (incomingMovieArrayList.isEmpty()) {
                    Log.v("******************", "defaultMovies isEmpty");
                    return;
                } else {
                    defaultMovies = incomingMovieArrayList;
                    mDefaultMovieAdapter = new DefaultMovieAdapter(getActivity(), defaultMovies);
                    mDefaultMovieAdapter.setMovieData(defaultMovies);
                    mDefaultMovieRecyclerView.setAdapter(mDefaultMovieAdapter);

                }
                break;
            case TOP_RATED_MOVIE_LOADER_ID:
                /*
                 * prevent onLoadFinished from called twice using @param nullChecker
                */
                if (nullChecker.isEmpty()) {
                    nullChecker = incomingMovieArrayList;
                    Log.v("############******", "onLoadFinished called with id " + TOP_RATED_MOVIE_LOADER_ID);
                    if (incomingMovieArrayList.isEmpty()) {
                        Log.v("******************", "defaultMovies isEmpty");
                        return;
                    } else {
                        topRatedMovies = incomingMovieArrayList;
                        mTopRatedMovieAdapter = new TopRatedMovieAdapter(getActivity(), topRatedMovies);
                        mTopRatedMovieAdapter.setMovieData(topRatedMovies);
                        mTopRatedMovieRecyclerView.setAdapter(mTopRatedMovieAdapter);
                    }

                } else {
                    return;
                }
                break;
            default:
                break;
        }
    }

    public void updateDefaultMovieRecyclerView(ArrayList<Movie> movies) {
        Log.v("############******", "defaultMovies are" + movies.get(0).getMovieTitle());
        Log.v("############******", "defaultMovies are" + movies.get(1).getMovieTitle());
//         Attach the mDefaultMovieAdapter to the recyclerview to populate items
        mDefaultMovieAdapter.setMovieData(movies);
        Log.v("############", " mDefaultMovieAdapter.setMovieDetailsBundleData(movie) finished");
        mDefaultMovieRecyclerView.setAdapter(mDefaultMovieAdapter);
        Log.v("############", " mMovieReviewRecyclerView.setAdapter(mDefaultMovieAdapter); finished");
        defaultMovies = new ArrayList<>();
        getLoaderManager().destroyLoader(DEFAULT_MOVIE_LOADER_ID);
    }

    public void updateTopRatedMovieRecyclerView(ArrayList<Movie> movies) {
        Log.v("############******", "topMovies are" + movies.get(0).getMovieTitle());
        Log.v("############******", "topMovies are" + movies.get(1).getMovieTitle());
        // Attach the mDefaultMovieAdapter to the recyclerview to populate items
        mTopRatedMovieAdapter.setMovieData(movies);
        Log.v("############", " mDefaultMovieAdapter.setMovieDetailsBundleData(movie) finished");
        mTopRatedMovieRecyclerView.setAdapter(mTopRatedMovieAdapter);
        Log.v("############", " mMovieReviewRecyclerView.setAdapter(mDefaultMovieAdapter); finished");
        defaultMovies = new ArrayList<>();
        getLoaderManager().destroyLoader(TOP_RATED_MOVIE_LOADER_ID);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        Log.v("############******", "onLoaderReset called ");
    }
}
