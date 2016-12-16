package me.abhishekraj.showmyshow.model.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 12/9/2016.
 */

public class Credits implements Parcelable {

    /*
    After implementing the `Parcelable` interface, we need to create the
    `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    Notice how it has our class specified as its type.
    */
    public static final Parcelable.Creator<Credits> CREATOR
            = new Parcelable.Creator<Credits>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Credits createFromParcel(Parcel in) {
            return new Credits(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Credits[] newArray(int size) {
            return new Credits[size];
        }
    };
    /**
     * Reviews of the movie
     */
    private int mCastId;
    private String mCharacter;
    private String mCreditId;
    private int mId;
    private String mName;
    private String mProfilePath;

    /**
     * Create an empty constructor so that an empty movie's object can be referenced
     * in the MainActivity for storing movie's info, if empty object is needed for instantiation purpose
     */
    public Credits() {
    }

    /**
     * Constructs a new {@link Movie} for movie's details Intent
     *
     * @param castId is the castId of the review
     * @param character is the character of the review
     * @param creditId is the creditId of the review
     * @param id is the id of the review
     * @param name is the name of the review
     * @param profilePath is the profilePath of the review
     */
    /* Used to store all the info of the selected movie on RecyclerView item click on the MainActivity */
    public Credits(int castId, String character, String creditId, int id, String name, String profilePath) {
        mCastId = castId;
        mCharacter = character;
        mCreditId = creditId;
        mId = id;
        mName = name;
        mProfilePath = profilePath;
    }

    private Credits(Parcel in) {
        mCastId = in.readInt();
        mCharacter = in.readString();
        mCreditId = in.readString();
        mId = in.readInt();
        mName = in.readString();
        mProfilePath = in.readString();
    }

    /**
     * The Getters Methods
     */
    public int getMovieCastCastId() {
        return mCastId;
    }

    public String getMovieCastCharacter() {
        return mCharacter;
    }

    public String getMovieCastCreditId() {
        return mCreditId;
    }

    public int getMovieCastId() {
        return mId;
    }

    public String getMovieCastName() {
        return mName;
    }

    public String getMovieCastProfilePath() {
        return mProfilePath;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mCastId);
        out.writeString(mCharacter);
        out.writeString(mCreditId);
        out.writeInt(mId);
        out.writeString(mName);
        out.writeString(mProfilePath);
    }

    /**
     * Make Parcelabe Work Through these methods
     */
    @Override
    public int describeContents() {
        return 0;
    }
}