package me.abhishekraj.showmyshow.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.abhishekraj.showmyshow.Fragment.MoviePosterFragment;
import me.abhishekraj.showmyshow.R;


public class MainActivity extends AppCompatActivity {
    /*
    * The codes in this project has been referenced from the SunshineApp of the Udacity Android Course
    * and the code related to Loaders has been referenced from my own work under the Udacity AndroidBasicsNanodegree
    * where I made the News app, link to @link: "https://github.com/rajtheinnovator/NewsToday",
    * Also the code related to RecyclerView has been referenced from codepath tutorial, linked to
    * @link: "https://guides.codepath.com/android/using-the-recyclerview".
    * And the other code uses has been given their credit throughout the project as per the ethical
    * work flow culture and use of opensource projects
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerMainActivity, new MoviePosterFragment())
                    .commit();
        }
    }
}
