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

import me.abhishekraj.showmyshow.Adapter.MoviePosterAdapters.PopularMoviesAdapter;
import me.abhishekraj.showmyshow.Adapter.MoviePosterAdapters.TopRatedMoviesAdapter;
import me.abhishekraj.showmyshow.Adapter.MoviePosterAdapters.UpcomingMovieAdapter;
import me.abhishekraj.showmyshow.Model.Movie.Movie;
import me.abhishekraj.showmyshow.Network.MoviePosterLoader;
import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.Utils.UrlsAndConstants;

import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.DESCENDING;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.SORT_BY_KEY;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.SORT_BY_POPULARITY_VALUE_DESCENDING;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MoviePosterQuery.SORT_BY_TOP_RATED_VALUE_DESCENDING;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviePosterFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final int POPULAR_MOVIE_LOADER_ID = 1111;
    private static final int UPCOMING_MOVIE_LOADER_ID = 2222;
    private static final int TOP_RATED_MOVIE_LOADER_ID = 3333;
    ArrayList<Movie> popularMovies;
    ArrayList<Movie> upcomingMovies;
    ArrayList<Movie> topRatedMovies;
    PopularMoviesAdapter mPopularMoviesAdapter;
    UpcomingMovieAdapter mUpcomingMovieAdapter;
    TopRatedMoviesAdapter mTopRatedMoviesAdapter;
    RecyclerView mPopularMovieRecyclerView;
    RecyclerView mUpcomingMovieRecyclerView;
    RecyclerView mTopRatedMovieRecyclerView;
    Uri.Builder uriBuilder;
    String savedInstance;
    LinearLayoutManager layoutManagerPopularMoviesPoster;
    LinearLayoutManager layoutManagerUpcomingMoviesPoster;
    LinearLayoutManager layoutManagerTopRatedMoviesPoster;


    LinearLayout containerMoviePosterPopularMovies;
    LinearLayout containerMoviePosterUpcomingMovies;
    LinearLayout containerMoviePosterTopRatedMovies;
    ProgressBar loadingIndicatorMoviePoster;

    public MoviePosterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstance = "not empty";
            popularMovies = savedInstanceState.getParcelableArrayList("popularMovies");
            upcomingMovies = savedInstanceState.getParcelableArrayList("upcomingMovies");
            topRatedMovies = savedInstanceState.getParcelableArrayList("topRatedMovies");
        } else {
            popularMovies = new ArrayList<>();
            upcomingMovies = new ArrayList<>();
            topRatedMovies = new ArrayList<>();
            savedInstance = "empty";
            //First of all check if network is connected or not then only start the loader
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                /*
                 *fetch data. Get a reference to the LoaderManager, in order to interact with loaders.
                */
                startPopularMoviesLoaderManager();
                startUpcomingMoviesLoaderManager();
                startTopRatedMoviesLoaderManager();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        mTopRatedMovieRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTopRatedMovies);

        /*search recyclerview containers for the purpose of empty view */
        containerMoviePosterPopularMovies = (LinearLayout) rootView.findViewById(R.id.containerMoviePosterPopularMovies);
        containerMoviePosterTopRatedMovies = (LinearLayout) rootView.findViewById(R.id.containerMoviePosterTopRatedMovies);
        containerMoviePosterUpcomingMovies = (LinearLayout) rootView.findViewById(R.id.containerMoviePosterUpcomingMovies);
        loadingIndicatorMoviePoster = (ProgressBar) rootView.findViewById(R.id.loading_indicator_movie_poster);

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

                 /*
          * Setup layout manager for items with orientation
          * Also supports `LinearLayoutManager.HORIZONTAL`
          */
        layoutManagerTopRatedMoviesPoster = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerTopRatedMoviesPoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mTopRatedMovieRecyclerView.setLayoutManager(layoutManagerTopRatedMoviesPoster);

        SnapHelper snapHelperForPopularMovieRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForPopularMovieRecyclerView.attachToRecyclerView(mPopularMovieRecyclerView);

        SnapHelper snapHelperForUpcomingMovieRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForUpcomingMovieRecyclerView.attachToRecyclerView(mUpcomingMovieRecyclerView);

        SnapHelper snapHelperForTopRatedMovieRecyclerView = new GravitySnapHelper(Gravity.START);
        snapHelperForTopRatedMovieRecyclerView.attachToRecyclerView(mTopRatedMovieRecyclerView);

        /* Code referenced from the @link:
        * "https://guides.codepath.com/android/using-the-recyclerview"
        */

        // Create mPopularMovieAdapter passing in the sample user data
        mPopularMoviesAdapter = new PopularMoviesAdapter(getActivity(), popularMovies);
        mPopularMoviesAdapter.setMovieData(popularMovies);
        // Attach the mPopularMovieAdapter to the recyclerview to populate items
        mPopularMovieRecyclerView.setAdapter(mPopularMoviesAdapter);

        // Create mUpcomingMoviesAdapter passing in the sample user data
        mUpcomingMovieAdapter = new UpcomingMovieAdapter(getActivity(), upcomingMovies);
        mUpcomingMovieAdapter.setMovieData(upcomingMovies);
        // Attach the mUpcomingMoviesAdapter to the recyclerview to populate items
        mUpcomingMovieRecyclerView.setAdapter(mUpcomingMovieAdapter);

        // Create mTopRatedMovieAdapter passing in the sample user data
        mTopRatedMoviesAdapter = new TopRatedMoviesAdapter(getActivity(), topRatedMovies);
        mTopRatedMoviesAdapter.setMovieData(topRatedMovies);
        // Attach the mTopRatedMovieAdapter to the recyclerview to populate items
        mTopRatedMovieRecyclerView.setAdapter(mTopRatedMoviesAdapter);

        /*get loading indicator to work*/
        if (upcomingMovies.isEmpty() && topRatedMovies.isEmpty() && popularMovies.isEmpty()) {
            loadingIndicatorMoviePoster.setVisibility(View.VISIBLE);
            containerMoviePosterUpcomingMovies.setVisibility(View.GONE);
            containerMoviePosterTopRatedMovies.setVisibility(View.GONE);
            containerMoviePosterPopularMovies.setVisibility(View.GONE);
        } else if ((!(upcomingMovies.isEmpty()) || !(topRatedMovies.isEmpty()) || !(popularMovies.isEmpty()))) {
            loadingIndicatorMoviePoster.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("popularMovies", popularMovies);
        outState.putParcelableArrayList("upcomingMovies", upcomingMovies);
        outState.putParcelableArrayList("topRatedMovies", topRatedMovies);
        super.onSaveInstanceState(outState);
    }


    private void startPopularMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(POPULAR_MOVIE_LOADER_ID, null, this);
    }

    private void startUpcomingMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(UPCOMING_MOVIE_LOADER_ID, null, this);
    }

    private void startTopRatedMoviesLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(TOP_RATED_MOVIE_LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        if (id == POPULAR_MOVIE_LOADER_ID) {
            Uri baseUri = Uri.parse(UrlsAndConstants.MoviePosterQuery.DEFAULT_URL);
            uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_POPULARITY_VALUE_DESCENDING);

        } else if (id == UPCOMING_MOVIE_LOADER_ID) {
            Uri baseUri = Uri.parse(UrlsAndConstants.MoviePosterQuery.UPCOMING_MOVIE_BASE_URL);
            uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            uriBuilder.appendQueryParameter(SORT_BY_KEY, DESCENDING);
        } else if (id == TOP_RATED_MOVIE_LOADER_ID) {
            Uri baseUri = Uri.parse(UrlsAndConstants.MoviePosterQuery.DEFAULT_URL);
            uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
            uriBuilder.appendQueryParameter(SORT_BY_KEY, SORT_BY_TOP_RATED_VALUE_DESCENDING);
        }
        return new MoviePosterLoader(getActivity().getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> incomingMovieArrayList) {
        switch (loader.getId()) {
            case POPULAR_MOVIE_LOADER_ID:
                if (incomingMovieArrayList.isEmpty()) {
                    return;
                } else {
                    popularMovies = incomingMovieArrayList;
                    mPopularMoviesAdapter = new PopularMoviesAdapter(getActivity(), popularMovies);
                    mPopularMoviesAdapter.setMovieData(popularMovies);
                    mPopularMovieRecyclerView.setAdapter(mPopularMoviesAdapter);

                      /*get loading indicator to work*/
                    containerMoviePosterPopularMovies.setVisibility(View.VISIBLE);
                    loadingIndicatorMoviePoster.setVisibility(View.GONE);
                }
                break;
            case UPCOMING_MOVIE_LOADER_ID:
                if (incomingMovieArrayList.isEmpty()) {
                    return;
                } else {
                    upcomingMovies = incomingMovieArrayList;
                    mUpcomingMovieAdapter = new UpcomingMovieAdapter(getActivity(), upcomingMovies);
                    mUpcomingMovieAdapter.setMovieData(upcomingMovies);
                    mUpcomingMovieRecyclerView.setAdapter(mUpcomingMovieAdapter);
                    /*get loading indicator to work*/
                    containerMoviePosterUpcomingMovies.setVisibility(View.VISIBLE);
                    loadingIndicatorMoviePoster.setVisibility(View.GONE);
                }
                break;
            case TOP_RATED_MOVIE_LOADER_ID:
                if (incomingMovieArrayList.isEmpty()) {
                    return;
                } else {
                    topRatedMovies = incomingMovieArrayList;
                    mTopRatedMoviesAdapter = new TopRatedMoviesAdapter(getActivity(), topRatedMovies);
                    mTopRatedMoviesAdapter.setMovieData(topRatedMovies);
                    mTopRatedMovieRecyclerView.setAdapter(mTopRatedMoviesAdapter);
                    /*get loading indicator to work*/
                    containerMoviePosterTopRatedMovies.setVisibility(View.VISIBLE);
                    loadingIndicatorMoviePoster.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    }
}