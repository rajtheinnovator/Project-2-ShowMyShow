package me.abhishekraj.showmyshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 11/26/2016.
 */

public class MovieDetailsAdapter extends ArrayAdapter<Movie> {

    // Store the context for easy access
    private Context mContext;

    // Pass in the movies array into the constructor
    public MovieDetailsAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
        mContext = context;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView textView;
        ImageView imageView;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Movie mMovie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            /* If there's no view to re-use, inflate a brand new view for row */
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_movie_detail, parent, false);
            /*Find the TextView and ImageView and set them on the VIewHolder*/
            viewHolder.textView = (TextView) convertView.findViewById(R.id.movie_detail_title_text_view);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.movie_detail_title_image_view);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mMovie != null) {
            // Populate the data into the template view using the data object
            viewHolder.textView.setText(mMovie.getMovieTitle().toString());

            String url = "https://image.tmdb.org/t/p/w500/" + mMovie.getMoviePosterPath().toString();

            Picasso.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(viewHolder.imageView);
        }
        return convertView;
    }
}
