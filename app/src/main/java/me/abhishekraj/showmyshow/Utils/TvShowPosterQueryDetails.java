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

import me.abhishekraj.showmyshow.Model.TvShow.TvShow;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowPosterQueryDetails {
    private static String posterPath;
    private static Double popularity;
    private static int id;
    private static String backdropPath;
    private static double voteAverage;
    private static String overview;
    private static String firstAirDate;
    private static int voteCount;
    private static String name;
    private static String originalName;

    private static ArrayList<TvShow> tvShowsArrayList;
  /* Following codes are my own work from other Udacity course under the AndroidBasicsNanodegree
    * and the reference link on the github for that project is @link: https://github.com/rajtheinnovator/NewsToday
    */

    /**
     * Create a private constructor because no one should ever create a {@link TvShowPosterQueryDetails} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name TvShowPosterQueryDetails (and an object instance of TvShowPosterQueryDetails is not needed).
     */
    private TvShowPosterQueryDetails() {
    }

    /**
     * Query the TheMovieDb dataset and return an {@link TvShow} ArrayList to represent a single TvShow.
     */
    public static ArrayList<TvShow> fetchTvShowData(String requestUrl) {
        Log.v("############", "fetchTvShowData called");
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
        ArrayList<TvShow> tvShow = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return tvShow;
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
     * Return a list of {@link TvShow} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<TvShow> extractFeatureFromJson(String jsonResponse) {
        Log.v("############", "extractFeatureFromJson called");
        Log.v("############", "jsonResponse" + jsonResponse);

        // Create an empty ArrayList that we can start adding tvShow to
        tvShowsArrayList = new ArrayList<TvShow>();
        // Create a TvShow reference
        TvShow tvShow;

        /*
        Try to parse the received jsonResponse. If there's a problem with the way the JSON
        is formatted, a JSONException exception object will be thrown. Catch the exception
        so the app doesn't crash, and handle exception.
        */
        try {
            // Parse the jsonResponse string
            JSONObject tv_show_json_response = new JSONObject(jsonResponse);
            Log.v("############", "JSONObject is: " + tv_show_json_response.toString());
            if (tv_show_json_response.has("results")) {
                JSONArray resultsArray = tv_show_json_response.getJSONArray("results");
                if (resultsArray.length() > 0) {
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject tvShowDetail = resultsArray.getJSONObject(i);
                        if (tvShowDetail.has("name")) {
                            name = tvShowDetail.getString("name");
                        }
                        if (tvShowDetail.has("id")) {
                            id = tvShowDetail.getInt("id");
                        }
                        if (tvShowDetail.has("poster_path")) {
                            posterPath = tvShowDetail.getString("poster_path");
                        }
                        if (tvShowDetail.has("overview")) {
                            overview = tvShowDetail.getString("overview");
                        }
                        if (tvShowDetail.has("original_name")) {
                            originalName = tvShowDetail.getString("original_name");
                        }
                        if (tvShowDetail.has("backdrop_path")) {
                            backdropPath = tvShowDetail.getString("backdrop_path");
                        }
                        if (tvShowDetail.has("popularity")) {
                            popularity = tvShowDetail.getDouble("popularity");
                        }
                        if (tvShowDetail.has("vote_count")) {
                            voteCount = tvShowDetail.getInt("vote_count");
                        }
                        if (tvShowDetail.has("vote_average")) {
                            voteAverage = tvShowDetail.getDouble("vote_average");
                        }
                        if (tvShowDetail.has("first_air_date")) {
                            firstAirDate = tvShowDetail.getString("first_air_date");
                        }
                        Log.v("############", " name is " + name + "############ id is" + id + " ############ poster path is " + posterPath);
                        tvShowsArrayList.add(new TvShow(posterPath, popularity, id, backdropPath, voteAverage, overview,
                                firstAirDate, voteCount, name, originalName));
                    }
                }
            }
        } catch (JSONException e) {
            /* handle exception */
            /* if no useful JSON response is returned, then create an empty TvShowArrayList so that
             * null point exception can be avoided
             */
            tvShowsArrayList = new ArrayList<TvShow>();
        }
        Log.v("############", "TvShow returned is: " + tvShowsArrayList.toString());
        // Return the list of TvShow
        return tvShowsArrayList;
    }
}

