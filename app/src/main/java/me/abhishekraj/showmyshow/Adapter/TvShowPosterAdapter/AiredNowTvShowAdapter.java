package me.abhishekraj.showmyshow.Adapter.TvShowPosterAdapter;

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

import me.abhishekraj.showmyshow.Model.TvShow.TvShow;
import me.abhishekraj.showmyshow.R;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class AiredNowTvShowAdapter extends RecyclerView.Adapter<AiredNowTvShowAdapter.ViewHolder> {

    /* Store a member variable for the popularMovies */
    private static ArrayList<TvShow> mAiredNowTvShows;
    /* Store the context for easy access */
    private Context mContext;

    /* Pass in the airedNowTvShow array into the constructor */
    public AiredNowTvShowAdapter(Context context, ArrayList<TvShow> tvShows) {
        mAiredNowTvShows = tvShows;
        mContext = context;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @Override
    public AiredNowTvShowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View moviesView = inflater.inflate(R.layout.item_movies_poster, parent, false);

        /* Return a new holder instance */
        AiredNowTvShowAdapter.ViewHolder viewHolder = new AiredNowTvShowAdapter.ViewHolder(context, moviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AiredNowTvShowAdapter.ViewHolder viewHolder, int position) {
        Log.v("############", "onBindViewHolder called");
        /* Get the data model based on position */
        TvShow currentTvShow = mAiredNowTvShows.get(position);
        Log.v("############", "currentTvShow called is " + currentTvShow.toString());
        Log.v("############", "currentTvShow's title is " + currentTvShow.getTvShowName().toString());
        /*
        Set item views based on your views and data model
         */
        viewHolder.tvShowTitleTextView.setText(currentTvShow.getTvShowName());
        Log.v("############", "title is :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + currentTvShow.getTvShowName());
        String url = "https://image.tmdb.org/t/p/w500/" + currentTvShow.getTvShowPosterPath().toString();
        Log.v("############", "poster path is :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + currentTvShow.getTvShowPosterPath().toString());
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.tvShowTitleImageView);
    }

    @Override
    public int getItemCount() {
        Log.v("############", "getItemCount called with size " + mAiredNowTvShows.size());
        return mAiredNowTvShows.size();
    }

    public void setTvShowData(ArrayList<TvShow> tvShowData) {
        Log.v("############", "setTvShowDetailsBundleData Called");
        mAiredNowTvShows = tvShowData;
        Log.v("############", "mAiredNowTvShows is " + mAiredNowTvShows);
        notifyDataSetChanged();
        Log.v("############", "notifyDataSetChanged Finished");
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
            tvShowTitleTextView = (TextView) itemView.findViewById(R.id.grid_item_movie_poster_title);
            tvShowTitleImageView = (ImageView) itemView.findViewById(R.id.grid_item_movie_poster_image);
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
//                Movie currentMovie = mAiredNowTvShows.get(position);
//                // We can access the data within the views
//                Toast.makeText(context, "" + currentMovie.getMovieTitle(), Toast.LENGTH_SHORT).show();
//                Intent movieDetailIntent = new Intent(context, MovieDetailsActivity.class);
//                movieDetailIntent.putExtra("movie", currentMovie);
//                context.startActivity(movieDetailIntent);
            }
        }
    }
}
