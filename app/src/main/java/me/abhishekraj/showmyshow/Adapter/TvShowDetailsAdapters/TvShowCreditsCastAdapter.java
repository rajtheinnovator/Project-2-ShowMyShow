package me.abhishekraj.showmyshow.adapter.tvshowdetailsadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.model.tvshow.Credits;
import me.abhishekraj.showmyshow.model.tvshow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.R;

import static me.abhishekraj.showmyshow.utils.UrlsAndConstants.MovieDetailQuery.BASE_IMAGE_URL;

/**
 * Created by ABHISHEK RAJ on 12/11/2016.
 */

public class TvShowCreditsCastAdapter extends RecyclerView.Adapter<TvShowCreditsCastAdapter.ViewHolder> {

    /* Store a member variable for the tvShowCredits */
    private static ArrayList<Credits> mCredits;
    TvShowDetailsBundle tvShowDetailsBundle;
    /* Store the context for easy access */
    private Context mContext;

    /* Pass in the tvShowsDetails bundle into the constructor */
    public TvShowCreditsCastAdapter(Context context, TvShowDetailsBundle tvShows) {
        tvShowDetailsBundle = tvShows;
        mCredits = new ArrayList<>();
        mContext = context;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @Override
    public TvShowCreditsCastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View tvShowsView = inflater.inflate(R.layout.item_movie_and_tv_show_cast, parent, false);

        /* Return a new holder instance */
        TvShowCreditsCastAdapter.ViewHolder viewHolder = new TvShowCreditsCastAdapter.ViewHolder(context, tvShowsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TvShowCreditsCastAdapter.ViewHolder viewHolder, int position) {
        /* Get the data model based on position */
        Credits currentCast = mCredits.get(position);
        /*
        Set item views based on your views and data model
        */
        String url = BASE_IMAGE_URL + currentCast.getTvShowCastProfilePath();
        viewHolder.tvShowCastName.setText(currentCast.getTvShowCastName());
        viewHolder.tvShowCastCharacter.setText(currentCast.getTvShowCastCharacter());
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.castplaceholder)
                .into(viewHolder.tvShowCastImageView);


    }

    @Override
    public int getItemCount() {
        return mCredits.size();
    }

    /* call this method from TvShowDetailsFragment to set new data */
    public void setTvShowDetailsBundleData(TvShowDetailsBundle tvShowData) {
        tvShowDetailsBundle = tvShowData;
        mCredits = tvShowDetailsBundle.getCreditsArrayList();
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
