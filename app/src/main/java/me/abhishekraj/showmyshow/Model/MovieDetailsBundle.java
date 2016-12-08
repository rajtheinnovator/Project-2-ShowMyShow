package me.abhishekraj.showmyshow.Model;

import java.util.ArrayList;

import me.abhishekraj.showmyshow.Model.Movie;
import me.abhishekraj.showmyshow.Model.Review;
import me.abhishekraj.showmyshow.Model.Video;

/**
 * Created by ABHISHEK RAJ on 12/2/2016.
 */

public class MovieDetailsBundle {
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;
    public Movie mMovie;

    /*
    * Create an empty constructor so that no NullPontException occur during the JSON parsing
    */
    public MovieDetailsBundle(){
    }
    public MovieDetailsBundle(ArrayList review, ArrayList video, Movie movie){
        mReview = review;
        mVideo = video;
        mMovie = movie;
    }

    public void setReviewArrayList(ArrayList<Review> reviews){mReview = reviews;}
    public void setVideoArrayList(ArrayList<Video> video){mVideo = video;}
    public void setMovie(Movie movie){mMovie = movie;}

    public ArrayList<Review> getReviewArrayList(){return mReview;}
    public ArrayList<Video> getVideoArrayList(){return mVideo;}
    public Movie getMovie(){return mMovie;}
}
