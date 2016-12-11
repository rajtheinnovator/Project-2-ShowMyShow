package me.abhishekraj.showmyshow.Model.TvShow;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowDetailsBundle {
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;
    public ArrayList<Credits> mCredits;
    public TvShow mTvShow;

    /*
    * Create an empty constructor so that no NullPontException occur during the JSON parsing
    */
    public TvShowDetailsBundle() {
    }

    public TvShowDetailsBundle(ArrayList review, ArrayList video, TvShow movie, ArrayList credits) {
        mReview = review;
        mVideo = video;
        mTvShow = movie;
        mCredits = credits;
    }

    public ArrayList<Review> getReviewArrayList() {
        return mReview;
    }

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

    public TvShow getTvShow() {
        return mTvShow;
    }

    public void setTvShow(TvShow tvShow) {
        mTvShow = tvShow;
    }
}