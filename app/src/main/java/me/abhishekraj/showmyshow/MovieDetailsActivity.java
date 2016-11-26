package me.abhishekraj.showmyshow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        /**get the Movie's Object from the parent Activity**/
        Movie movie = getIntent().getParcelableExtra("movie");

        newString = movie.getMovieTitle();
        Toast.makeText(getApplicationContext(), "" + newString, Toast.LENGTH_SHORT).show();
        //getStringExtras("movieId");
        TextView textView = (TextView) findViewById(R.id.movie_detail_title_text_view);
        textView.setText(newString);
        this.setTitle(newString);
        ImageView imageView = (ImageView) findViewById(R.id.movie_detail_title_image_view);
        String url = "https://image.tmdb.org/t/p/w500/" + movie.getMoviePosterPath().toString();

        Picasso.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);

    }
}