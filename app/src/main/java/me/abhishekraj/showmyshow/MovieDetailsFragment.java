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
    DefaultMovieAdapter mAdapter;
    RecyclerView mRecyclerView;
    Movie movie;
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    TextView textView;
    ImageView imageView;
    ImageView imageViewBackdrop;
    CollapsingToolbarLayout collapsingToolbar;
    String posterURL;
    String backdropURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle bundle = getArguments();
        textView = (TextView) rootView.findViewById(R.id.movie_detail_title_text_view);
        imageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view);
        imageViewBackdrop = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view_backdrop);

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

        if ((bundle != null)) {
            movie = getArguments().getParcelable("movie");
            textView.setText(movie.getMovieTitle());
            posterURL = UrlsAndConstants.DefaultQuery.BASE_IMAGE_URL + movie.getMoviePosterPath();
            backdropURL = UrlsAndConstants.DefaultQuery.BASE_IMAGE_URL + movie.getMovieBackdropPath();
            collapsingToolbar.setTitle(movie.getMovieTitle());
            Picasso.with(getContext())
                    .load(posterURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            Picasso.with(getContext())
                    .load(backdropURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageViewBackdrop);

             /* First of all check if network is connected or not then only start the loader */
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

             /* fetch data. Get a reference to the LoaderManager, in order to interact with loaders. */
                startLoaderManager();
                Log.v("############", "startLoaderManager called");
            }

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
    public void onLoadFinished(Loader<MovieDetailsBundle> loader, MovieDetailsBundle data) {
        if (data != null) {
            mReview = data.getReviewArrayList();
            mVideo = data.getVideoArrayList();
            ArrayList<String> videoArrayList = new ArrayList<>();
            Log.v("############", "MovieDetailsBundle is" + data.toString());
            Log.v("############", "onLoadFinished with MovieDetailsBundle");
            for (Video videos :
                    mVideo) {
                videoArrayList.add(videos.getMovieVideoName());
            }
            Log.v("############", "Size of mReview is" + mReview.size());
            Log.v("############", "Size of mVideo is" + mVideo.size());
            Log.v("############", "Size of videoArrayList is" + videoArrayList.size());
            textView.setText(videoArrayList.get(0));
        }
    }
    @Override
    public void onLoaderReset(Loader<MovieDetailsBundle> loader) {

    }
}
