package me.abhishekraj.showmyshow.Adapter.MovieDetailsAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.Model.Movie.MovieDetailsBundle;
import me.abhishekraj.showmyshow.Model.Movie.Video;
import me.abhishekraj.showmyshow.R;

/**
 * Created by ABHISHEK RAJ on 12/2/2016.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    // Store a member variable for the popularMovies
    private static ArrayList<Video> mVideo;
    MovieDetailsBundle movieDetailsBundle;
    // Store the context for easy access
    private Context mContext;

    // Pass in the popularMovies array into the constructor
    public MovieTrailerAdapter(Context context, MovieDetailsBundle movies) {
        movieDetailsBundle = movies;
        mVideo = new ArrayList<>();
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
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
        // Get the data model based on position
        Video currentVideo = mVideo.get(position);

        /*
        Set item views based on your views and data model
        TextView movieDetailTitleTextView = viewHolder.movieReviewContentTextView;
        */
        viewHolder.movieVideoName.setText(currentVideo.getMovieVideoName());

        //Youtube thumbnail url referenced from @link: "http://stackoverflow.com/a/8842839/5770629"
        String url = "http://img.youtube.com/vi/" + currentVideo.getMovieVideoKey();
        String completeUrl = url + "/0.jpg";
        Picasso.with(getContext())
                .load(completeUrl)
                .placeholder(R.drawable.placeholdercinema)
                .into(viewHolder.movieVideoBanner);
    }

    @Override
    public int getItemCount() {
        return mVideo.size();
    }

    public void setMovieDetailsBundleData(MovieDetailsBundle movieBundledData) {
        movieDetailsBundle = movieBundledData;
        mVideo = movieDetailsBundle.getVideoArrayList();
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
                Video currentVideo = mVideo.get(position);
                // We can access the data within the views
                Intent goToUrl = new Intent(Intent.ACTION_VIEW);
                String videoPlaybackUrl = "https://www.youtube.com/watch?v=" + currentVideo.getMovieVideoKey();
                goToUrl.setData(Uri.parse(videoPlaybackUrl));
                context.startActivity(goToUrl);
            }
        }
    }
}
