package me.abhishekraj.showmyshow.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.abhishekraj.showmyshow.Fragment.TvShowDetailsFragment;
import me.abhishekraj.showmyshow.Model.TvShow.TvShow;
import me.abhishekraj.showmyshow.R;

public class TvShowDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);
        Bundle tvShowDetails = new Bundle();

        /**get the TvShow's Object from the parent Activity**/
        TvShow tvShow = getIntent().getParcelableExtra("tvShow");
        tvShowDetails.putParcelable("tvShow", tvShow);
        if (savedInstanceState == null) {
            TvShowDetailsFragment defaultTvShowFragment = new TvShowDetailsFragment();
            defaultTvShowFragment.setArguments(tvShowDetails);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerTvShowDetailActivity, defaultTvShowFragment)
                    .commit();
        }
    }
}