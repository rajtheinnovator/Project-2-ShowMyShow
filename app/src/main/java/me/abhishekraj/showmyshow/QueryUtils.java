package me.abhishekraj.showmyshow;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 11/15/2016.
 */

public class QueryUtils {
    private static String movieTitle;
    private static int movieId;
    private static String moviePosterPath;
    private static String movieOverview;
    private static double movieVoteCount;
    private static String movieOriginalTitle;
    private static double movieVoteAverage;
    private static double moviePopularity;
    private static String movieBackdropPath;


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the GUARDIAN dataset and return an {@link Movie} ArrayList to represent a single Movie.
     */
    public static ArrayList<Movie> fetchMovieData(String requestUrl) {
        Log.v("############", "fetchMovieData called");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            //handle exception
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Movie> movies = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return movies;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            //handle exception
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        Log.v("############", "makeHttpRequest called");
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /*
            If the request was successful (response code 200),
            then read the input stream and parse the response.
            */
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                //handle exception
            }
        } catch (IOException e) {
            //handle exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Movie> extractFeatureFromJson(String jsonResponse) {
        Log.v("############", "extractFeatureFromJson called");
        Log.v("############", "jsonResponse" + jsonResponse);

        // Create an empty ArrayList that we can start adding movies to
        ArrayList<Movie> movies = new ArrayList<Movie>();

        /*
        Try to parse the received jsonResponse. If there's a problem with the way the JSON
        is formatted, a JSONException exception object will be thrown. Catch the exception
        so the app doesn't crash, and handle exception.
        */
        try {
            // Parse the jsonResponse string
            JSONObject movie_json_response = new JSONObject(jsonResponse);
            Log.v("############", "JSONObject is: " + movie_json_response.toString());
            if (movie_json_response.has("results")) {
                JSONArray resultsArray = movie_json_response.getJSONArray("results");
                if (resultsArray.length() > 0) {
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject movieDetail = resultsArray.getJSONObject(i);
                        if (movieDetail.has("title")) {
                            movieTitle = movieDetail.getString("title");
                        }
                        if (movieDetail.has("id")) {
                            movieId = movieDetail.getInt("id");
                        }
                        if (movieDetail.has("poster_path")) {
                            moviePosterPath = movieDetail.getString("poster_path");
                        }
                        if (movieDetail.has("overview")) {
                            movieOverview = movieDetail.getString("overview");
                        }
                        if (movieDetail.has("original_title")) {
                            movieOriginalTitle = movieDetail.getString("original_title");
                        }
                        if (movieDetail.has("backdrop_path")) {
                            movieBackdropPath = movieDetail.getString("backdrop_path");
                        }
                        if (movieDetail.has("popularity")) {
                            moviePopularity = movieDetail.getDouble("popularity");
                        }
                        if (movieDetail.has("vote_count")) {
                            movieVoteCount = movieDetail.getDouble("vote_count");
                        }
                        if (movieDetail.has("vote_average")){
                            movieVoteAverage = movieDetail.getDouble("vote_average");
                        }
                        Log.v("############", " title is " + movies + "############ id is" + movieId + " ############ poster path is " + moviePosterPath);
                        movies.add(new Movie(movieTitle, movieId, moviePosterPath, movieOverview, movieVoteCount, movieOriginalTitle,
                                movieVoteAverage, moviePopularity, movieBackdropPath));
                    }
                }
            }
        } catch (JSONException e) {
            //handle exception
        }
        Log.v("############", "Movies returned is: " + movies.toString());
        // Return the list of movies
        return movies;
    }


}

