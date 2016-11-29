package me.abhishekraj.showmyshow;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle bundle = getArguments();
        textView = (TextView) rootView.findViewById(R.id.movie_detail_title_text_view);
        imageView = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view);
        imageViewBackdrop = (ImageView) rootView.findViewById(R.id.movie_detail_title_image_view_backdrop);
        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);


        if ((bundle != null)) {
            Movie movie = getArguments().getParcelable("movie");
            textView.setText(movie.getMovieTitle().toString());
            String url = "https://image.tmdb.org/t/p/w500/" + movie.getMoviePosterPath().toString();
            collapsingToolbar.setTitle(movie.getMovieTitle().toString());
            Picasso.with(getContext())
                    .load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            Picasso.with(getContext())
                    .load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageViewBackdrop);

//            ArrayList<Movie> movieArrayList = new ArrayList<>();
//            movieArrayList.add(movie);
//
//            // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
//            // adapter knows how to create list items for each item in the list.
//            MovieDetailsAdapter adapter = new MovieDetailsAdapter(getActivity(), movieArrayList);
//
//            // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
//            // There should be a {@link ListView} with the view ID called list, which is declared in the
//            // word_list.xml layout file.
//            ListView listView = (ListView) rootView.findViewById(R.id.list_item);
//
//            // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
//            // {@link ListView} will display list items for each {@link Word} in the list.
//            listView.setAdapter(adapter);
        }

        return rootView;
    }
}
