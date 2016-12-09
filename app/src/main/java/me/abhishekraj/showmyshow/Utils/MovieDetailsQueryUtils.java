package me.abhishekraj.showmyshow.Utils;

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

import me.abhishekraj.showmyshow.Model.Credits;
import me.abhishekraj.showmyshow.Model.Movie;
import me.abhishekraj.showmyshow.Model.MovieDetailsBundle;
import me.abhishekraj.showmyshow.Model.Review;
import me.abhishekraj.showmyshow.Model.Video;

/**
 * Created by ABHISHEK RAJ on 12/1/2016.
 */
public class MovieDetailsQueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link DefaultMovieQueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name DefaultMovieQueryUtils (and an object instance of DefaultMovieQueryUtils is not needed).
     */
    private MovieDetailsQueryUtils() {
    }

    /**
     * Query the TheMovieDb dataset and return an {@link Movie} ArrayList to represent a single Movie.
     */
    public static MovieDetailsBundle fetchMovieData(String requestUrl) {
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
        MovieDetailsBundle movieDetailsBundle = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return movieDetailsBundle;
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
    public static MovieDetailsBundle extractFeatureFromJson(String jsonResponse) {
        Log.v("############", "extractFeatureFromJson called");
        Log.v("############", "jsonResponse" + jsonResponse);

        // Create an empty ArrayList that we can start adding popularMovies to
        ArrayList<Review> reviews = new ArrayList<Review>();
        ArrayList<Video> videos = new ArrayList<Video>();
        ArrayList<Credits> credits = new ArrayList<>();
        Movie movie = new Movie();
        // Create a MovieDetailsBundle and initialize it
        MovieDetailsBundle movieDetailsBundle = new MovieDetailsBundle();

        /*
        Try to parse the received jsonResponse. If there's a problem with the way the JSON
        is formatted, a JSONException exception object will be thrown. Catch the exception
        so the app doesn't crash, and handle exception.
        */
        try {
            // Parse the jsonResponse string
            JSONObject movie_json_response = new JSONObject(jsonResponse);
            Log.v("############", "JSONObject is: " + movie_json_response.toString());
            if (movie_json_response.has("reviews")) {
                JSONObject mainReviewObject = movie_json_response.getJSONObject("reviews");
                if (mainReviewObject.has("results")) {
                    JSONArray resultsArray = mainReviewObject.getJSONArray("results");
                    if (resultsArray.length() > 0) {
                        for (int i = 0; i < resultsArray.length(); i++) {
                            JSONObject reviewObject = resultsArray.getJSONObject(i);
                            String author = reviewObject.getString("author");
                            String content = reviewObject.getString("content");
                            String url = reviewObject.getString("url");
                            reviews.add(new Review(author, content, url));
                        }
                    }
                }
            }
            if (movie_json_response.has("videos")) {
                JSONObject mainVideoObject = movie_json_response.getJSONObject("videos");
                if (mainVideoObject.has("results")) {
                    JSONArray videoResultsArray = mainVideoObject.getJSONArray("results");
                    if (videoResultsArray.length() > 0) {
                        for (int i = 0; i < videoResultsArray.length(); i++) {
                            JSONObject videoObject = videoResultsArray.getJSONObject(i);
                            String id = videoObject.getString("id");
                            String key = videoObject.getString("key");
                            String name = videoObject.getString("name");
                            String size = videoObject.getString("size");
                            String type = videoObject.getString("type");
                            videos.add(new Video(id, key, name, size, type));
                        }
                    }
                }
            }
            if (movie_json_response.has("runtime")){
                int runTime = movie_json_response.getInt("runtime");
                //movie = new Movie(runTime);
                movie.setMovieRunTimeDuration(runTime);
            }
            if (movie_json_response.has("credits")) {
                JSONObject creditsObject = movie_json_response.getJSONObject("credits");
                if (creditsObject.has("cast")) {
                    JSONArray castArray = creditsObject.getJSONArray("cast");
                    if (castArray.length() > 0) {
                        for (int i = 0; i < castArray.length(); i++) {
                            JSONObject castObject = castArray.getJSONObject(i);
                            String character = "", creditId = "", name = "", profilePath = "";
                            int castId = 0, id = 0;
                            if (castObject.has("cast_id")) {
                                castId = castObject.getInt("cast_id");
                            }
                            if (castObject.has("character")) {
                                character = castObject.getString("character");
                            }
                            if (castObject.has("credit_id")) {
                                character = castObject.getString("credit_id");
                            }
                            if (castObject.has("id")) {
                                castId = castObject.getInt("id");
                            }
                            if (castObject.has("name")) {
                                character = castObject.getString("name");
                            }
                            if (castObject.has("profile_path")) {
                                character = castObject.getString("profile_path");
                            }
                            credits.add(new Credits(castId, character, creditId, id, name, profilePath));
                        }
                    }
                }
            }
            Log.v("############", "Size of reviews is" + reviews.size());
            Log.v("############", "Size of videos is" + videos.size());
            movieDetailsBundle.setReviewArrayList(reviews);
            movieDetailsBundle.setVideoArrayList(videos);
            movieDetailsBundle.setCreditsArrayList(credits);
            movieDetailsBundle.setMovie(movie);
        } catch (JSONException e) {
            //handle exception
        }
        Log.v("############", "Movies returned is: " + movieDetailsBundle.toString());
        // Return the list of popularMovies
        return movieDetailsBundle;
    }
}

