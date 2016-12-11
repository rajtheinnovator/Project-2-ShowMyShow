package me.abhishekraj.showmyshow.Adapter.TvShowDetailsAdapters;

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

import me.abhishekraj.showmyshow.Model.TvShow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.Model.TvShow.Video;
import me.abhishekraj.showmyshow.R;

/**
 * Created by ABHISHEK RAJ on 12/11/2016.
 */

public class TvShowTrailerAdapter extends RecyclerView.Adapter<TvShowTrailerAdapter.ViewHolder> {

    // Store a member variable for the tvShow
    private static ArrayList<Video> mVideo;
    TvShowDetailsBundle tvShowDetailsBundle;
    // Store the context for easy access
    private Context mContext;

    // Pass in the TvShows array into the constructor
    public TvShowTrailerAdapter(Context context, TvShowDetailsBundle tvShows) {
        tvShowDetailsBundle = tvShows;
        mVideo = new ArrayList<>();
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public TvShowTrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tvShowsView = inflater.inflate(R.layout.item_movie_trailer, parent, false);

        // Return a new holder instance
        TvShowTrailerAdapter.ViewHolder viewHolder = new TvShowTrailerAdapter.ViewHolder(context, tvShowsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TvShowTrailerAdapter.ViewHolder viewHolder, int position) {
        Log.v("############", "onBindViewHolder called");
        // Get the data model based on position
        Video currentVideo = mVideo.get(position);
        Log.v("############", "currentVideo called is " + currentVideo.toString());
        Log.v("############", "currentVideo's title is " + currentVideo.getTvShowVideoName().toString());
        /*
        Set item views based on your views and data model
        TextView tvShowDetailTitleTextView = viewHolder.tvShowReviewContentTextView;
        */
        viewHolder.tvShowVideoName.setText(currentVideo.getTvShowVideoName());
        Log.v("############", "title is :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + currentVideo.getTvShowVideoName());

        //Youtube thumbnail url referenced from @link: "http://stackoverflow.com/a/8842839/5770629"
        String url = "http://img.youtube.com/vi/" + currentVideo.getTvShowVideoKey();
        String completeUrl = url + "/0.jpg";
        Picasso.with(getContext())
                .load(completeUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.tvShowVideoBanner);
        Log.v("############", "trailer url is :>>>>>>>>>>>****>>>>>>>>" + completeUrl);

    }

    @Override
    public int getItemCount() {
        Log.v("############", "getItemCount called with size " + mVideo.size());
        return mVideo.size();
    }

    public void setTvShowDetailsBundleData(TvShowDetailsBundle tvShowBundledData) {
        Log.v("############", "setTvShowDetailsBundleData Called");
        tvShowDetailsBundle = tvShowBundledData;
        mVideo = tvShowDetailsBundle.getVideoArrayList();
        Log.v("############", "mDefaultTvShow is " + mVideo);
        notifyDataSetChanged();
        Log.v("############", "notifyDataSetChanged Finished");
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
        //public final TextView tvShowVideoContentTextView;
        public final TextView tvShowVideoName;
        public final ImageView tvShowVideoBanner;

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

            tvShowVideoBanner = (ImageView) itemView.findViewById(R.id.video_banner);
            // tvShowReviewContentTextView = (TextView) itemView.findViewById(R.id.review_content);
            tvShowVideoName = (TextView) itemView.findViewById(R.id.video_name);
            this.context = context;
        }
    }
}