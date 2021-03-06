package me.abhishekraj.showmyshow.adapter.moviedetailsadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.model.movie.MovieDetailsBundle;
import me.abhishekraj.showmyshow.model.movie.Review;
import me.abhishekraj.showmyshow.R;

/**
 * Created by ABHISHEK RAJ on 12/2/2016.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

    /* Store a member variable for the popularMovies */
    private static ArrayList<Review> mReview;
    MovieDetailsBundle movieDetailsBundle;

    /* Store the context for easy access */
    private Context mContext;

    /* Pass in the popularMovies array into the constructor */
    public MovieReviewAdapter(Context context, MovieDetailsBundle movies) {
        movieDetailsBundle = movies;
        mReview = new ArrayList<>();
        mContext = context;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @Override
    public MovieReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View moviesView = inflater.inflate(R.layout.item_movie_and_tv_show_review, parent, false);

        /* Return a new holder instance */
        MovieReviewAdapter.ViewHolder viewHolder = new MovieReviewAdapter.ViewHolder(context, moviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapter.ViewHolder viewHolder, int position) {
        /* Get the data model based on position */
        Review currentReview = mReview.get(position);
        /*
        Set item views based on your views and data model
        */
        viewHolder.expTv1.setText(currentReview.getMovieReviewContent().trim());
        viewHolder.movieReviewAuthorTextView.setText(currentReview.getMovieReviewAuthor().trim());
    }

    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public void setMovieDetailsBundleData(MovieDetailsBundle movieData) {
        movieDetailsBundle = movieData;
        mReview = movieDetailsBundle.getReviewArrayList();
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

        public final TextView movieReviewAuthorTextView;
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

            /* set the review content on the ExpandableTextView */
            expTv1 = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
            movieReviewAuthorTextView = (TextView) itemView.findViewById(R.id.review_author);
            this.context = context;
        }
    }
}
