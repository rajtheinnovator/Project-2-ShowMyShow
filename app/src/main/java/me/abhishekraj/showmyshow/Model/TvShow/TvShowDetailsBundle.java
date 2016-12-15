package me.abhishekraj.showmyshow.Model.TvShow;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowDetailsBundle {

    /* ArrayLists for holding TvShowdetails */
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;
    public ArrayList<Credits> mCredits;
    public ArrayList<Seasons> mSeasons;

    /* Create a TvShow Object for handling null point exception cases */
    public TvShow mTvShow;

    /*
    * Create an empty constructor so that no NullPontException occur during the JSON parsing
    */
    public TvShowDetailsBundle() {
        mTvShow = new TvShow();
        mReview = new ArrayList<>();
        mCredits = new ArrayList<>();
        mSeasons = new ArrayList<>();
        mVideo = new ArrayList<>();
    }

    public TvShowDetailsBundle(ArrayList review, ArrayList video, TvShow movie, ArrayList credits, ArrayList seasons) {
        mReview = review;
        mVideo = video;
        mTvShow = movie;
        mCredits = credits;
        mSeasons = seasons;
    }

    /* The getter smethods */
    public ArrayList<Review> getReviewArrayList() {
        return mReview;
    }

    /* The setters methods */
    public void setReviewArrayList(ArrayList<Review> reviews) {
        mReview = reviews;
    }

    public ArrayList<Video> getVideoArrayList() {
        return mVideo;
    }

    public void setVideoArrayList(ArrayList<Video> video) {
        mVideo = video;
    }

    public ArrayList<Credits> getCreditsArrayList() {
        return mCredits;
    }

    public void setCreditsArrayList(ArrayList<Credits> credits) {
        mCredits = credits;
    }

    public ArrayList<Seasons> getSeasonsArrayList() {
        return mSeasons;
    }

    public void setSeasonsArrayList(ArrayList<Seasons> seasons) {
        mSeasons = seasons;
    }

    public TvShow getTvShow() {
        return mTvShow;
    }

    public void setTvShow(TvShow tvShow) {
        mTvShow = tvShow;
    }
}