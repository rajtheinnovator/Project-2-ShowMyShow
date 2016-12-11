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

import me.abhishekraj.showmyshow.Model.TvShow.Credits;
import me.abhishekraj.showmyshow.Model.TvShow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.R;

import static me.abhishekraj.showmyshow.Utils.UrlsAndConstants.MovieDetailQuery.BASE_IMAGE_URL;

/**
 * Created by ABHISHEK RAJ on 12/11/2016.
 */

public class TvShowCreditsCastAdapter extends RecyclerView.Adapter<TvShowCreditsCastAdapter.ViewHolder> {

    // Store a member variable for the popularMovies
    private static ArrayList<Credits> mCredits;
    TvShowDetailsBundle tvShowDetailsBundle;
    // Store the context for easy access
    private Context mContext;

    // Pass in the tvShows array into the constructor
    public TvShowCreditsCastAdapter(Context context, TvShowDetailsBundle tvShows) {
        tvShowDetailsBundle = tvShows;
        mCredits = new ArrayList<>();
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public TvShowCreditsCastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tvShowsView = inflater.inflate(R.layout.item_movie_cast, parent, false);

        // Return a new holder instance
        TvShowCreditsCastAdapter.ViewHolder viewHolder = new TvShowCreditsCastAdapter.ViewHolder(context, tvShowsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TvShowCreditsCastAdapter.ViewHolder viewHolder, int position) {
        Log.v("############", "onBindViewHolder called");
        // Get the data model based on position
        Credits currentCast = mCredits.get(position);
        Log.v("############", "currentCredits called is " + currentCast.toString());
        /*
        Set item views based on your views and data model
        */
        String url = BASE_IMAGE_URL + currentCast.getTvShowCastProfilePath();
        Log.v("#######***##", "URL of TvShow is " + url);
        viewHolder.tvShowCastName.setText(currentCast.getTvShowCastName());
        viewHolder.tvShowCastCharacter.setText(currentCast.getTvShowCastCharacter());
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.tvShowCastImageView);


    }

    @Override
    public int getItemCount() {
        Log.v("############", "getItemCount called with size " + mCredits.size());
        return mCredits.size();
    }

    public void setTvShowDetailsBundleData(TvShowDetailsBundle tvShowData) {
        Log.v("############", "setTvShowDetailsBundleData Called");
        tvShowDetailsBundle = tvShowData;
        mCredits = tvShowDetailsBundle.getCreditsArrayList();
        Log.v("############", "mDefaultTvShow is " + mCredits);
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
        public final ImageView tvShowCastImageView;
        public final TextView tvShowCastName;
        public final TextView tvShowCastCharacter;

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

            tvShowCastImageView = (ImageView) itemView.findViewById(R.id.castImageView);
            tvShowCastName = (TextView) itemView.findViewById(R.id.castName);
            tvShowCastCharacter = (TextView) itemView.findViewById(R.id.castCharacter);
            this.context = context;
        }
    }
}
