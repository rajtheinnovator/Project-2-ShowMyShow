package me.abhishekraj.showmyshow.fragment;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.adapter.tvshowdetailsadapters.TvShowCreditsCastAdapter;
import me.abhishekraj.showmyshow.adapter.tvshowdetailsadapters.TvShowReviewAdapter;
import me.abhishekraj.showmyshow.adapter.tvshowdetailsadapters.TvShowSeasonsAdapter;
import me.abhishekraj.showmyshow.adapter.tvshowdetailsadapters.TvShowTrailerAdapter;
import me.abhishekraj.showmyshow.model.tvshow.Credits;
import me.abhishekraj.showmyshow.model.tvshow.Review;
import me.abhishekraj.showmyshow.model.tvshow.Seasons;
import me.abhishekraj.showmyshow.model.tvshow.TvShow;
import me.abhishekraj.showmyshow.model.tvshow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.model.tvshow.Video;
import me.abhishekraj.showmyshow.network.DetailsTvShowLoader;
import me.abhishekraj.showmyshow.utils.UrlsAndConstants;

import static me.abhishekraj.showmyshow.utils.UrlsAndConstants.MovieDetailQuery.API_KEY_PARAM;
import static me.abhishekraj.showmyshow.utils.UrlsAndConstants.MovieDetailQuery.API_KEY_PARAM_VALUE;
import static me.abhishekraj.showmyshow.utils.UrlsAndConstants.MovieDetailQuery.APPEND_TO_RESPONSE;
import static me.abhishekraj.showmyshow.utils.UrlsAndConstants.MovieDetailQuery.VIDEOS_AND_REVIEWS_AND_CREDITS;


/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<TvShowDetailsBundle> {

    private static final int TV_SHOW_DETAIL_LOADER_ID = 3;

    /* Arrays for holding tvShow details */
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;
    public ArrayList<Credits> mCredits;
    public ArrayList<Seasons> mSeasons;

    /* Adapters to inflate the different TvShowDetails recyclerviews */
    TvShowReviewAdapter mTvShowReviewAdapter;
    TvShowCreditsCastAdapter mTvShowCreditsCastAdapter;
    TvShowTrailerAdapter mTvShowTrailerAdapter;
    TvShowSeasonsAdapter mTvShowSeasonsAdapter;

    /* recyclerviews for holding and displaying the tvshow details */
    RecyclerView mTvShowReviewRecyclerView;
    RecyclerView mTvShowTrailerRecyclerView;
    RecyclerView mTvShowCastRecyclerView;
    RecyclerView mTvShowSeasonsRecyclerView;

    /* Reference to different layout views and view groups */
    TvShow tvShow;
    TextView tvShowDetailTitleTextView;
    ImageView tvShowDetailTitleImageView;
    ImageView tvShowDetailsBackdropImageView;
    CollapsingToolbarLayout collapsingToolbar;
    String posterURL;
    String backdropURL;
    TextView tvShowLastAirDateTextView;
    TextView tvShowRunTimeDurationTextView;
    TextView tvShowNumberOfEpisodesTextView;
    TextView tvShowNumberOfSeasonsTextView;
    TextView tvShowTotalVotesTextView;
    TextView tvShowTypeTextView;
    TextView tvShowPopularityTextView;
    ExpandableTextView tvShowOverviewExpandableTextView;

    /*Add containers of recycler view for empty view setup*/
    LinearLayout containerTvShowTrailer;
    LinearLayout containerTvShowCast;
    LinearLayout containerTvShowReviews;
    LinearLayout containerTvShowSeasons;
    ProgressBar loadingIndicatorTvShowDetail;
    boolean myBool;
    private TvShowDetailsBundle mTvShowDetailsBundle;

    public TvShowDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv_show_detail, container, false);

        /* get the arguments from the tvshow poster(i.e., parent activity/fragment) so that new
        data can be fetched and current layout can be inflated in proper tvshow context */
        Bundle bundle = getArguments();

        myBool = bundle.getBoolean("myBool");

        tvShowDetailTitleTextView = (TextView) rootView.findViewById(R.id.tv_show_detail_title_text_view);
        tvShowDetailTitleImageView = (ImageView) rootView.findViewById(R.id.tv_show_detail_title_image_view);
        tvShowDetailsBackdropImageView = (ImageView) rootView.findViewById(R.id.tv_show_detail_title_image_view_backdrop);
        tvShowLastAirDateTextView = (TextView) rootView.findViewById(R.id.tv_show_last_air_date_text_view);
        tvShowRunTimeDurationTextView = (TextView) rootView.findViewById(R.id.tv_show_duration_text_view);
        tvShowTotalVotesTextView = (TextView) rootView.findViewById(R.id.tvShowTotalVotesValue);
        tvShowTypeTextView = (TextView) rootView.findViewById(R.id.typeTvShowValue);
        tvShowPopularityTextView = (TextView) rootView.findViewById(R.id.popularityTvShowValue);
        tvShowOverviewExpandableTextView = (ExpandableTextView) rootView.findViewById(R.id.expand_text_viewTvShowOverview);
        tvShowNumberOfEpisodesTextView = (TextView) rootView.findViewById(R.id.tvShowTotalEpisodesValue);
        tvShowNumberOfSeasonsTextView = (TextView) rootView.findViewById(R.id.tvShowTotalSeasonsValue);

        /*search recyclerview containers for the purpose of empty view */
        containerTvShowCast = (LinearLayout) rootView.findViewById(R.id.containerTvShowCast);
        containerTvShowReviews = (LinearLayout) rootView.findViewById(R.id.containerTvShowReviews);
        containerTvShowSeasons = (LinearLayout) rootView.findViewById(R.id.containerTvShowSeasons);
        containerTvShowTrailer = (LinearLayout) rootView.findViewById(R.id.containerTvShowTrailer);
        loadingIndicatorTvShowDetail = (ProgressBar) rootView.findViewById(R.id.loading_indicator_tv_show_detail);

         /* As there is no actionbar defined in the Style for this activity, so creating one toolbar_tv_show_detail for this fragment
        *  which will act as an actionbar after scrolling-up, referenced from StackOverflow link
        *  @link http://stackoverflow.com/a/32858049/5770629
        */
        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_tv_show_detail);
        if (myBool) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /*Creating a collapsing toolbar_tv_show_detail, defined in the fragment_tv_show_details.xml  */
        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar_tv_show_detail);

        if (savedInstanceState == null) {
            mReview = new ArrayList<>();
            mVideo = new ArrayList<>();
            mCredits = new ArrayList<>();
            mSeasons = new ArrayList<>();
            mTvShowDetailsBundle = new TvShowDetailsBundle();
        }

        if ((bundle != null)) {
            tvShow = getArguments().getParcelable("tvShow");

            /*inflate the details of tv show*/
            tvShowDetailTitleTextView.setText(tvShow.getTvShowName());
            tvShowLastAirDateTextView.setText(tvShow.getTvShowLastAirDate());
            posterURL = UrlsAndConstants.MoviePosterQuery.BASE_IMAGE_URL + tvShow.getTvShowPosterPath();
            backdropURL = UrlsAndConstants.MoviePosterQuery.BASE_IMAGE_URL + tvShow.getTvShowBackdropPath();
            collapsingToolbar.setTitle(tvShow.getTvShowName());
            Picasso.with(getContext())
                    .load(posterURL)
                    .placeholder(R.drawable.posterplaceholder)
                    .into(tvShowDetailTitleImageView);
            Picasso.with(getContext())
                    .load(backdropURL)
                    .placeholder(R.drawable.backdropimage)
                    .into(tvShowDetailsBackdropImageView);

            /*setting the ratingbar from @link: https://github.com/FlyingPumba/SimpleRatingBar*/
            SimpleRatingBar simpleRatingBar = (SimpleRatingBar) rootView.findViewById(R.id.tvShowRatingInsideTvShowDetailsFragment);
            simpleRatingBar.setRating((float) (tvShow.getTvShowVoteAverage()) / 2);

            tvShowOverviewExpandableTextView.setText(tvShow.getTvShowOverview());
            tvShowTotalVotesTextView.setText(String.valueOf(tvShow.getTvShowVoteCount()));
            tvShowPopularityTextView.setText(String.valueOf(tvShow.getTvShowPopularity()));


             /* First of all check if network is connected or not then only start the loader */
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

             /* fetch data. Get a reference to the LoaderManager, in order to interact with loaders. */
                startLoaderManager();
            }
            /*
            RecyclerView Codes are referenced from the @link: "https://guides.codepath.com/android/using-the-recyclerview"
            Lookup the recyclerview in activity layout
            */
            mTvShowReviewRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTvShowReviews);
            mTvShowTrailerRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTvShowsTrailers);
            mTvShowCastRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTvShowCast);
            mTvShowSeasonsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTvShowsSeasons);

            /* Create mPopularTvShowAdapter passing in the sample user data */
            mTvShowReviewAdapter = new TvShowReviewAdapter(getActivity(), mTvShowDetailsBundle);
             /* Create mPopularTvShowAdapter passing in the sample user data */
            mTvShowTrailerAdapter = new TvShowTrailerAdapter(getActivity(), mTvShowDetailsBundle);
                 /* Create mPopularTvShowAdapter passing in the sample user data */
            mTvShowCreditsCastAdapter = new TvShowCreditsCastAdapter(getActivity(), mTvShowDetailsBundle);
             /* Create mPopularTvShowAdapter passing in the sample user data */
            mTvShowSeasonsAdapter = new TvShowSeasonsAdapter(getActivity(), mTvShowDetailsBundle);

            /* Attach the mPopularTvShowAdapter to the reviewRecyclerView to populate items */
            mTvShowReviewRecyclerView.setAdapter(mTvShowReviewAdapter);
            /* Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items */
            mTvShowTrailerRecyclerView.setAdapter(mTvShowTrailerAdapter);
            /* Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items */
            mTvShowCastRecyclerView.setAdapter(mTvShowCreditsCastAdapter);
            /* Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items */
            mTvShowSeasonsRecyclerView.setAdapter(mTvShowSeasonsAdapter);

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

                 /*
            Setup layout manager for items with orientation
            Also supports `LinearLayoutManager.HORIZONTAL`
            */
            LinearLayoutManager layoutManagerTvShowSeason = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            /* Optionally customize the position you want to default scroll to */
            layoutManagerTvShowtrailer.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
            mTvShowSeasonsRecyclerView.setLayoutManager(layoutManagerTvShowSeason);

            /*Snap code for trailer review taken from
            * @link: "https://github.com/rubensousa/RecyclerViewSnap/"
            */

            SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
            snapHelperStart.attachToRecyclerView(mTvShowTrailerRecyclerView);

            SnapHelper snapHelperCastStart = new GravitySnapHelper(Gravity.START);
            snapHelperCastStart.attachToRecyclerView(mTvShowCastRecyclerView);

            SnapHelper snapHelperSeasonsStart = new GravitySnapHelper(Gravity.START);
            snapHelperSeasonsStart.attachToRecyclerView(mTvShowSeasonsRecyclerView);

                /*get loading indicator to work*/
            if (mTvShowDetailsBundle.getReviewArrayList().isEmpty() && mTvShowDetailsBundle.getCreditsArrayList().isEmpty()
                    && mTvShowDetailsBundle.getVideoArrayList().isEmpty() && mTvShowDetailsBundle.getSeasonsArrayList().isEmpty()) {
                loadingIndicatorTvShowDetail.setVisibility(View.VISIBLE);
                containerTvShowSeasons.setVisibility(View.GONE);
                containerTvShowTrailer.setVisibility(View.GONE);
                containerTvShowReviews.setVisibility(View.GONE);
                containerTvShowCast.setVisibility(View.GONE);
            } else if ((!(mTvShowDetailsBundle.getReviewArrayList().isEmpty()) ||
                    !(mTvShowDetailsBundle.getCreditsArrayList().isEmpty()) || !(mTvShowDetailsBundle.getVideoArrayList().isEmpty()
                    || !(mTvShowDetailsBundle.getSeasonsArrayList().isEmpty())))) {
                loadingIndicatorTvShowDetail.setVisibility(View.GONE);
            }

        }
        return rootView;
    }

    private void startLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(TV_SHOW_DETAIL_LOADER_ID, null, this);
    }

    @Override
    public Loader<TvShowDetailsBundle> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse((UrlsAndConstants.TvShowDetailQuery.DEFAULT_URL) + tvShow.getTvShowId());
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY_PARAM_VALUE);
        uriBuilder.appendQueryParameter(APPEND_TO_RESPONSE, VIDEOS_AND_REVIEWS_AND_CREDITS);
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
            // Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items
            mTvShowCreditsCastAdapter.setTvShowDetailsBundleData(mTvShowDetailsBundle);
            // Attach the mPopularTvShowAdapter to the trailerRecyclerView to populate items
            mTvShowSeasonsAdapter.setTvShowDetailsBundleData(mTvShowDetailsBundle);

            /* set adapters on recycler views */
            mTvShowReviewRecyclerView.setAdapter(mTvShowReviewAdapter);
            mTvShowTrailerRecyclerView.setAdapter(mTvShowTrailerAdapter);
            mTvShowCastRecyclerView.setAdapter(mTvShowCreditsCastAdapter);
            mTvShowSeasonsRecyclerView.setAdapter(mTvShowSeasonsAdapter);
            updateExtraDetailsTextView(mTvShowDetailsBundle);

            /* get Loading Indicators to work */
            if (!tvShowDetailsBundle.getVideoArrayList().isEmpty()) {
                containerTvShowTrailer.setVisibility(View.VISIBLE);
                loadingIndicatorTvShowDetail.setVisibility(View.GONE);
            } else {
                containerTvShowTrailer.setVisibility(View.GONE);
            }

            if (!tvShowDetailsBundle.getCreditsArrayList().isEmpty()) {
                containerTvShowCast.setVisibility(View.VISIBLE);
                loadingIndicatorTvShowDetail.setVisibility(View.GONE);
            } else {
                containerTvShowCast.setVisibility(View.GONE);
            }

            if (!tvShowDetailsBundle.getReviewArrayList().isEmpty()) {
                containerTvShowReviews.setVisibility(View.VISIBLE);
                loadingIndicatorTvShowDetail.setVisibility(View.GONE);
            } else {
                containerTvShowReviews.setVisibility(View.GONE);
            }
            if (!tvShowDetailsBundle.getSeasonsArrayList().isEmpty()) {
                containerTvShowSeasons.setVisibility(View.VISIBLE);
                loadingIndicatorTvShowDetail.setVisibility(View.GONE);
            } else {
                containerTvShowSeasons.setVisibility(View.GONE);
            }
            if (!tvShowDetailsBundle.getReviewArrayList().isEmpty() || !tvShowDetailsBundle.getVideoArrayList().isEmpty()
                    || !tvShowDetailsBundle.getCreditsArrayList().isEmpty() || !tvShowDetailsBundle.getSeasonsArrayList().isEmpty()) {
                loadingIndicatorTvShowDetail.setVisibility(View.GONE);
            }
        }
    }

    //Update the extra details(texts) that we get after Loader callback
    public void updateExtraDetailsTextView(TvShowDetailsBundle tvShowDetailsBundle) {
        TvShow detail = tvShowDetailsBundle.getTvShow();
        tvShowRunTimeDurationTextView.setText(String.valueOf(detail.getTvShowRuntime()));
        tvShowLastAirDateTextView.setText(detail.getTvShowLastAirDate());
        tvShowNumberOfEpisodesTextView.setText(String.valueOf(detail.getTvShowNumberEpisodes()));
        tvShowNumberOfSeasonsTextView.setText(String.valueOf(detail.getTvShowNumberOfSeasons()));
        tvShowTypeTextView.setText(detail.getTvShowType());
    }

    @Override
    public void onLoaderReset(Loader<TvShowDetailsBundle> loader) {
    }
}

