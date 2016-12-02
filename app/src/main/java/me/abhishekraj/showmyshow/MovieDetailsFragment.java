package me.abhishekraj.showmyshow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static me.abhishekraj.showmyshow.UrlsAndConstants.DetailQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DetailQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DetailQuery.APPEND_TO_RESPONSE;
import static me.abhishekraj.showmyshow.UrlsAndConstants.DetailQuery.VIDEOS_AND_REVIEWS;

/**
 * Created by ABHISHEK RAJ on 11/26/2016.
 */

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<MovieDetailsBundle> {

    private static final int MOVIE_DETAIL_LOADER_ID = 2;

    MovieReviewAdapter mMovieReviewAdapter;
    MovieTrailerAdapter mMovieTrailerAdapter;

    RecyclerView mMovieReviewRecyclerView;
    RecyclerView mMovieTrailerRecyclerView;

    Movie movie;
    private MovieDetailsBundle mMovieDetailsBundle;

    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    TextView movieDetailTitleTextView;
    ImageView movieDetailTitleImageView;
    ImageView moviedetailsBackdropImageView;
    CollapsingToolbarLayout collapsingToolbar;
    String posterURL;
    String backdropURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle bundle = getArguments();
        movieDetailTitleTextView = (TextView) rootView.findViewById(R.id.movie_detail_title_text_view);
        movieDetailTitleImageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view);
        moviedetailsBackdropImageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view_backdrop);

        /* As there is no actionbar defined in the Style for this activity, so creating one toolbar for this Fragment
        *  which will act as an actionbar after scrolling-up, referenced from StackOverflow link
        *  @link http://stackoverflow.com/a/32858049/5770629
        */
        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Creating a collapsing toolbar, defined in the fragment_movie_details.xml  */
        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        if (savedInstanceState == null){
            mReview = new ArrayList<>();
            mVideo = new ArrayList<>();
            mMovieDetailsBundle = new MovieDetailsBundle();
        }

        if ((bundle != null)) {
            movie = getArguments().getParcelable("movie");
            movieDetailTitleTextView.setText(movie.getMovieTitle());
            posterURL = UrlsAndConstants.DefaultQuery.BASE_IMAGE_URL + movie.getMoviePosterPath();
            backdropURL = UrlsAndConstants.DefaultQuery.BASE_IMAGE_URL + movie.getMovieBackdropPath();
            collapsingToolbar.setTitle(movie.getMovieTitle());
            Picasso.with(getContext())
                    .load(posterURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(movieDetailTitleImageView);
            Picasso.with(getContext())
                    .load(backdropURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(moviedetailsBackdropImageView);

             /* First of all check if network is connected or not then only start the loader */
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

             /* fetch data. Get a reference to the LoaderManager, in order to interact with loaders. */
                startLoaderManager();
                Log.v("############", "startLoaderManager called");
            }
            /*
            RecyclerView Codes are referenced from the @link: "https://guides.codepath.com/android/using-the-recyclerview"
            Lookup the recyclerview in activity layout
            */
            mMovieReviewRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovieReviews);
            mMovieTrailerRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovieTrailers);

            /* Create mAdapter passing in the sample user data */
            mMovieReviewAdapter = new MovieReviewAdapter(getActivity(), mMovieDetailsBundle);
             /* Create mAdapter passing in the sample user data */
            mMovieTrailerAdapter = new MovieTrailerAdapter(getActivity(), mMovieDetailsBundle);

            /* Attach the mAdapter to the reviewRecyclerView to populate items */
            mMovieReviewRecyclerView.setAdapter(mMovieReviewAdapter);
            /* Attach the mAdapter to the trailerRecyclerView to populate items */
            mMovieTrailerRecyclerView.setAdapter(mMovieTrailerAdapter);

            /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
            LinearLayoutManager layoutManagerMovieReview = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
            layoutManagerMovieReview.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
            mMovieReviewRecyclerView.setLayoutManager(layoutManagerMovieReview);

            /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
            LinearLayoutManager layoutManagerMovietrailer = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
            layoutManagerMovietrailer.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
            mMovieTrailerRecyclerView.setLayoutManager(layoutManagerMovietrailer);
        }
        return rootView;
    }

    private void startLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############", "startLoaderManager started");
        loaderManager.initLoader(MOVIE_DETAIL_LOADER_ID, null, this);
        Log.v("############", "startLoaderManager finished");
    }

    @Override
    public Loader<MovieDetailsBundle> onCreateLoader(int id, Bundle args) {
        Log.v("############", "onCreateLoader called");
        Uri baseUri = Uri.parse((UrlsAndConstants.DetailQuery.DEFAULT_URL) + movie.getMovieId());
        Log.v("############", "baseUri is " + baseUri.toString());
        Uri.Builder uriBuilder = baseUri.buildUpon();
        Log.v("############", "uriBuilder is " + uriBuilder.toString());
        uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
        uriBuilder.appendQueryParameter(APPEND_TO_RESPONSE, VIDEOS_AND_REVIEWS);
        Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
        return new DetailsMovieLoader(getActivity().getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<MovieDetailsBundle> loader, MovieDetailsBundle movieDetailsBundle) {
        if (movieDetailsBundle != null) {
            mMovieDetailsBundle = movieDetailsBundle;
            // Attach the mAdapter to the reviewRecyclerView to populate items
            mMovieReviewAdapter.setMovieDetailsBundleData(mMovieDetailsBundle);
            // Attach the mAdapter to the trailerRecyclerView to populate items
            mMovieTrailerAdapter.setMovieDetailsBundleData(mMovieDetailsBundle);
            Log.v("############", " mAdapter.setMovieDetailsBundleData(movie) finished");
            mMovieReviewRecyclerView.setAdapter(mMovieReviewAdapter);
            Log.v("############", " mMovieReviewRecyclerView.setAdapter(mAdapter); finished");
            mMovieTrailerRecyclerView.setAdapter(mMovieTrailerAdapter);
            Log.v("############", " mMovieReviewRecyclerView.setAdapter(mAdapter); finished");

        }
    }
    @Override
    public void onLoaderReset(Loader<MovieDetailsBundle> loader) {

    }
}
