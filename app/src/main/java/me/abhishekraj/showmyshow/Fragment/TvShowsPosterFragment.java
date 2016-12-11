package me.abhishekraj.showmyshow.Fragment;


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

import me.abhishekraj.showmyshow.Adapter.TvShowPosterAdapter.AiredNowTvShowAdapter;
import me.abhishekraj.showmyshow.Adapter.TvShowPosterAdapter.PopularTvShowAdapter;
import me.abhishekraj.showmyshow.Adapter.TvShowPosterAdapter.TopRatedTvShowAdapter;
import me.abhishekraj.showmyshow.Model.TvShow.TvShow;
import me.abhishekraj.showmyshow.Network.TvShowPosterLoader;
import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.Utils.UrlsAndConstants;

import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.SORT_BY_KEY;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.SORT_BY_POPULARITY_VALUE_DESCENDING;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.SORT_BY_TOP_RATED_VALUE_DESCENDING;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.TvPosterQuery.SORT_BY_FIRST_AIR_DATES_DESCENDING;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowsPosterFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<TvShow>> {

    private static final int POPULAR_TV_SHOW_LOADER_ID = 7777;
    private static final int AIRED_NOW_TV_SHOW_LOADER_ID = 8888;
    private static final int TOP_RATED_TV_SHOW_LOADER_ID = 9999;
    ArrayList<TvShow> popularTvShows;
    ArrayList<TvShow> airedNowTvShows;
    ArrayList<TvShow> topRatedTvShows;
    PopularTvShowAdapter mPopularTvShowAdapter;
    AiredNowTvShowAdapter mAiredNowTvShowAdapter;
    TopRatedTvShowAdapter mTopRatedTvShowAdapter;
    RecyclerView mPopularTvShowRecyclerView;
    RecyclerView mAiredNowTvShowRecyclerView;
    RecyclerView mTopRatedTvShowRecyclerView;
    Uri.Builder uriBuilder;
    String savedInstance;
    LinearLayoutManager layoutManagerPopularTvShowPoster;
    LinearLayoutManager layoutManagerAiredNowTvShowPoster;
    LinearLayoutManager layoutManagerTopRatedTvShowPoster;

    public TvShowsPosterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("############******", "onCreate called");
        if (savedInstanceState != null) {
            savedInstance = "not empty";
            Log.v("############******", "onCreate savedInstance is " + savedInstance);
            popularTvShows = savedInstanceState.getParcelableArrayList("popularTvShows");
            airedNowTvShows = savedInstanceState.getParcelableArrayList("airedNowTvShows");
            topRatedTvShows = savedInstanceState.getParcelableArrayList("topRatedTvShows");

        } else {
            popularTvShows = new ArrayList<>();
            airedNowTvShows = new ArrayList<>();
            topRatedTvShows = new ArrayList<>();
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
                Log.v("############******", "startPopularTvShowsLoaderManager called");
                startPopularTvShowsLoaderManager();
                Log.v("############******", "startAiredNowTvShowsLoaderManager called");
                startAiredNowTvShowsLoaderManager();
                Log.v("############******", "startTopRatedTvShowsLoaderManager called");
                startTopRatedTvShowsLoaderManager();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("############******", "onCreateView savedInstance is " + savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tv_show_poster, container, false);

        /* Code referenced from the @link:
         * "https://guides.codepath.com/android/using-the-recyclerview"
         */
        /*
         * Lookup the recyclerView in activity layout
         */
        mPopularTvShowRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPopularTvShows);
        mAiredNowTvShowRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewAiredNowTvShows);
        mTopRatedTvShowRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTopRatedTvShows);

         /*
          * Setup layout manager for items with orientation
          * Also supports `LinearLayoutManager.HORIZONTAL`
          */
        layoutManagerPopularTvShowPoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerPopularTvShowPoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mPopularTvShowRecyclerView.setLayoutManager(layoutManagerPopularTvShowPoster);

         /*
          * Setup layout manager for items with orientation
          * Also supports `LinearLayoutManager.HORIZONTAL`
          */
        layoutManagerAiredNowTvShowPoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerAiredNowTvShowPoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mAiredNowTvShowRecyclerView.setLayoutManager(layoutManagerAiredNowTvShowPoster);

                 /*
          * Setup layout manager for items with orientation
          * Also supports `LinearLayoutManager.HORIZONTAL`
          */
        layoutManagerTopRatedTvShowPoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerTopRatedTvShowPoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mTopRatedTvShowRecyclerView.setLayoutManager(layoutManagerTopRatedTvShowPoster);

        SnapHelper snapHelperForPopularTvShowsRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForPopularTvShowsRecyclerView.attachToRecyclerView(mPopularTvShowRecyclerView);

        SnapHelper snapHelperForUpcomingTvShowsRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForUpcomingTvShowsRecyclerView.attachToRecyclerView(mAiredNowTvShowRecyclerView);

        SnapHelper snapHelperForTopRatedTvShowsRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForTopRatedTvShowsRecyclerView.attachToRecyclerView(mTopRatedTvShowRecyclerView);

        /* Code referenced from the @link:
        * "https://guides.codepath.com/android/using-the-recyclerview"
        */

        // Create mPopularTvShowAdapter passing in the sample user data
        mPopularTvShowAdapter = new PopularTvShowAdapter(getActivity(), popularTvShows);
        mPopularTvShowAdapter.setTvShowData(popularTvShows);
        // Attach the mPopularTvShowAdapter to the recyclerview to populate items
        mPopularTvShowRecyclerView.setAdapter(mPopularTvShowAdapter);

        // Create mAiredNowTvShowsAdapter passing in the sample user data
        mAiredNowTvShowAdapter = new AiredNowTvShowAdapter(getActivity(), airedNowTvShows);
        mAiredNowTvShowAdapter.setTvShowData(airedNowTvShows);
        // Attach the mAiredNowTvShowsAdapter to the recyclerview to populate items
        mAiredNowTvShowRecyclerView.setAdapter(mAiredNowTvShowAdapter);

        // Create mTopRatedTvShowAdapter passing in the sample user data
        mTopRatedTvShowAdapter = new TopRatedTvShowAdapter(getActivity(), topRatedTvShows);
        mTopRatedTvShowAdapter.setTvShowData(topRatedTvShows);
        // Attach the mTopRatedTvShowAdapter to the recyclerview to populate items
        mTopRatedTvShowRecyclerView.setAdapter(mTopRatedTvShowAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v("############******", "onSaveInstanceState called");
        outState.putParcelableArrayList("popularTvShows", popularTvShows);
        outState.putParcelableArrayList("airedNowTvShows", airedNowTvShows);
        outState.putParcelableArrayList("topRatedTvShows", topRatedTvShows);
        super.onSaveInstanceState(outState);
    }


    private void startPopularTvShowsLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############******", "initLoader called with id " + POPULAR_TV_SHOW_LOADER_ID);
        loaderManager.initLoader(POPULAR_TV_SHOW_LOADER_ID, null, this);
        Log.v("############******", "startPopularTvShowsLoaderManager finished");
    }

    private void startAiredNowTvShowsLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############******", "initLoader called with id " + AIRED_NOW_TV_SHOW_LOADER_ID);
        loaderManager.initLoader(AIRED_NOW_TV_SHOW_LOADER_ID, null, this);
        Log.v("############******", "startAiredNowTvShowsLoaderManager finished");
    }

    private void startTopRatedTvShowsLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############******", "initLoader called with id " + TOP_RATED_TV_SHOW_LOADER_ID);
        loaderManager.initLoader(TOP_RATED_TV_SHOW_LOADER_ID, null, this);
        Log.v("############******", "startTopRatedTvShowsLoaderManager finished");
    }

    @Override
    public Loader<ArrayList<TvShow>> onCreateLoader(int id, Bundle args) {
        if (id == POPULAR_TV_SHOW_LOADER_ID) {
            Log.v("############******", "onCreateLoader called with id " + POPULAR_TV_SHOW_LOADER_ID);
            Uri baseUri = Uri.parse(UrlsAndConstants.TvPosterQuery.DISCOVER_TV_SHOW_DEFAULT_URL);
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_POPULARITY_VALUE_DESCENDING);

        } else if (id == AIRED_NOW_TV_SHOW_LOADER_ID) {
            Log.v("############", "onCreateLoader called with id " + AIRED_NOW_TV_SHOW_LOADER_ID);
            Uri baseUri = Uri.parse(UrlsAndConstants.TvPosterQuery.DISCOVER_TV_SHOW_DEFAULT_URL);
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_FIRST_AIR_DATES_DESCENDING);
        } else if (id == TOP_RATED_TV_SHOW_LOADER_ID) {
            Log.v("############", "onCreateLoader called with id " + TOP_RATED_TV_SHOW_LOADER_ID);
            Uri baseUri = Uri.parse(UrlsAndConstants.TvPosterQuery.DISCOVER_TV_SHOW_DEFAULT_URL);
            Log.v("############", "baseUri is " + baseUri.toString());
            uriBuilder = baseUri.buildUpon();
            Log.v("############", "uriBuilder is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_TOP_RATED_VALUE_DESCENDING);
        }
        return new TvShowPosterLoader(getActivity().getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<TvShow>> loader, ArrayList<TvShow> incomingTvShowArrayList) {
        switch (loader.getId()) {
            case POPULAR_TV_SHOW_LOADER_ID:
                Log.v("############******", "onLoadFinished called with id " + POPULAR_TV_SHOW_LOADER_ID);
                if (incomingTvShowArrayList.isEmpty()) {
                    Log.v("******************", "popularTvShows isEmpty");
                    return;
                } else {
                    popularTvShows = incomingTvShowArrayList;
                    mPopularTvShowAdapter = new PopularTvShowAdapter(getActivity(), popularTvShows);
                    mPopularTvShowAdapter.setTvShowData(popularTvShows);
                    mPopularTvShowRecyclerView.setAdapter(mPopularTvShowAdapter);

                }
                break;
            case AIRED_NOW_TV_SHOW_LOADER_ID:
                Log.v("############******", "onLoadFinished called with id " + AIRED_NOW_TV_SHOW_LOADER_ID);
                if (incomingTvShowArrayList.isEmpty()) {
                    Log.v("******************", "popularTvShows isEmpty");
                    return;
                } else {
                    airedNowTvShows = incomingTvShowArrayList;
                    mAiredNowTvShowAdapter = new AiredNowTvShowAdapter(getActivity(), airedNowTvShows);
                    mAiredNowTvShowAdapter.setTvShowData(airedNowTvShows);
                    mAiredNowTvShowRecyclerView.setAdapter(mAiredNowTvShowAdapter);
                }
                break;
            case TOP_RATED_TV_SHOW_LOADER_ID:
                Log.v("############******", "onLoadFinished called with id " + TOP_RATED_TV_SHOW_LOADER_ID);
                if (incomingTvShowArrayList.isEmpty()) {
                    Log.v("******************", "popularTvShows isEmpty");
                    return;
                } else {
                    topRatedTvShows = incomingTvShowArrayList;
                    mTopRatedTvShowAdapter = new TopRatedTvShowAdapter(getActivity(), topRatedTvShows);
                    mTopRatedTvShowAdapter.setTvShowData(topRatedTvShows);
                    mTopRatedTvShowRecyclerView.setAdapter(mTopRatedTvShowAdapter);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<TvShow>> loader) {
        Log.v("############******", "onLoaderReset called ");
    }
}