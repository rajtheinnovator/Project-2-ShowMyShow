package me.abhishekraj.showmyshow.Adapter.MoviePosterAdapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.abhishekraj.showmyshow.Fragment.MoviePosterFragment;
import me.abhishekraj.showmyshow.Fragment.TvShowsPosterFragment;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class MainActivityPagerAdapter extends FragmentPagerAdapter {
    /* Total number of pages/fragments in the MainActivity */
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Movies", "TV SHOWS"};
    private Context context;

    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /* set the position of fragments */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MoviePosterFragment();
        } else {
            return new TvShowsPosterFragment();
        }
    }

    /*set the title for the fragment*/
    @Override
    public CharSequence getPageTitle(int position) {
        /* Generate title based on item position */
        return tabTitles[position];
    }

    /* get total fragment count for the Activity */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}