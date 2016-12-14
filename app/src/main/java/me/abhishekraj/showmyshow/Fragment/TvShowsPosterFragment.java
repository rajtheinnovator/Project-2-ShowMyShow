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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.TvPosterQuery.AIR_DATE_GREATER_THAN;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.TvPosterQuery.AIR_DATE_GREATER_THAN_VALUE_NOVEMBER_START;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.TvPosterQuery.PAGE_OF_RESULT_TO_QUERY;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.TvPosterQuery.SORT_BY_FIRST_AIR_DATES_DESCENDING;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.TvPosterQuery.VOTE_AVERAGE_GREATER_THAN;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.TvPosterQuery.WITH_RUNTIME_GREATER_THAN;

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

    LinearLayout containerTvShowPosterPopularTvShow;
    LinearLayout containerTvShowPosterTopRatedTvShow;
    LinearLayout containerTvShowPosterAiredNowTvShow;
    ProgressBar loadingIndicatorTvShowPoster;

    public TvShowsPosterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstance = "not empty";
            popularTvShows = savedInstanceState.getParcelableArrayList("popularTvShows");
            airedNowTvShows = savedInstanceState.getParcelableArrayList("airedNowTvShows");
            topRatedTvShows = savedInstanceState.getParcelableArrayList("topRatedTvShows");

        } else {
            popularTvShows = new ArrayList<>();
            airedNowTvShows = new ArrayList<>();
            topRatedTvShows = new ArrayList<>();
            savedInstance = "empty";
            //First of all check if network is connected or not then only start the loader
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                /*
                 *fetch data. Get a reference to the LoaderManager, in order to interact with loaders.
                */
                startPopularTvShowsLoaderManager();
                startAiredNowTvShowsLoaderManager();
                startTopRatedTvShowsLoaderManager();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        /*search recyclerview containers for the purpose of empty view */
        containerTvShowPosterPopularTvShow = (LinearLayout) rootView.findViewById(R.id.containerTvShowPosterPopularTvShow);
        containerTvShowPosterAiredNowTvShow = (LinearLayout) rootView.findViewById(R.id.containerTvShowPosterAiredNowTvShow);
        containerTvShowPosterTopRatedTvShow = (LinearLayout) rootView.findViewById(R.id.containerTvShowPosterTopRatedTvShow);
        loadingIndicatorTvShowPoster = (ProgressBar) rootView.findViewById(R.id.loading_indicator_tv_show_poster);

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

           /*get loading indicator to work*/
        if (popularTvShows.isEmpty() && airedNowTvShows.isEmpty() && topRatedTvShows.isEmpty()) {
            loadingIndicatorTvShowPoster.setVisibility(View.VISIBLE);
            containerTvShowPosterTopRatedTvShow.setVisibility(View.GONE);
            containerTvShowPosterAiredNowTvShow.setVisibility(View.GONE);
            containerTvShowPosterPopularTvShow.setVisibility(View.GONE);
        } else if ((!(popularTvShows.isEmpty()) || !(airedNowTvShows.isEmpty()) || !(topRatedTvShows.isEmpty()))) {
            loadingIndicatorTvShowPoster.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("popularTvShows", popularTvShows);
        outState.putParcelableArrayList("airedNowTvShows", airedNowTvShows);
        outState.putParcelableArrayList("topRatedTvShows", topRatedTvShows);
        super.onSaveInstanceState(outState);
    }


    private void startPopularTvShowsLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(POPULAR_TV_SHOW_LOADER_ID, null, this);
    }

    private void startAiredNowTvShowsLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(AIRED_NOW_TV_SHOW_LOADER_ID, null, this);
    }

    private void startTopRatedTvShowsLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(TOP_RATED_TV_SHOW_LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<TvShow>> onCreateLoader(int id, Bundle args) {
        if (id == POPULAR_TV_SHOW_LOADER_ID) {
            Uri baseUri = Uri.parse(UrlsAndConstants.TvPosterQuery.DISCOVER_TV_SHOW_DEFAULT_URL);
            uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            uriBuilder.appendQueryParameter(PAGE_OF_RESULT_TO_QUERY, "1");
            uriBuilder.appendQueryParameter(WITH_RUNTIME_GREATER_THAN, "20");
            uriBuilder.appendQueryParameter(VOTE_AVERAGE_GREATER_THAN, "2");
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_POPULARITY_VALUE_DESCENDING);

        } else if (id == AIRED_NOW_TV_SHOW_LOADER_ID) {
            Uri baseUri = Uri.parse(UrlsAndConstants.TvPosterQuery.DISCOVER_TV_SHOW_DEFAULT_URL);
            uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            uriBuilder.appendQueryParameter(PAGE_OF_RESULT_TO_QUERY, "1");
            uriBuilder.appendQueryParameter(AIR_DATE_GREATER_THAN, AIR_DATE_GREATER_THAN_VALUE_NOVEMBER_START);
            uriBuilder.appendQueryParameter(WITH_RUNTIME_GREATER_THAN, "20");
            uriBuilder.appendQueryParameter(VOTE_AVERAGE_GREATER_THAN, "2");
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_FIRST_AIR_DATES_DESCENDING);
        } else if (id == TOP_RATED_TV_SHOW_LOADER_ID) {
            Uri baseUri = Uri.parse(UrlsAndConstants.TvPosterQuery.DISCOVER_TV_SHOW_DEFAULT_URL);
            uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            uriBuilder.appendQueryParameter(PAGE_OF_RESULT_TO_QUERY, "1");
            uriBuilder.appendQueryParameter(WITH_RUNTIME_GREATER_THAN, "20");
            uriBuilder.appendQueryParameter(VOTE_AVERAGE_GREATER_THAN, "2");
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_TOP_RATED_VALUE_DESCENDING);
        }
        return new TvShowPosterLoader(getActivity().getApplicationContext(), uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<TvShow>> loader, ArrayList<TvShow> incomingTvShowArrayList) {
        switch (loader.getId()) {
            case POPULAR_TV_SHOW_LOADER_ID:
                if (incomingTvShowArrayList.isEmpty()) {
                    return;
                } else {
                    popularTvShows = incomingTvShowArrayList;
                    mPopularTvShowAdapter = new PopularTvShowAdapter(getActivity(), popularTvShows);
                    mPopularTvShowAdapter.setTvShowData(popularTvShows);
                    mPopularTvShowRecyclerView.setAdapter(mPopularTvShowAdapter);

                      /*get loading indicator to work*/
                    containerTvShowPosterPopularTvShow.setVisibility(View.VISIBLE);
                    loadingIndicatorTvShowPoster.setVisibility(View.GONE);

                }
                break;
            case AIRED_NOW_TV_SHOW_LOADER_ID:
                if (incomingTvShowArrayList.isEmpty()) {
                    return;
                } else {
                    airedNowTvShows = incomingTvShowArrayList;
                    mAiredNowTvShowAdapter = new AiredNowTvShowAdapter(getActivity(), airedNowTvShows);
                    mAiredNowTvShowAdapter.setTvShowData(airedNowTvShows);
                    mAiredNowTvShowRecyclerView.setAdapter(mAiredNowTvShowAdapter);
                    /*get loading indicator to work*/
                    containerTvShowPosterAiredNowTvShow.setVisibility(View.VISIBLE);
                    loadingIndicatorTvShowPoster.setVisibility(View.GONE);
                }
                break;
            case TOP_RATED_TV_SHOW_LOADER_ID:
                if (incomingTvShowArrayList.isEmpty()) {
                    return;
                } else {
                    topRatedTvShows = incomingTvShowArrayList;
                    mTopRatedTvShowAdapter = new TopRatedTvShowAdapter(getActivity(), topRatedTvShows);
                    mTopRatedTvShowAdapter.setTvShowData(topRatedTvShows);
                    mTopRatedTvShowRecyclerView.setAdapter(mTopRatedTvShowAdapter);
                    /*get loading indicator to work*/
                    containerTvShowPosterTopRatedTvShow.setVisibility(View.VISIBLE);
                    loadingIndicatorTvShowPoster.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<TvShow>> loader) {
    }
}