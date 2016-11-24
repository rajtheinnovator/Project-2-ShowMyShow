package me.abhishekraj.showmyshow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 11/16/2016.
 */

public class DefaultMovieAdapter extends  RecyclerView.Adapter<DefaultMovieAdapter.ViewHolder>{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView movieTitleTextView;
        public ImageView movieTitleImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            movieTitleTextView = (TextView) itemView.findViewById(R.id.grid_item_movie_title);
            movieTitleImageView = (ImageView) itemView.findViewById(R.id.grid_item_movie_image);
        }
    }
    // Store a member variable for the movies
    private ArrayList<Movie> mDefaultMovie;
    // Store the context for easy access
    private Context mContext;

    // Pass in the movies array into the constructor
    public DefaultMovieAdapter(Context context, ArrayList<Movie> movies) {
        mDefaultMovie = movies;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    @Override
    public DefaultMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View moviesView = inflater.inflate(R.layout.item_movies, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(moviesView);
        return viewHolder;
    }
    public void add(int position, Movie item) {
        mDefaultMovie.add(position, item);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(DefaultMovieAdapter.ViewHolder viewHolder, int position) {
        Log.v("############", "onBindViewHolder called");
        // Get the data model based on position
        Movie movie = mDefaultMovie.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.movieTitleTextView;
        textView.setText(movie.getMovieTitle());
        Log.v("############", "title is :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+movie.getMovieTitle());
        ImageView button = viewHolder.movieTitleImageView;
        button.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        Log.v("############", "getItemCount called with size"+ mDefaultMovie.size());
        return mDefaultMovie.size();
    }
}
