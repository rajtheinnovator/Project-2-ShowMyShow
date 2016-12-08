package me.abhishekraj.showmyshow.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.abhishekraj.showmyshow.Model.Movie;
import me.abhishekraj.showmyshow.Fragment.MovieDetailsFragment;
import me.abhishekraj.showmyshow.R;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle movieDetails = new Bundle();

        /**get the Movie's Object from the parent Activity**/
        Movie movie = getIntent().getParcelableExtra("movie");
        movieDetails.putParcelable("movie", movie);
        if (savedInstanceState == null) {
            MovieDetailsFragment defaultMovieFragment = new MovieDetailsFragment();
            defaultMovieFragment.setArguments(movieDetails);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerMovieDetailActivity, defaultMovieFragment)
                    .commit();

        }
    }
}