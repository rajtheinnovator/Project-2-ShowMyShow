package me.abhishekraj.showmyshow.Model.TvShow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 12/11/2016.
 */

public class Credits implements Parcelable {

    /*
    After implementing the `Parcelable` interface, we need to create the
    `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    Notice how it has our class specified as its type.
    */
    public static final Parcelable.Creator<me.abhishekraj.showmyshow.Model.TvShow.Credits> CREATOR
            = new Parcelable.Creator<me.abhishekraj.showmyshow.Model.TvShow.Credits>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public me.abhishekraj.showmyshow.Model.TvShow.Credits createFromParcel(Parcel in) {
            return new me.abhishekraj.showmyshow.Model.TvShow.Credits(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public me.abhishekraj.showmyshow.Model.TvShow.Credits[] newArray(int size) {
            return new me.abhishekraj.showmyshow.Model.TvShow.Credits[size];
        }
    };
    /**
     * Credits of the TvShow
     */
    private String mCharacter;
    private String mCreditId;
    private int mId;
    private String mName;
    private String mProfilePath;

    /**
     * Create an empty constructor so that an empty tvShow's object can be referenced
     * in the MainActivity for storing tvShow's info, if empty object is needed for instantiation purpose
     */
    public Credits() {
    }

    /**
     * Constructs a new {@link TvShow} for tvShow's details Intent
     *
     * @param character is the character of the actor/cast
     */
    /* Used to store all the info of the selected tvShow on RecyclerView item click on the MainActivity */
    public Credits(String character, String creditId, int id, String name, String profilePath) {
        mCharacter = character;
        mCreditId = creditId;
        mId = id;
        mName = name;
        mProfilePath = profilePath;
    }

    private Credits(Parcel in) {
        mCharacter = in.readString();
        mCreditId = in.readString();
        mId = in.readInt();
        mName = in.readString();
        mProfilePath = in.readString();
    }

    /**
     * The Getters Methods
     */

    public String getTvShowCastCharacter() {
        return mCharacter;
    }

    public String getTvShowCastCreditId() {
        return mCreditId;
    }

    public int getTvShowCastId() {
        return mId;
    }

    public String getTvShowCastName() {
        return mName;
    }

    public String getTvShowCastProfilePath() {
        return mProfilePath;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
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