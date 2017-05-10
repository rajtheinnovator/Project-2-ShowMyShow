package me.abhishekraj.showmyshow.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.data.MovieContract.MoviesEntry;

/**
 * Created by ABHISHEK RAJ on 5/9/2017.
 */

public class FavoriteCursorAdapter extends SimpleCursorAdapter {

    private Context context;

    private int layout;

    public FavoriteCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flag) {
        super(context, layout, c, from, to, flag);
        this.context = context;
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);


        return v;
    }

    @Override
    public void bindView(View v, final Context context, Cursor cursor) {

        /*Instantiate the views*/
        TextView titleText = (TextView) v.findViewById(R.id.moivie_detail_title_text_view);
        TextView releaseDateText = (TextView) v.findViewById(R.id.movie_release_date_text_view);
        ImageView posterImage = (ImageView) v.findViewById(R.id.movie_detail_title_image_view);
        final ImageButton favoriteStatusImage = (ImageButton) v.findViewById(R.id.favorite);

            /*setting the ratingbar from @link: https://github.com/FlyingPumba/SimpleRatingBar*/
        SimpleRatingBar simpleRatingBar = (SimpleRatingBar) v.findViewById(R.id.movieRatingInsideMovieDetailsFragment);

        /*find columns from the database*/
        int movieTitleCol = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIE_TITLE);
        int moviePosterCol = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIE_POSTER_URL);
        int movieRatingCol = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIE_RATING);
        int movieReleaseDateCol = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE);
        int movieFavoriteStatusCol = cursor.getColumnIndex(MoviesEntry.COLUMN_FAVORITE_STATUS);
        final int movieIdCol = cursor.getColumnIndex(MoviesEntry.COLUMN_MOVIE_ID);

                /*Retrieve the data from the database*/
        String title = cursor.getString(movieTitleCol);
        String posterUrl = cursor.getString(moviePosterCol);
        float rating = cursor.getFloat(movieRatingCol);
        String releaseDate = cursor.getString(movieReleaseDateCol);
        int favoriteStatus = cursor.getInt(movieFavoriteStatusCol);
        final int movieId = cursor.getInt(movieIdCol);

        if (titleText != null) {
            titleText.setText(title);
        }
        if (simpleRatingBar != null) {
            simpleRatingBar.setRating((float) (rating) / 2);
        }
        if (posterImage != null) {
            String url = "https://image.tmdb.org/t/p/w500/" + posterUrl.toString();
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.posterplaceholder)
                    .into(posterImage);
        }
        if (releaseDateText!=null){
            releaseDateText.setText(releaseDate);
        }
        if (favoriteStatusImage!=null){
            /*set the movie as favorite only if its ID is not zero*/
            if (favoriteStatus>0){
                favoriteStatusImage.setImageResource(R.drawable.starred);
            }
        }
        favoriteStatusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteStatusImage.setImageResource(R.drawable.unstarred);
                String selection = MoviesEntry.COLUMN_MOVIE_ID + "=?";
                String[] selectionArgs = new String[]{String.valueOf(movieId)};
                /*Delete the movie as it's already favorited*/
                int rowsDeleted = context.getContentResolver().delete(MoviesEntry.CONTENT_URI, selection, selectionArgs);
            }
        });
    }
}