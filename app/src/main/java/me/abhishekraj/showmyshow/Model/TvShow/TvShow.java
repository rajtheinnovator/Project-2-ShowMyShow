package me.abhishekraj.showmyshow.Model.TvShow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShow implements Parcelable {

    /*
    * Codes referenced from the link, @link: "https://guides.codepath.com/android/Using-Parcelable"
    */

    /*Variables needed when handling recycler view clicked*/

    /**
     * Make Parcelabe Work Through these methods
     */
    /*
    After implementing the `Parcelable` interface, we need to create the
    `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    Notice how it has our class specified as its type.
    */
    public static final Parcelable.Creator<TvShow> CREATOR
            = new Parcelable.Creator<TvShow>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
    /**
     * BackdropPosterPath of the tvShow
     */
    private String mTvShowBackdropPath;
    private int mTvShowRunTime;
    private String mTvShowFirstAirDate;
    /* id of the tvShow */
    private int mTvShowId;
    private String mTvShowLastAirDate;
    /* Name of the tvShow */
    private String mTvShowName;
    private int mTvShowNoOfEpisodes;
    private int mTvShowNoOfSeasons;
    private String mTvShowOriginalName;
    private String mTvShowOverview;
    private double mTvShowPopularity;
    /* posterPath of the tvShow */
    private String mTvShowPosterPath;
    private String mTvShowType;
    private double mTvShowVoteAverage;
    private int mTvShowVoteCount;

    /**
     * Create an empty constructor so that an empty tvShow's object can be referenced
     * in the MainActivity for storing tvShow's info
     */
    public TvShow() {
    }
    /**
     * Constructs a new {@link TvShow}.
     *
     * @param tvShowName       is the name of the tvShow
     * @param tvShowPosterPath is the posterPath of the tvShow
     * @param tvShowId         is the id of the tvShow
     */
    public TvShow(String tvShowPosterPath, double tvShowPopularity, int tvShowId, String tvShowBackdropPath,
                  double tvShowVoteAverage, String tvShowOverview, String tvShowFirstAirDate,
                  int tvShowVoteCount, String tvShowName, String tvShowOriginalName) {
        mTvShowPosterPath = tvShowPosterPath;
        mTvShowPopularity = tvShowPopularity;
        mTvShowId = tvShowId;
        mTvShowBackdropPath = tvShowBackdropPath;
        mTvShowVoteAverage = tvShowVoteAverage;
        mTvShowOverview = tvShowOverview;
        mTvShowFirstAirDate = tvShowFirstAirDate;
        mTvShowVoteCount = tvShowVoteCount;
        mTvShowName = tvShowName;
        mTvShowOriginalName = tvShowOriginalName;
    }

    /*For movie details*/
    public TvShow(int runTime, String lastAirDate, int numberOfEpisodes, int numberOfSeasons, String type) {
        mTvShowRunTime = runTime;
        mTvShowLastAirDate = lastAirDate;
        mTvShowNoOfEpisodes = numberOfEpisodes;
        mTvShowNoOfSeasons = numberOfSeasons;
        mTvShowType = type;
    }

    private TvShow(Parcel in) {
        mTvShowPosterPath = in.readString();
        mTvShowPopularity = in.readDouble();
        mTvShowId = in.readInt();
        mTvShowBackdropPath = in.readString();
        mTvShowVoteAverage = in.readDouble();
        mTvShowOverview = in.readString();
        mTvShowFirstAirDate = in.readString();
        mTvShowVoteCount = in.readInt();
        mTvShowName = in.readString();
        mTvShowOriginalName = in.readString();
        mTvShowRunTime = in.readInt();
        mTvShowLastAirDate = in.readString();
        mTvShowNoOfEpisodes = in.readInt();
        mTvShowNoOfSeasons = in.readInt();
        mTvShowType = in.readString();
    }

    /**
     * The Getters Methods
     */
    public String getTvShowPosterPath() {
        return mTvShowPosterPath;
    }

    /**
     * The Setters Methods
     */
    public void setTvShowPosterPath(String path) {
        mTvShowPosterPath = path;
    }

    public double getTvShowPopularity() {
        return mTvShowPopularity;
    }

    public void setTvShowPopularity(double popularity) {
        mTvShowPopularity = popularity;
    }

    public int getTvShowId() {
        return mTvShowId;
    }

    public void setTvShowId(int id) {
        mTvShowId = id;
    }

    public String getTvShowBackdropPath() {
        return mTvShowBackdropPath;
    }

    public void setTvShowBackdropPath(String backdropPath) {
        mTvShowBackdropPath = backdropPath;
    }

    public double getTvShowVoteAverage() {
        return mTvShowVoteAverage;
    }

    public void setTvShowVoteAverage(double voteAverage) {
        mTvShowVoteAverage = voteAverage;
    }

    public String getTvShowOverview() {
        return mTvShowOverview;
    }

    public void setTvShowOverview(String overview) {
        mTvShowOverview = overview;
    }

    public String getTvShowFirstAirDate() {
        return mTvShowFirstAirDate;
    }

    public void setTvShowFirstAirDate(String firstAirDate) {
        mTvShowFirstAirDate = firstAirDate;
    }

    public int getTvShowVoteCount() {
        return mTvShowVoteCount;
    }

    public void setTvShowVoteCount(int voteCount) {
        mTvShowVoteCount = voteCount;
    }

    public String getTvShowName() {
        return mTvShowName;
    }

    public void setTvShowName(String name) {
        mTvShowName = name;
    }

    public String getTvShowOriginalName() {
        return mTvShowOriginalName;
    }

    public void setTvShowOriginalName(String originalName) {
        mTvShowOriginalName = originalName;
    }

    public int getTvShowRuntime() {
        return mTvShowRunTime;
    }

    public void setTvShowTvShowRuntime(int runtime) {
        mTvShowRunTime = runtime;
    }

    public int getTvShowNumberEpisodes() {
        return mTvShowNoOfEpisodes;
    }

    public void setTvShowTvShowNumberOfEpisodes(int numberOfEpisodes) {
        mTvShowNoOfEpisodes = numberOfEpisodes;
    }

    public int getTvShowNumberOfSeasons() {
        return mTvShowNoOfSeasons;
    }

    public void setTvShowTvShowNumberOfSeasons(int numberOfSeasons) {
        mTvShowNoOfSeasons = numberOfSeasons;
    }

    public String getTvShowType() {
        return mTvShowType;
    }

    public void setTvShowTvShowType(String type) {
        mTvShowType = type;
    }

    public String getTvShowLastAirDate() {
        return mTvShowLastAirDate;
    }

    public void setTvShowTvShowLastAirDate(String lastAirDate) {
        mTvShowLastAirDate = lastAirDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTvShowPosterPath);
        out.writeDouble(mTvShowPopularity);
        out.writeInt(mTvShowId);
        out.writeString(mTvShowBackdropPath);
        out.writeDouble(mTvShowVoteAverage);
        out.writeString(mTvShowOverview);
        out.writeString(mTvShowFirstAirDate);
        out.writeInt(mTvShowVoteCount);
        out.writeString(mTvShowName);
        out.writeString(mTvShowOriginalName);
    }

}