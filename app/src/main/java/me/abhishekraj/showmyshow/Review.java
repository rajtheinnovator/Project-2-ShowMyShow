package me.abhishekraj.showmyshow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 12/1/2016.
 */

public class Review implements Parcelable {
    /**
     * Reviews of the movie
     */
    private String mAuthor;
    private String mURL;
    private String mContent;

    /**
     * Create an empty constructor so that an empty movie's object can be referenced
     * in the MainActivity for storing movie's info
     */
    public Review() {
    }

    /**
     * Constructs a new {@link Movie} for movie's details Intent
     *
     * @param author is the title of the review
     */
//Used to store all the info of the selected movie on RecyclerView item click on the MainActivity
    public Review(String author, String content, String url) {
        mAuthor = author;
        mURL = url;
        mContent = content;

    }

    /**
     * The Getters Methods
     */
    public String getMovieReviewAuthor() {
        return mAuthor;
    }

    public String getMovieReviewContent() {
        return mContent;
    }

    public String getMovieReviewURL() {
        return mURL;
    }

    /**
     * Make Parcelabe Work Through these methods
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mAuthor);
        out.writeString(mContent);
        out.writeString(mURL);
    }

    private Review(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
        mURL = in.readString();
    }

    /*
    After implementing the `Parcelable` interface, we need to create the
    `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    Notice how it has our class specified as its type.
    */
    public static final Parcelable.Creator<Review> CREATOR
            = new Parcelable.Creator<Review>() {

        // This simply calls our new constructor (typically private) and
// passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}