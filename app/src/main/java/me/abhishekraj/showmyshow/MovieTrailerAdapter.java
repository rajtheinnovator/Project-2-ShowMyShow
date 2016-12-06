package me.abhishekraj.showmyshow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 12/2/2016.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    // Store a member variable for the defaultMovies
    private static ArrayList<Video> mVideo;

    // Store the context for easy access
    private Context mContext;
    MovieDetailsBundle movieDetailsBundle;

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    /*
     Provide a direct reference to each of the views within a data item
     Used to cache the views within the item layout for fast access
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*
        Your holder should contain a member variable
        for any view that will be set as you render a row
        */
        //public final TextView movieVideoContentTextView;
        public final TextView movieVideoName;
        public final ImageView movieVideoBanner;

        private Context context;

        /*
        We also create a constructor that accepts the entire item row
        and does the view lookups to find each subview
        */
        public ViewHolder(Context context, View itemView) {
            /*
            Stores the itemView in a public final member variable that can be used
            to access the context from any ViewHolder instance.
            */
            super(itemView);

            movieVideoBanner = (ImageView) itemView.findViewById(R.id.video_banner);
            // movieReviewContentTextView = (TextView) itemView.findViewById(R.id.review_content);
            movieVideoName = (TextView) itemView.findViewById(R.id.video_name);
            this.context = context;
        }
    }

    // Pass in the defaultMovies array into the constructor
    public MovieTrailerAdapter(Context context, MovieDetailsBundle movies) {
        movieDetailsBundle = movies;
        mVideo = new ArrayList<>();
        mContext = context;
    }

    @Override
    public MovieTrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View moviesView = inflater.inflate(R.layout.item_movie_trailer, parent, false);

        // Return a new holder instance
        MovieTrailerAdapter.ViewHolder viewHolder = new MovieTrailerAdapter.ViewHolder(context, moviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieTrailerAdapter.ViewHolder viewHolder, int position) {
        Log.v("############", "onBindViewHolder called");
        // Get the data model based on position
        Video currentVideo = mVideo.get(position);
        Log.v("############", "currentVideo called is " + currentVideo.toString());
        Log.v("############", "currentVideo's title is " + currentVideo.getMovieVideoName().toString());
        /*
        Set item views based on your views and data model
        TextView movieDetailTitleTextView = viewHolder.movieReviewContentTextView;
        */
        viewHolder.movieVideoName.setText(currentVideo.getMovieVideoName());
        Log.v("############", "title is :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + currentVideo.getMovieVideoName());

        //Youtube thumbnail url referenced from @link: "http://stackoverflow.com/a/8842839/5770629"
        String url = "http://img.youtube.com/vi/" + currentVideo.getMovieVideoKey();
        String completeUrl = url+"/0.jpg";
        Picasso.with(getContext())
                .load(completeUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.movieVideoBanner);
        Log.v("############", "trailer url is :>>>>>>>>>>>****>>>>>>>>" + completeUrl);

    }

    @Override
    public int getItemCount() {
        Log.v("############", "getItemCount called with size " + mVideo.size());
        return mVideo.size();
    }

    public void setMovieDetailsBundleData(MovieDetailsBundle movieBundledData) {
        Log.v("############", "setMovieDetailsBundleData Called");
        movieDetailsBundle = movieBundledData;
        mVideo = movieDetailsBundle.getVideoArrayList();
        Log.v("############", "mDefaultMovie is " + mVideo);
        notifyDataSetChanged();
        Log.v("############", "notifyDataSetChanged Finished");
    }
}
