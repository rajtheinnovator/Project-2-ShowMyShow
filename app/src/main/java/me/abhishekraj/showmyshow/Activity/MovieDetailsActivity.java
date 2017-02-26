package me.abhishekraj.showmyshow.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.abhishekraj.showmyshow.R;
import me.abhishekraj.showmyshow.fragment.MovieDetailsFragment;
import me.abhishekraj.showmyshow.model.movie.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set the content of the activity to use the activity_tv_show_details.xml layout file */
        setContentView(R.layout.activity_movie_details);
        Bundle movieDetails = new Bundle();

        /**get the movie's Object from the parent activity**/
        Movie movie = getIntent().getParcelableExtra("movie");
        int position = Integer.parseInt(getIntent().getExtras().getString("position"));
        movieDetails.putParcelable("movie", movie);
        movieDetails.putString("position", String.valueOf(position));
        Log.v("mytag", "String.valueOf(position) is :"+String.valueOf(position));
        //movieDetails.putString("myBundle", getIntent().getStringExtra("movieDetails"));
        Intent intent = getIntent();
        Uri mCurrentMovieUri = intent.getData();
        movieDetails.putString("currentMovieUri", mCurrentMovieUri.toString());

        /* Check for pre-existing instances of fragments(here explicitly check for savedInstance)
        and then begin fragment transaction accordingly */
        if (savedInstanceState == null) {
            MovieDetailsFragment defaultMovieFragment = new MovieDetailsFragment();
            defaultMovieFragment.setArguments(movieDetails);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerMovieDetailActivity, defaultMovieFragment)
                    .commit();
        }
    }
}