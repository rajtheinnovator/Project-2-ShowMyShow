package me.abhishekraj.showmyshow.model.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 12/2/2016.
 */

public class Video implements Parcelable {

    /*
    After implementing the `Parcelable` interface, we need to create the
    `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    Notice how it has our class specified as its type.
    */
    public static final Parcelable.Creator<Video> CREATOR
            = new Parcelable.Creator<Video>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
    /**
     * Reviews of the movie
     */
    private String mId;
    private String mKey;
    private String mName;
    private String mSize;
    private String mType;

    /**
     * Create an empty constructor so that an empty movie's object can be referenced
     * in the MainActivity for storing movie's info
     */
    public Video() {
    }

    /**
     * Constructs a new {@link Movie} for movie's details Intent
     *
     * @param id is the title of the review
     */
    /* Used to store all the info of the selected movie on RecyclerView item click on the MainActivity */
    public Video(String id, String key, String name, String size, String type) {
        mId = id;
        mKey = key;
        mName = name;
        mSize = size;
        mType = type;

    }

    private Video(Parcel in) {
        mId = in.readString();
        mKey = in.readString();
        mName = in.readString();
        mSize = in.readString();
        mType = in.readString();
    }

    /**
     * The Getters Methods
     */
    public String getMovieVideoId() {
        return mId;
    }

    public String getMovieVideoKey() {
        return mKey;
    }

    public String getMovieVideoName() {
        return mName;
    }

    public String getMovieVideoSize() {
        return mSize;
    }

    public String getMovieVideoType() {
        return mType;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mId);
        out.writeString(mKey);
        out.writeString(mName);
        out.writeString(mSize);
        out.writeString(mType);
    }

    /**
     * Make Parcelabe Work Through these methods
     */
    @Override
    public int describeContents() {
        return 0;
    }


}