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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
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

import me.abhishekraj.showmyshow.Adapter.TvShowDetailsAdapters.TvShowCreditsCastAdapter;
import me.abhishekraj.showmyshow.Adapter.TvShowDetailsAdapters.TvShowReviewAdapter;
import me.abhishekraj.showmyshow.Adapter.TvShowDetailsAdapters.TvShowTrailerAdapter;
import me.abhishekraj.showmyshow.Model.TvShow.Credits;
import me.abhishekraj.showmyshow.Model.TvShow.Review;
import me.abhishekraj.showmyshow.Model.TvShow.TvShow;
import me.abhishekraj.showmyshow.Model.TvShow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.Model.TvShow.Video;
import me.abhishekraj.showmyshow.Network.DetailsTvShowLoader;
import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.Utils.UrlsAndConstants;

import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MovieDetailQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MovieDetailQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MovieDetailQuery.APPEND_TO_RESPONSE;
import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MovieDetailQuery.VIDEOS_AND_REVIEWS_AND_CREDITS;


/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<TvShowDetailsBundle> {

    private static final int TV_SHOW_DETAIL_LOADER_ID = 3;
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;
    public ArrayList<Credits> mCredits;
    TvShowReviewAdapter mTvShowReviewAdapter;
    TvShowCreditsCastAdapter mTvShowCreditsCastAdapter;
    TvShowTrailerAdapter mTvShowTrailerAdapter;
    RecyclerView mTvShowReviewRecyclerView;
    RecyclerView mTvShowTrailerRecyclerView;
    RecyclerView mTvShowCastRecyclerView;
    TvShow tvShow;
    TextView tvShowDetailTitleTextView;
    ImageView tvShowDetailTitleImageView;
    ImageView tvShowDetailsBackdropImageView;
    CollapsingToolbarLayout collapsingToolbar;
    String posterURL;
    String backdropURL;
    TextView tvShowReleaseDate;
    TextView tvShowRunTimeDuration;
    private TvShowDetailsBundle mTvShowDetailsBundle;
    private int mTvShowDuration;
    private String mTvShowDurationString;

    public TvShowDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle bundle = getArguments();
        tvShowDetailTitleTextView = (TextView) rootView.findViewById(R.id.movie_detail_title_text_view);
        tvShowDetailTitleImageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view);
        tvShowDetailsBackdropImageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view_backdrop);
        tvShowReleaseDate = (TextView) rootView.findViewById(R.id.movie_release_date_text_view);
        tvShowRunTimeDuration = (TextView) rootView.findViewById(R.id.movie_duration_text_view);

        /*Creating a collapsing toolbar, defined in the fragment_movie_details.xml  */
        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        if (savedInstanceState == null) {
            mReview = new ArrayList<>();
            mVideo = new ArrayList<>();
            mCredits = new ArrayList<>();
            mTvShowDetailsBundle = new TvShowDetailsBundle();
        }

        if ((bundle != null)) {
            tvShow = getArguments().getParcelable("tvShow");
            tvShowDetailTitleTextView.setText(tvShow.getTvShowName());
            tvShowReleaseDate.setText(tvShow.getTvShowFirstAirDate());
            posterURL = UrlsAndConstants.MoviePosterQuery.BASE_IMAGE_URL + tvShow.getTvShowPosterPath();
            backdropURL = UrlsAndConstants.MoviePosterQuery.BASE_IMAGE_URL + tvShow.getTvShowBackdropPath();
            collapsingToolbar.setTitle(tvShow.getTvShowName());
            Picasso.with(getContext())
                    .load(posterURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(tvShowDetailTitleImageView);
            Picasso.with(getContext())
                    .load(backdropURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(tvShowDetailsBackdropImageView);

            /*setting the ratingbar from @link: https://github.com/FlyingPumba/SimpleRatingBar*/
            SimpleRatingBar simpleRatingBar = (SimpleRatingBar) rootView.findViewById(R.id.movieRatingInsideMovieDetailsFragment);
            simpleRatingBar.setRating((float) (tvShow.getTvShowVoteAverage()) / 2);

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
            mTvShowReviewRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovieReviews);
            mTvShowTrailerRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovieTrailers);
            mTvShowCastRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovieCast);

            /* Create mPopularTvShowAdapter passing in the sample user data */
            mTvShowReviewAdapter = new TvShowReviewAdapter(getActivity(), mTvShowDetailsBundle);
             /* Create mPopularTvShowAdapter passing in the sample user data */
            mTvShowTrailerAdapter = new TvShowTrailerAdapter(getActivity(), mTvShowDetailsBundle);
                 /* Create mPopularTvShowAdapter passing in the sample user data */
            mTvShowCreditsCastAdapter = new TvShowCreditsCastAdapter(getActivity(), mTvShowDetailsBundle);

            /* Attach the mPopularTvShowAdapter to the reviewRecyclerView to populate items */
            mTvShowReviewRecyclerView.setAdapter(mTvShowReviewAdapter);
            /* Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items */
            mTvShowTrailerRecyclerView.setAdapter(mTvShowTrailerAdapter);
            /* Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items */
            mTvShowCastRecyclerView.setAdapter(mTvShowCreditsCastAdapter);

            /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
            LinearLayoutManager layoutManagerTvShowReview = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            /* Optionally customize the position you want to default scroll to */
            layoutManagerTvShowReview.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
            mTvShowReviewRecyclerView.setLayoutManager(layoutManagerTvShowReview);

            /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
            LinearLayoutManager layoutManagerTvShowtrailer = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
            layoutManagerTvShowtrailer.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
            mTvShowTrailerRecyclerView.setLayoutManager(layoutManagerTvShowtrailer);

               /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
            LinearLayoutManager layoutManagerTvShowCast = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
            layoutManagerTvShowtrailer.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
            mTvShowCastRecyclerView.setLayoutManager(layoutManagerTvShowCast);

//            /*
//            * Snap code for trailer review taken from @link: "https://guides.codepath.com/android/using-the-recyclerview"
//            */
//            SnapHelper snapHelper = new LinearSnapHelper();
//            snapHelper.attachToRecyclerView(mTvShowTrailerRecyclerView);

            /*Snap code for trailer review taken from
            * @link: "https://github.com/rubensousa/RecyclerViewSnap/"
            */

            SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
            snapHelperStart.attachToRecyclerView(mTvShowTrailerRecyclerView);

            SnapHelper snapHelperCastStart = new GravitySnapHelper(Gravity.START);
            snapHelperCastStart.attachToRecyclerView(mTvShowCastRecyclerView);
        }
        return rootView;
    }

    private void startLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        Log.v("############", "startLoaderManager started");
        loaderManager.initLoader(TV_SHOW_DETAIL_LOADER_ID, null, this);
        Log.v("############", "startLoaderManager finished");
    }

    @Override
    public Loader<TvShowDetailsBundle> onCreateLoader(int id, Bundle args) {
        Log.v("############", "onCreateLoader called");
        Uri baseUri = Uri.parse((UrlsAndConstants.TvShowDetailQuery.DEFAULT_URL) + tvShow.getTvShowId());
        Log.v("############", "baseUri is " + baseUri.toString());
        Uri.Builder uriBuilder = baseUri.buildUpon();
        Log.v("############", "uriBuilder is " + uriBuilder.toString());
        uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
        uriBuilder.appendQueryParameter(APPEND_TO_RESPONSE, VIDEOS_AND_REVIEWS_AND_CREDITS);
        Log.v("############", "uriBuilder.toString() is " + uriBuilder.toString());
        return new DetailsTvShowLoader(getActivity().getApplicationContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<TvShowDetailsBundle> loader, TvShowDetailsBundle tvShowDetailsBundle) {
        if (tvShowDetailsBundle != null) {
            mTvShowDetailsBundle = tvShowDetailsBundle;
            // Attach the mPopularTvShowAdapter to the reviewRecyclerView to populate items
            mTvShowReviewAdapter.setTvShowDetailsBundleData(mTvShowDetailsBundle);
            // Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items
            mTvShowTrailerAdapter.setTvShowDetailsBundleData(mTvShowDetailsBundle);
            Log.v("############", " mPopularTvShowAdapter.setTvShowDetailsBundleData(tvShow) finished");
            // Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items
            mTvShowCreditsCastAdapter.setTvShowDetailsBundleData(mTvShowDetailsBundle);
            Log.v("############", " mPopularTvShowAdapter.setTvShowDetailsBundleData(tvShow) finished");

            mTvShowReviewRecyclerView.setAdapter(mTvShowReviewAdapter);
            Log.v("############", " mTvShowReviewRecyclerView.setAdapter(mPopularTvShowAdapter); finished");
            mTvShowTrailerRecyclerView.setAdapter(mTvShowTrailerAdapter);
            Log.v("############", " mTvShowReviewRecyclerView.setAdapter(mPopularTvShowAdapter); finished");
            mTvShowCastRecyclerView.setAdapter(mTvShowCreditsCastAdapter);
            Log.v("############", " mTvShowReviewRecyclerView.setAdapter(mPopularTvShowAdapter); finished");
            //updateDurationTextView(mTvShowDetailsBundle);

        }
    }

    public void updateDurationTextView(TvShowDetailsBundle tvShowDetailsBundle) {

        mTvShowDuration = tvShowDetailsBundle.getTvShow().getTvShowId();
        if (mTvShowDuration < 60) {
            mTvShowDurationString = String.valueOf(mTvShowDuration) + "mins";
        } else if (60 < mTvShowDuration && mTvShowDuration < 120) {
            mTvShowDurationString = "1 Hrs " + String.valueOf(mTvShowDuration - 60) + "mins";
        } else if (120 < mTvShowDuration && mTvShowDuration < 180) {
            mTvShowDurationString = "2 Hrs " + String.valueOf(mTvShowDuration - 120) + "mins";
        } else {
            mTvShowDurationString = "3 Hrs " + String.valueOf(mTvShowDuration - 180) + "mins";
        }
        tvShowRunTimeDuration.setText(mTvShowDurationString);
    }

    @Override
    public void onLoaderReset(Loader<TvShowDetailsBundle> loader) {
    }
}

