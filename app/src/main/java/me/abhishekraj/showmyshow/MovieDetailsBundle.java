package me.abhishekraj.showmyshow;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 12/2/2016.
 */

public class MovieDetailsBundle {
    public ArrayList<Review> mReview;
    public ArrayList<Video> mVideo;

    /*
    * Create an empty constructor so that no NullPontException occur during the JSON parsing
    */
    public MovieDetailsBundle(){
    }
    public MovieDetailsBundle(ArrayList review, ArrayList video){
        mReview = review;
        mVideo = video;
    }

    public void setReviewArrayList(ArrayList<Review> reviews){mReview = reviews;}
    public void setVideoArrayList(ArrayList<Video> video){mVideo = video;}

    public ArrayList<Review> getReviewArrayList(){return mReview;}
    public ArrayList<Video> getVideoArrayList(){return mVideo;}
}
