package me.abhishekraj.showmyshow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 12/4/2016.
 */

public class TopRatedMovieAdapter extends RecyclerView.Adapter<TopRatedMovieAdapter.ViewHolder> {

    /* Store a member variable for the movies */
    private static ArrayList<Movie> mDefaultMovie;
    /* Store the context for easy access */
    private Context mContext;

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
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
        public final TextView movieTitleTextView;
        public final ImageView movieTitleImageView;
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
            movieTitleTextView = (TextView) itemView.findViewById(R.id.grid_item_top_movie_title);
            movieTitleImageView = (ImageView) itemView.findViewById(R.id.grid_item_top_movie_image);
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
                Movie currentMovie = mDefaultMovie.get(position);
                // We can access the data within the views
                Toast.makeText(context, "" + currentMovie.getMovieTitle(), Toast.LENGTH_SHORT).show();
                Intent movieDetailIntent = new Intent(context, MovieDetailsActivity.class);
                movieDetailIntent.putExtra("movie", currentMovie);
                context.startActivity(movieDetailIntent);
            }
        }
    }

    /* Pass in the movies array into the constructor */
    public TopRatedMovieAdapter(Context context, ArrayList<Movie> movies) {
        mDefaultMovie = movies;
        mContext = context;
    }

    @Override
    public TopRatedMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View moviesView = inflater.inflate(R.layout.item_movie_grid_top_movies, parent, false);

        /* Return a new holder instance */
        TopRatedMovieAdapter.ViewHolder viewHolder = new TopRatedMovieAdapter.ViewHolder(context, moviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopRatedMovieAdapter.ViewHolder viewHolder, int position) {
        Log.v("############", "onBindViewHolder called");
        /* Get the data model based on position */
        Movie currentMovie = mDefaultMovie.get(position);
        Log.v("############", "currentMovie called is " + currentMovie.toString());
        Log.v("############", "currentMovie's title is " + currentMovie.getMovieTitle().toString());
        /*
        Set item views based on your views and data model
         */
        viewHolder.movieTitleTextView.setText(currentMovie.getMovieTitle());
        Log.v("############", "title is :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + currentMovie.getMovieTitle());
        String url = "https://image.tmdb.org/t/p/w500/" + currentMovie.getMoviePosterPath().toString();
        Log.v("############", "poster path is :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + currentMovie.getMoviePosterPath().toString());
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.movieTitleImageView);
    }

    @Override
    public int getItemCount() {
        Log.v("############", "getItemCount called with size " + mDefaultMovie.size());
        return mDefaultMovie.size();
    }

    public void setMovieData(ArrayList<Movie> movieData) {
        Log.v("############", "setMovieDetailsBundleData Called");
        mDefaultMovie = movieData;
        Log.v("############", "mDefaultMovie is " + mDefaultMovie);
        notifyDataSetChanged();
        Log.v("############", "notifyDataSetChanged Finished");
    }
}
