package me.abhishekraj.showmyshow.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.abhishekraj.showmyshow.Fragment.MoviePosterFragment;
import me.abhishekraj.showmyshow.Fragment.TvShowsFragment;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class MainActivityPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Movies", "TV SHOWS"};
    private Context context;

    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MoviePosterFragment();
        } else {
            return new TvShowsFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}