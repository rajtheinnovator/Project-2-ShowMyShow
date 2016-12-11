package me.abhishekraj.showmyshow.Model.TvShow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 12/11/2016.
 */

public class Review implements Parcelable {
    /*
    After implementing the `Parcelable` interface, we need to create the
    `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    Notice how it has our class specified as its type.
    */
    public static final Parcelable.Creator<me.abhishekraj.showmyshow.Model.TvShow.Review> CREATOR
            = new Parcelable.Creator<me.abhishekraj.showmyshow.Model.TvShow.Review>() {

        // This simply calls our new constructor (typically private) and
// passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public me.abhishekraj.showmyshow.Model.TvShow.Review createFromParcel(Parcel in) {
            return new me.abhishekraj.showmyshow.Model.TvShow.Review(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public me.abhishekraj.showmyshow.Model.TvShow.Review[] newArray(int size) {
            return new me.abhishekraj.showmyshow.Model.TvShow.Review[size];
        }
    };
    /**
     * Reviews of the tvShow
     */
    private String mAuthor;
    private String mURL;
    private String mContent;

    /**
     * Create an empty constructor so that an empty TvShow's object can be referenced
     * in the MainActivity for storing tvShow's info
     */
    public Review() {
    }

    /**
     * Constructs a new {@link TvShow} for tvShow's details Intent
     *
     * @param author is the title of the review
     */
//Used to store all the info of the selected tvShow on RecyclerView item click on the MainActivity
    public Review(String author, String content, String url) {
        mAuthor = author;
        mURL = url;
        mContent = content;

    }

    private Review(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
        mURL = in.readString();
    }

    /**
     * The Getters Methods
     */
    public String getTvShowReviewAuthor() {
        return mAuthor;
    }

    public String getTvShowReviewContent() {
        return mContent;
    }

    public String getTvShowReviewURL() {
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
}