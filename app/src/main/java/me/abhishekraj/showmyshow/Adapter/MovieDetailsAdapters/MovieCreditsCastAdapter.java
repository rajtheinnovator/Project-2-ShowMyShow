package me.abhishekraj.showmyshow.Adapter.MovieDetailsAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.Model.Movie.Credits;
import me.abhishekraj.showmyshow.Model.Movie.MovieDetailsBundle;
import me.abhishekraj.showmyshow.R;

import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MovieDetailQuery.BASE_IMAGE_URL;

/**
 * Created by ABHISHEK RAJ on 12/9/2016.
 */

public class MovieCreditsCastAdapter extends RecyclerView.Adapter<MovieCreditsCastAdapter.ViewHolder> {

    // Store a member variable for the popularMovies
    private static ArrayList<Credits> mCredits;
    MovieDetailsBundle movieDetailsBundle;
    // Store the context for easy access
    private Context mContext;

    // Pass in the popularMovies array into the constructor
    public MovieCreditsCastAdapter(Context context, MovieDetailsBundle movies) {
        movieDetailsBundle = movies;
        mCredits = new ArrayList<>();
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public MovieCreditsCastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View moviesView = inflater.inflate(R.layout.item_movie_cast, parent, false);

        // Return a new holder instance
        MovieCreditsCastAdapter.ViewHolder viewHolder = new MovieCreditsCastAdapter.ViewHolder(context, moviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieCreditsCastAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Credits currentCast = mCredits.get(position);
        /*
        Set item views based on your views and data model
        */
        String url = BASE_IMAGE_URL + currentCast.getMovieCastProfilePath();
        viewHolder.movieCastName.setText(currentCast.getMovieCastName());
        viewHolder.movieCastCharacter.setText(currentCast.getMovieCastCharacter());
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.movieCastImageView);


    }

    @Override
    public int getItemCount() {
        return mCredits.size();
    }

    public void setMovieDetailsBundleData(MovieDetailsBundle movieData) {
        movieDetailsBundle = movieData;
        mCredits = movieDetailsBundle.getCreditsArrayList();
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
        public final ImageView movieCastImageView;
        public final TextView movieCastName;
        public final TextView movieCastCharacter;

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

            movieCastImageView = (ImageView) itemView.findViewById(R.id.castImageView);
            movieCastName = (TextView) itemView.findViewById(R.id.castName);
            movieCastCharacter = (TextView) itemView.findViewById(R.id.castCharacter);
            this.context = context;
        }
    }
}
