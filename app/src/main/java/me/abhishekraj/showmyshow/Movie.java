package me.abhishekraj.showmyshow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 11/15/2016.
 */

public class Movie implements Parcelable {

    /*
    * Codes referenced from the link, @link: "https://guides.codepath.com/android/Using-Parcelable"
    */
    /**
     * Title of the movie
     */
    private String mMovieTitle;

    /**
     * posterPath of the movie
     */
    private String mMoviePosterPath;

    /**
     * id of the movie
     */
    private int mMovieId;
    /*Variables needed when handling recycler view is clicked*/

    /**
     * BackdropPosterPath of the movie
     */
    private String mMovieBackdropPath;

    private double mMovieVoteCount;
    private String mMovieOverview;
    private double mMovieVoteAverage;
    private String mMovieOriginalTitle;
    private double mMoviePopularity;
    private String mMovieReleaseDate;
    /**
     * Create an empty constructor so that an empty movie's object can be referenced
     * in the MainActivity for storing movie's info
     */
    public Movie() {
    }

    /**
     * Constructs a new {@link Movie}.
     *
     * @param movieTitle is the title of the movie
     * @param posterPath is the posterPath of the movie
     * @param id         is the id of the movie
     */
    public Movie(String movieTitle, int id, String posterPath, String overview, double voteCount, String originalTitle,
                 double voteAverage, double popularity, String backdropPath, String movieReleaseDate) {
        mMovieTitle = movieTitle;
        mMoviePosterPath = posterPath;
        mMovieId = id;
        mMovieOverview = overview;
        mMovieVoteCount = voteCount;
        mMovieOriginalTitle = originalTitle;
        mMovieVoteAverage = voteAverage;
        mMoviePopularity = popularity;
        mMovieBackdropPath = backdropPath;
        mMovieReleaseDate = movieReleaseDate;

    }
    /**
     * The Setters Methods
     */
    public void setMovieTitle(String title) {
        mMovieTitle = title;
    }

    public void setMovieId(int id) {
        mMovieId = id;
    }

    public void setMoviePosterPath(String path) {
        mMoviePosterPath = path;
    }


    /**
     * The Getters Methods
     */
    public String getMovieTitle() {
        return mMovieTitle;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public String getMoviePosterPath() {
        return mMoviePosterPath;
    }

    public String getMovieBackdropPath() {
        return mMovieBackdropPath;
    }

    public String getMovieOverview() {
        return mMovieOverview;
    }

    public double getMovieVoteCount() {
        return mMovieVoteCount;
    }

    public String getMovieOriginalTitle() {
        return mMovieOriginalTitle;
    }

    public double getMovieVoteAverage() {
        return mMovieVoteAverage;
    }

    public double getMoviePopularity() {
        return mMoviePopularity;
    }

    public String getMovieReleaseDate(){return mMovieReleaseDate;}



    /**
     * Make Parcelabe Work Through these methods
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mMovieTitle);
        out.writeInt(mMovieId);
        out.writeString(mMoviePosterPath);
        out.writeString(mMovieOverview);
        out.writeDouble(mMovieVoteCount);
        out.writeString(mMovieOriginalTitle);
        out.writeDouble(mMovieVoteAverage);
        out.writeDouble(mMoviePopularity);
        out.writeString(mMovieBackdropPath);
        out.writeString(mMovieReleaseDate);
    }

    private Movie(Parcel in) {
        mMovieTitle = in.readString();
        mMovieId = in.readInt();
        mMoviePosterPath = in.readString();
        mMovieOverview = in.readString();
        mMovieVoteCount = in.readDouble();
        mMovieOriginalTitle = in.readString();
        mMovieVoteAverage = in.readDouble();
        mMoviePopularity = in.readDouble();
        mMovieBackdropPath = in.readString();
        mMovieReleaseDate = in.readString();
    }

    /*
    After implementing the `Parcelable` interface, we need to create the
    `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    Notice how it has our class specified as its type.
    */
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}