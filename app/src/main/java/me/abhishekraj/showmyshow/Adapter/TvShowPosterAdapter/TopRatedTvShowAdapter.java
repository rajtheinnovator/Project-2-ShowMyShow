package me.abhishekraj.showmyshow.adapter.tvshowposteradapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.activity.TvShowDetailsActivity;
import me.abhishekraj.showmyshow.model.tvshow.TvShow;
import me.abhishekraj.showmyshow.R;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TopRatedTvShowAdapter extends RecyclerView.Adapter<TopRatedTvShowAdapter.ViewHolder> {

    /* Store a member variable for the topRatedTvShows */
    private static ArrayList<TvShow> mTopRatedTvShows;
    /* Store the context for easy access */
    private Context mContext;

    /* Pass in the topRatedTvShows array into the constructor */
    public TopRatedTvShowAdapter(Context context, ArrayList<TvShow> tvShows) {
        mTopRatedTvShows = tvShows;
        mContext = context;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @Override
    public TopRatedTvShowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View tvShowsView = inflater.inflate(R.layout.item_movies_and_tv_show_poster, parent, false);

        /* Return a new holder instance */
        TopRatedTvShowAdapter.ViewHolder viewHolder = new TopRatedTvShowAdapter.ViewHolder(context, tvShowsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopRatedTvShowAdapter.ViewHolder viewHolder, int position) {
        /* Get the data model based on position */
        TvShow currentTvShow = mTopRatedTvShows.get(position);
        /*
        Set item views based on your views and data model
         */
        viewHolder.tvShowTitleTextView.setText(currentTvShow.getTvShowName());
        viewHolder.tvShowVoteAverageTextView.setText(String.valueOf((long) (currentTvShow.getTvShowVoteAverage()) / 2));
        String url = "https://image.tmdb.org/t/p/w500/" + currentTvShow.getTvShowPosterPath().toString();
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.posterplaceholder)
                .into(viewHolder.tvShowTitleImageView);
    }

    @Override
    public int getItemCount() {
        return mTopRatedTvShows.size();
    }

    public void setTvShowData(ArrayList<TvShow> tvShowData) {
        mTopRatedTvShows = tvShowData;
        notifyDataSetChanged();
    }

    /*
     Provide a direct reference to each of the views within a data item
     Used to cache the views within the item layout for fast access
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /*
        Your holder should contain a member variable
        for any view that will be set as you render a row
        */
        public final TextView tvShowTitleTextView;
        public final ImageView tvShowTitleImageView;
        public final TextView tvShowVoteAverageTextView;
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
            tvShowTitleTextView = (TextView) itemView.findViewById(R.id.grid_item_movie_and_tv_show_poster_title);
            tvShowTitleImageView = (ImageView) itemView.findViewById(R.id.grid_item_movie_and_tv_show_poster_image);
            tvShowVoteAverageTextView = (TextView) itemView.findViewById(R.id.voteAverageMovieAndTvShowPoster);
            this.context = context;
            /* Attach a click listener to the entire row view */
            itemView.setOnClickListener(this);
        }

        /*The codes below and at some other places throughout the app related to RecyclerView has been
        * taken from multiple sources on the web including from the following @link:
        * "https://guides.codepath.com/android/using-the-recyclerview
        */
        /* Handles the row being being clicked */
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                TvShow currentTvShow = mTopRatedTvShows.get(position);
                /* We can access the data within the views */
                Toast.makeText(context, "" + currentTvShow.getTvShowName(), Toast.LENGTH_SHORT).show();
                Intent tvShowDetailIntent = new Intent(context, TvShowDetailsActivity.class);
                tvShowDetailIntent.putExtra("tvShow", currentTvShow);

                context.startActivity(tvShowDetailIntent);
            }
        }
    }
}
