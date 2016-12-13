package me.abhishekraj.showmyshow.Model.TvShow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 12/12/2016.
 */

public class Seasons implements Parcelable {

    /*
    After implementing the `Parcelable` interface, we need to create the
`   Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    Notice how it has our class specified as its type.
    */
    public static final Parcelable.Creator<me.abhishekraj.showmyshow.Model.TvShow.Seasons> CREATOR
            = new Parcelable.Creator<me.abhishekraj.showmyshow.Model.TvShow.Seasons>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public me.abhishekraj.showmyshow.Model.TvShow.Seasons createFromParcel(Parcel in) {
            return new me.abhishekraj.showmyshow.Model.TvShow.Seasons(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public me.abhishekraj.showmyshow.Model.TvShow.Seasons[] newArray(int size) {
            return new me.abhishekraj.showmyshow.Model.TvShow.Seasons[size];
        }
    };
    /**
     * Seasons of the TvShow
     */
    private String mSeasonAirDate;
    private int mSeasonEpisodeCount;
    private int mSeasonId;
    private String mSeasonPosterPath;
    private int mSeasonNumber;

    /**
     * Create an empty constructor so that an empty tvShow's object can be referenced
     * in the MainActivity for storing tvShow's info, if empty object is needed for instantiation purpose
     */
    public Seasons() {
    }

    /**
     * Constructs a new {@link TvShow} for tvShow's details Intent
     *
     * @param airDate is the air date of the Season
     */
//Used to store all the info of the selected tvShow on RecyclerView item click on the MainActivity
    public Seasons(String airDate, int episodeCount, int seasonId, String posterPath, int seasonNumber) {
        mSeasonAirDate = airDate;
        mSeasonEpisodeCount = episodeCount;
        mSeasonId = seasonId;
        mSeasonPosterPath = posterPath;
        mSeasonNumber = seasonNumber;
    }

    private Seasons(Parcel in) {
        mSeasonAirDate = in.readString();
        mSeasonEpisodeCount = in.readInt();
        mSeasonId = in.readInt();
        mSeasonPosterPath = in.readString();
        mSeasonNumber = in.readInt();
    }

    /**
     * The Getters Methods
     */

    public String getTvShowSeasonAirDate() {
        return mSeasonAirDate;
    }

    public int getTvShowSeasonEpisodeCount() {
        return mSeasonEpisodeCount;
    }

    public int getTvShowSeasonId() {
        return mSeasonId;
    }

    public String getTvShowSeasonPosterPath() {
        return mSeasonPosterPath;
    }

    public int getTvShowSeasonSeasonNumber() {
        return mSeasonNumber;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mSeasonAirDate);
        out.writeInt(mSeasonEpisodeCount);
        out.writeInt(mSeasonId);
        out.writeString(mSeasonPosterPath);
        out.writeInt(mSeasonNumber);
    }

    /**
     * Make Parcelabe Work Through these methods
     */
    @Override
    public int describeContents() {
        return 0;
    }
}