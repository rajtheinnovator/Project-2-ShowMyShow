package me.abhishekraj.showmyshow.Adapter.TvShowDetailsAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.Model.TvShow.Review;
import me.abhishekraj.showmyshow.Model.TvShow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.R;

/**
 * Created by ABHISHEK RAJ on 12/11/2016.
 */

public class TvShowReviewAdapter extends RecyclerView.Adapter<TvShowReviewAdapter.ViewHolder> {

    // Store a member variable for the tvShow
    private static ArrayList<Review> mReview;
    TvShowDetailsBundle tvShowDetailsBundle;
    // Store the context for easy access
    private Context mContext;

    // Pass in the tvShows array into the constructor
    public TvShowReviewAdapter(Context context, TvShowDetailsBundle tvShows) {
        tvShowDetailsBundle = tvShows;
        mReview = new ArrayList<>();
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public TvShowReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tvShowView = inflater.inflate(R.layout.item_movie_review, parent, false);

        // Return a new holder instance
        TvShowReviewAdapter.ViewHolder viewHolder = new TvShowReviewAdapter.ViewHolder(context, tvShowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TvShowReviewAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Review currentReview = mReview.get(position);
        /*
        Set item views based on your views and data model
        TextView movieDetailTitleTextView = viewHolder.movieReviewContentTextView;
        */
        viewHolder.expTv1.setText(currentReview.getTvShowReviewContent().trim());
        viewHolder.tvShowReviewAuthorTextView.setText(currentReview.getTvShowReviewAuthor().trim());

    }

    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public void setTvShowDetailsBundleData(TvShowDetailsBundle tvShowData) {
        tvShowDetailsBundle = tvShowData;
        mReview = tvShowDetailsBundle.getReviewArrayList();
        notifyDataSetChanged();
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
        //public final TextView tvShowReviewAuthorTextView;
        public final TextView tvShowReviewAuthorTextView;
        /*
        * Make the review content expandable by using the code
        * from: @link https://github.com/Manabu-GT/ExpandableTextView
        */
        public final ExpandableTextView expTv1;
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
            //set the review content on the ExpandableTextView

            expTv1 = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);

            // tvShowReviewAuthorTextView = (TextView) itemView.findViewById(R.id.review_content);
            tvShowReviewAuthorTextView = (TextView) itemView.findViewById(R.id.review_author);
            this.context = context;
        }
    }
}