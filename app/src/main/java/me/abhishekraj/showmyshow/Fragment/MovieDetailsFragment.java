package me.abhishekraj.showmyshow.Fragment;

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
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.Adapter.MovieDetailsAdapters.MovieCreditsCastAdapter;
import me.abhishekraj.showmyshow.Adapter.MovieDetailsAdapters.MovieReviewAdapter;
import me.abhishekraj.showmyshow.Adapter.MovieDetailsAdapters.MovieTrailerAdapter;
import me.abhishekraj.showmyshow.Model.Credits;
import me.abhishekraj.showmyshow.Model.Movie;
import me.abhishekraj.showmyshow.Model.MovieDetailsBundle;
import me.abhishekraj.showmyshow.Model.Review;
import me.abhishekraj.showmyshow.Model.Video;
import me.abhishekraj.showmyshow.Network.DetailsMovieLoader;
import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.Utils.UrlsAndConstants;

import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.DetailQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.DetailQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.DetailQuery.APPEND_TO_RESPONSE;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.DetailQuery.VIDEOS_AND_REVIEWS_AND_CREDITS;

/**
 * Created by ABHISHEK RAJ on 11/26/2016.
 */

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<MovieDetailsBundle> {

    private static final int MOVIE_DETAIL_LOADER_ID = 2;
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;
    public ArrayList<Credits> mCredits;
    MovieReviewAdapter mMovieReviewAdapter;
    MovieCreditsCastAdapter mMovieCreditsCastAdapter;
    MovieTrailerAdapter mMovieTrailerAdapter;
    RecyclerView mMovieReviewRecyclerView;
    RecyclerView mMovieTrailerRecyclerView;
    RecyclerView mMovieCastRecyclerView;
    Movie movie;
    TextView movieDetailTitleTextView;
    ImageView movieDetailTitleImageView;
    ImageView moviedetailsBackdropImageView;
    CollapsingToolbarLayout collapsingToolbar;
    String posterURL;
    String backdropURL;
    TextView movieReleaseDate;
    TextView movieRunTimeDuration;
    private MovieDetailsBundle mMovieDetailsBundle;
    private int mMovieDuration;
    private String mMovieDurationString;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle bundle = getArguments();
        movieDetailTitleTextView = (TextView) rootView.findViewById(R.id.movie_detail_title_text_view);
        movieDetailTitleImageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view);
        moviedetailsBackdropImageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view_backdrop);
        movieReleaseDate = (TextView) rootView.findViewById(R.id.movie_release_date_text_view);
        movieRunTimeDuration = (TextView) rootView.findViewById(R.id.movie_duration_text_view);

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

        if (savedInstanceState == null) {
            mReview = new ArrayList<>();
            mVideo = new ArrayList<>();
            mMovieDetailsBundle = new MovieDetailsBundle();
        }

        if ((bundle != null)) {
            movie = getArguments().getParcelable("movie");
            movieDetailTitleTextView.setText(movie.getMovieTitle());
            movieReleaseDate.setText(movie.getMovieReleaseDate());
            posterURL = UrlsAndConstants.MoviePosterQuery.BASE_IMAGE_URL + movie.getMoviePosterPath();
            backdropURL = UrlsAndConstants.MoviePosterQuery.BASE_IMAGE_URL + movie.getMovieBackdropPath();
            collapsingToolbar.setTitle(movie.getMovieTitle());
            Picasso.with(getContext())
                    .load(posterURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(movieDetailTitleImageView);
            Picasso.with(getContext())
                    .load(backdropURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(moviedetailsBackdropImageView);

            /*setting the ratingbar from @link: https://github.com/FlyingPumba/SimpleRatingBar*/
            SimpleRatingBar simpleRatingBar = (SimpleRatingBar) rootView.findViewById(R.id.movieRatingInsideMovieDetailsFragment);
            simpleRatingBar.setRating((float) (movie.getMovieVoteAverage()) / 2);

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
            mMovieCastRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovieCast);

            /* Create mPopularMoviesAdapter passing in the sample user data */
            mMovieReviewAdapter = new MovieReviewAdapter(getActivity(), mMovieDetailsBundle);
             /* Create mPopularMoviesAdapter passing in the sample user data */
            mMovieTrailerAdapter = new MovieTrailerAdapter(getActivity(), mMovieDetailsBundle);
                 /* Create mPopularMoviesAdapter passing in the sample user data */
            mMovieCreditsCastAdapter = new MovieCreditsCastAdapter(getActivity(), mMovieDetailsBundle);

            /* Attach the mPopularMoviesAdapter to the reviewRecyclerView to populate items */
            mMovieReviewRecyclerView.setAdapter(mMovieReviewAdapter);
            /* Attach the mPopularMoviesAdapter to the trailerRecyclerView to populate items */
            mMovieTrailerRecyclerView.setAdapter(mMovieTrailerAdapter);
            /* Attach the mPopularMoviesAdapter to the trailerRecyclerView to populate items */
            mMovieCastRecyclerView.setAdapter(mMovieCreditsCastAdapter);

            /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
            LinearLayoutManager layoutManagerMovieReview = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
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

               /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
            LinearLayoutManager layoutManagerMovieCast = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
            layoutManagerMovietrailer.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
            mMovieCastRecyclerView.setLayoutManager(layoutManagerMovieCast);

//            /*
//            * Snap code for trailer review taken from @link: "https://guides.codepath.com/android/using-the-recyclerview"
//            */
//            SnapHelper snapHelper = new LinearSnapHelper();
//            snapHelper.attachToRecyclerView(mMovieTrailerRecyclerView);

            /*Snap code for trailer review taken from
            * @link: "https://github.com/rubensousa/RecyclerViewSnap/"
            */

            SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
            snapHelperStart.attachToRecyclerView(mMovieTrailerRecyclerView);

            SnapHelper snapHelperCastStart = new GravitySnapHelper(Gravity.START);
            snapHelperCastStart.attachToRecyclerView(mMovieCastRecyclerView);
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
        uriBuilder.appendQueryParameter(APPEND_TO_RESPONSE, VIDEOS_AND_REVIEWS_AND_CREDITS);
        Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
        return new DetailsMovieLoader(getActivity().getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<MovieDetailsBundle> loader, MovieDetailsBundle movieDetailsBundle) {
        if (movieDetailsBundle != null) {
            mMovieDetailsBundle = movieDetailsBundle;
            // Attach the mPopularMoviesAdapter to the reviewRecyclerView to populate items
            mMovieReviewAdapter.setMovieDetailsBundleData(mMovieDetailsBundle);
            // Attach the mPopularMoviesAdapter to the trailerRecyclerView to populate items
            mMovieTrailerAdapter.setMovieDetailsBundleData(mMovieDetailsBundle);
            Log.v("############", " mPopularMoviesAdapter.setMovieDetailsBundleData(movie) finished");
            // Attach the mPopularMoviesAdapter to the trailerRecyclerView to populate items
            mMovieCreditsCastAdapter.setMovieDetailsBundleData(mMovieDetailsBundle);
            Log.v("############", " mPopularMoviesAdapter.setMovieDetailsBundleData(movie) finished");

            mMovieReviewRecyclerView.setAdapter(mMovieReviewAdapter);
            Log.v("############", " mMovieReviewRecyclerView.setAdapter(mPopularMoviesAdapter); finished");
            mMovieTrailerRecyclerView.setAdapter(mMovieTrailerAdapter);
            Log.v("############", " mMovieReviewRecyclerView.setAdapter(mPopularMoviesAdapter); finished");
            mMovieCastRecyclerView.setAdapter(mMovieCreditsCastAdapter);
            Log.v("############", " mMovieReviewRecyclerView.setAdapter(mPopularMoviesAdapter); finished");
            updateDurationTextView(mMovieDetailsBundle);

        }
    }

    public void updateDurationTextView(MovieDetailsBundle movieDetailsBundle) {

        mMovieDuration = movieDetailsBundle.getMovie().getMovieRuntimeDuration();
        if (mMovieDuration < 60) {
            mMovieDurationString = String.valueOf(mMovieDuration) + "mins";
        } else if (60 < mMovieDuration && mMovieDuration < 120) {
            mMovieDurationString = "1 Hrs " + String.valueOf(mMovieDuration - 60) + "mins";
        } else if (120 < mMovieDuration && mMovieDuration < 180) {
            mMovieDurationString = "2 Hrs " + String.valueOf(mMovieDuration - 120) + "mins";
        } else {
            mMovieDurationString = "3 Hrs " + String.valueOf(mMovieDuration - 180) + "mins";
        }
        movieRunTimeDuration.setText(mMovieDurationString);
    }

    @Override
    public void onLoaderReset(Loader<MovieDetailsBundle> loader) {
    }
}
