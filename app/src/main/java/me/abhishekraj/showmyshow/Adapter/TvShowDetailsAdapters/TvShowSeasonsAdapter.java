package me.abhishekraj.showmyshow.Adapter.TvShowDetailsAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.Model.TvShow.Seasons;
import me.abhishekraj.showmyshow.Model.TvShow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.R;

/**
 * Created by ABHISHEK RAJ on 12/13/2016.
 */

public class TvShowSeasonsAdapter extends RecyclerView.Adapter<TvShowSeasonsAdapter.ViewHolder> {

    // Store a member variable for the tvShow
    private static ArrayList<Seasons> mSeason;
    TvShowDetailsBundle tvShowDetailsBundle;
    // Store the context for easy access
    private Context mContext;

    // Pass in the TvShows array into the constructor
    public TvShowSeasonsAdapter(Context context, TvShowDetailsBundle tvShows) {
        tvShowDetailsBundle = tvShows;
        mSeason = new ArrayList<>();
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public TvShowSeasonsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tvShowsView = inflater.inflate(R.layout.item_tv_show_seasons, parent, false);

        // Return a new holder instance
        TvShowSeasonsAdapter.ViewHolder viewHolder = new TvShowSeasonsAdapter.ViewHolder(context, tvShowsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TvShowSeasonsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Seasons currentSeason = mSeason.get(position);
        /*
        Set item views based on your views and data model
        TextView tvShowDetailTitleTextView = viewHolder.tvShowReviewContentTextView;
        */
        viewHolder.tvShowSeasonAirDate.setText(currentSeason.getTvShowSeasonAirDate());
        viewHolder.tvSeasonEpisodeCount.setText(String.valueOf(currentSeason.getTvShowSeasonEpisodeCount()));

        //Youtube thumbnail url referenced from @link: "http://stackoverflow.com/a/8842839/5770629"
        String url = "https://image.tmdb.org/t/p/w500/" + currentSeason.getTvShowSeasonPosterPath();
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.posterplaceholder)
                .into(viewHolder.tvShowSeasonBaner);

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mSeason.isEmpty()) {
            size = 0;
        } else {
            size = mSeason.size();
        }
        return size;
    }

    public void setTvShowDetailsBundleData(TvShowDetailsBundle tvShowBundledData) {
        tvShowDetailsBundle = tvShowBundledData;
        mSeason = tvShowDetailsBundle.getSeasonsArrayList();
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

        public final TextView tvShowSeasonAirDate;
        public final ImageView tvShowSeasonBaner;
        public final TextView tvSeasonEpisodeCount;

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

            tvShowSeasonBaner = (ImageView) itemView.findViewById(R.id.season_banner);
            tvShowSeasonAirDate = (TextView) itemView.findViewById(R.id.season_air_date);
            tvSeasonEpisodeCount = (TextView) itemView.findViewById(R.id.season_episode_count);
            this.context = context;
        }
    }
}
