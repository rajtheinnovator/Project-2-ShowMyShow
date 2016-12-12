package me.abhishekraj.showmyshow.Model.Movie;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 12/2/2016.
 */

public class MovieDetailsBundle {
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;
    public ArrayList<Credits> mCredits;
    public Movie mMovie;

    /*
    * Create an empty constructor so that no NullPontException occur during the JSON parsing
    */
    public MovieDetailsBundle(){
        mReview = new ArrayList<>();
        mVideo = new ArrayList<>();
        mCredits = new ArrayList<>();
        mMovie = new Movie();
    }

    public MovieDetailsBundle(ArrayList review, ArrayList video, Movie movie, ArrayList credits) {
        mReview = review;
        mVideo = video;
        mMovie = movie;
        mCredits = credits;
    }

    public ArrayList<Review> getReviewArrayList() {
        return mReview;
    }

    public void setReviewArrayList(ArrayList<Review> reviews){mReview = reviews;}

    public ArrayList<Video> getVideoArrayList(){return mVideo;}

    public void setVideoArrayList(ArrayList<Video> video) {
        mVideo = video;
    }

    public ArrayList<Credits> getCreditsArrayList() {
        return mCredits;
    }

    public void setCreditsArrayList(ArrayList<Credits> credits) {
        mCredits = credits;
    }

    public Movie getMovie(){return mMovie;}

    public void setMovie(Movie movie) {
        mMovie = movie;
    }
}
