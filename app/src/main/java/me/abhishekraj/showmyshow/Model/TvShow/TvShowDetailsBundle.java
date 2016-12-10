package me.abhishekraj.showmyshow.Model.TvShow;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowDetailsBundle {
    public ArrayList<TvShow> mTvShowArrayList;
    public TvShow mTvShow;

    /*
    * Create an empty constructor so that no NullPontException occur during the JSON parsing
    */
    public TvShowDetailsBundle() {
    }

    public TvShowDetailsBundle(ArrayList tvShowArrayList, TvShow tvShow) {
        mTvShowArrayList = tvShowArrayList;
        mTvShow = tvShow;
    }

    public ArrayList<TvShow> getTvShowArrayList() {
        return mTvShowArrayList;
    }

    public void setTvShowArrayList(ArrayList<TvShow> tvShows) {
        mTvShowArrayList = tvShows;
    }

    public TvShow getTvShow() {
        return mTvShow;
    }

    public void setTvShow(TvShow tvShow) {
        mTvShow = tvShow;
    }
}