package me.abhishekraj.showmyshow;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ABHISHEK RAJ on 11/26/2016.
 */

public class MovieDetailsFragment extends Fragment {

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    TextView textView;
    ImageView imageView;
    ImageView imageViewBackdrop;
    CollapsingToolbarLayout collapsingToolbar;
    String posterURL;
    String backdropURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle bundle = getArguments();
        textView = (TextView) rootView.findViewById(R.id.movie_detail_title_text_view);
        imageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view);
        imageViewBackdrop = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view_backdrop);

        /* As there is no actionbar defined in the Style for this activity, so creating one toolbar for this Fragment
        *  which will act as an actionbar after scrolling-up, referenced from StackOverflow link
        *  @link http://stackoverflow.com/a/32858049/5770629
        */
        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Creating a collapsing toolbar, defined in the fragment_movie_details.xml  */
        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        if ((bundle != null)) {
            Movie movie = getArguments().getParcelable("movie");
            textView.setText(movie.getMovieTitle());
            posterURL = UrlsAndConstants.DefaultQuery.BASE_IMAGE_URL + movie.getMoviePosterPath();
            backdropURL = UrlsAndConstants.DefaultQuery.BASE_IMAGE_URL + movie.getMovieBackdropPath();
            collapsingToolbar.setTitle(movie.getMovieTitle());
            Picasso.with(getContext())
                    .load(posterURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            Picasso.with(getContext())
                    .load(backdropURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageViewBackdrop);
        }
        return rootView;
    }
}
