package me.abhishekraj.showmyshow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ABHISHEK RAJ on 11/15/2016.
 */

public class Movie implements Parcelable {
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
    /*Variables for handling recycler view click*/

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
    public Movie(String movieTitle, int id, String posterPath) {
        mMovieTitle = movieTitle;
        mMoviePosterPath = posterPath;
        mMovieId = id;

    }

    /**
     * Constructs a new {@link Movie} for movie's details Intent
     *
     * @param movieTitle   is the title of the book
     * @param posterPath   is the title of the book
     * @param id           is the title of the book
     * @param releaseDate  is the title of the book
     * @param voteCount    is the title of the book
     * @param voteAverage  is the title of the book
     * @param plotSynopsis is the title of the book
     * @param trailerUrl   is the title of the book
     * @param review       is the title of the book
     */
    //Used to store all the info of the selected movie on RecyclerView item click on the MainActivity
    public Movie(String movieTitle, String posterPath, String id, String releaseDate,
                 int voteCount, int voteAverage, String plotSynopsis, String trailerUrl, String review) {
        mMovieTitle = movieTitle;

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
    }

    private Movie(Parcel in) {
        mMovieTitle = in.readString();
        mMovieId = in.readInt();
        mMoviePosterPath = in.readString();
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