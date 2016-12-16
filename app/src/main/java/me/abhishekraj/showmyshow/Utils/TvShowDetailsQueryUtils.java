package me.abhishekraj.showmyshow.utils;

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

import me.abhishekraj.showmyshow.model.tvshow.Credits;
import me.abhishekraj.showmyshow.model.tvshow.Review;
import me.abhishekraj.showmyshow.model.tvshow.Seasons;
import me.abhishekraj.showmyshow.model.tvshow.TvShow;
import me.abhishekraj.showmyshow.model.tvshow.TvShowDetailsBundle;
import me.abhishekraj.showmyshow.model.tvshow.Video;

/**
 * Created by ABHISHEK RAJ on 12/10/2016.
 */

public class TvShowDetailsQueryUtils {

    /*tvshow Details Text*/
    private static int runtime;
    private static int numberOfSeasons;
    private static int numberOfEpisodes;
    private static String TvShowType;
    private static String lastAirDate;

    /*Variables for handling tvshow Cast Details*/
    private static String characterCast;
    private static String creditIdCast;
    private static String nameCast;
    private static String profilePathCast;
    private static int idCast;

    /*Variables for handling tvshow Videos Details*/
    private static String idVideo;
    private static String keyVideo;
    private static String nameVideo;
    private static String sizeVideo;
    private static String typeVideo;

    /* Variables for handling Reviews */
    private static String author;
    private static String content;
    private static String url;

    /* Variables for handling tvshow Seasons */
    private static String seasonAirDate;
    private static String seasonPosterPath;
    private static int seasonEpisodeCount;
    private static int seasonId;
    private static int seasonNumber;

    /* Main ArrayList and Object variables */
    private static ArrayList<Review> reviews;
    private static ArrayList<Video> videos;
    private static ArrayList<Credits> credits;
    private static ArrayList<Seasons> seasons;
    private static TvShow tvShow;

    /**
     * Create a private constructor because no one should ever create a {@link TvShowDetailsQueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class nameCast TvShowDetailsQueryUtils (and an object instance of TvShowDetailsQueryUtils is not needed).
     */
    private TvShowDetailsQueryUtils() {
    }

    /**
     * Query the TheMovieDb dataset and return an {@link me.abhishekraj.showmyshow.model.tvshow.TvShow}
     * ArrayList to represent a single tvshow.
     */
    public static TvShowDetailsBundle fetchTvShowData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            //handle exception
        }

        /* Extract relevant fields from the JSON response and create an {@link Event} object */
        TvShowDetailsBundle tvShowDetailsBundle = extractFeatureFromJson(jsonResponse);

        /* Return the {@link Event} */
        return tvShowDetailsBundle;
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
     * Return a list of {@link me.abhishekraj.showmyshow.model.tvshow.TvShow} objects that has been built up from
     * parsing a JSON response.
     */
    public static TvShowDetailsBundle extractFeatureFromJson(String jsonResponse) {

        // Create an empty ArrayList that we can start adding tvShows to
        reviews = new ArrayList<Review>();
        videos = new ArrayList<Video>();
        credits = new ArrayList<Credits>();
        seasons = new ArrayList<Seasons>();
        tvShow = new TvShow();
        /* Create a TvShowDetailsBundle and initialize it */
        TvShowDetailsBundle tvShowDetailsBundle = new TvShowDetailsBundle();

        /*
        Try to parse the received jsonResponse. If there's a problem with the way the JSON
        is formatted, a JSONException exception object will be thrown. Catch the exception
        so the app doesn't crash, and handle exception.
        */
        try {
            /* Parse the jsonResponse string */
            JSONObject tv_show_json_response = new JSONObject(jsonResponse);
            if (tv_show_json_response.has("reviews")) {
                JSONObject mainReviewObject = tv_show_json_response.getJSONObject("reviews");
                if (mainReviewObject.has("results")) {
                    JSONArray resultsArray = mainReviewObject.getJSONArray("results");
                    if (resultsArray.length() > 0) {
                        for (int i = 0; i < resultsArray.length(); i++) {
                            JSONObject reviewObject = resultsArray.getJSONObject(i);
                            if (reviewObject.has("author")) {
                                author = reviewObject.getString("author");
                            }
                            if (reviewObject.has("content")) {
                                content = reviewObject.getString("content");
                            }
                            if (reviewObject.has("url")) {
                                url = reviewObject.getString("url");
                            }
                            reviews.add(new Review(author, content, url));
                        }
                    }
                }
            }
            /* get extra details for TvShowDetailsFragment */
            if (tv_show_json_response.has("episode_run_time")) {
                JSONArray runTimeArray = tv_show_json_response.getJSONArray("episode_run_time");
                runtime = runTimeArray.getInt(0);
            }
            if (tv_show_json_response.has("last_air_date")) {
                lastAirDate = tv_show_json_response.getString("last_air_date");
            }
            if (tv_show_json_response.has("number_of_episodes")) {
                numberOfEpisodes = tv_show_json_response.getInt("number_of_episodes");
            }
            if (tv_show_json_response.has("number_of_seasons")) {
                numberOfSeasons = tv_show_json_response.getInt("number_of_seasons");
            }
            if (tv_show_json_response.has("type")) {
                TvShowType = tv_show_json_response.getString("type");
            }
            tvShow = new TvShow(runtime, lastAirDate, numberOfEpisodes, numberOfSeasons, TvShowType);

            /* get details for tvShow video */
            if (tv_show_json_response.has("videos")) {
                JSONObject mainVideoObject = tv_show_json_response.getJSONObject("videos");
                if (mainVideoObject.has("results")) {
                    JSONArray videoResultsArray = mainVideoObject.getJSONArray("results");
                    if (videoResultsArray.length() > 0) {
                        for (int i = 0; i < videoResultsArray.length(); i++) {
                            JSONObject videoObject = videoResultsArray.getJSONObject(i);
                            if (videoObject.has("id")) {
                                idVideo = videoObject.getString("id");
                            }
                            if (videoObject.has("key")) {
                                keyVideo = videoObject.getString("key");
                            }
                            if (videoObject.has("name")) {
                                nameVideo = videoObject.getString("name");
                            }
                            if (videoObject.has("size")) {
                                sizeVideo = videoObject.getString("size");
                            }
                            if (videoObject.has("type")) {
                                typeVideo = videoObject.getString("type");
                            }
                            videos.add(new Video(idVideo, keyVideo, nameVideo, sizeVideo, typeVideo));
                        }
                    }
                }
            } else {
                videos.add(new Video());
            }
            if (tv_show_json_response.has("credits")) {
                JSONObject creditsObject = tv_show_json_response.getJSONObject("credits");
                if (creditsObject.has("cast")) {
                    JSONArray castArray = creditsObject.getJSONArray("cast");
                    if (castArray.length() > 0) {
                        for (int i = 0; i < castArray.length(); i++) {
                            JSONObject castObject = castArray.getJSONObject(i);

                            if (castObject.has("character")) {
                                characterCast = castObject.getString("character");
                            }
                            if (castObject.has("credit_id")) {
                                creditIdCast = castObject.getString("credit_id");
                            }
                            if (castObject.has("id")) {
                                idCast = castObject.getInt("id");
                            }
                            if (castObject.has("name")) {
                                nameCast = castObject.getString("name");
                            }
                            if (castObject.has("profile_path")) {
                                profilePathCast = castObject.getString("profile_path");
                            }
                            credits.add(new Credits(characterCast, creditIdCast, idCast, nameCast, profilePathCast));
                        }
                    }
                }
            }
            /* details of tvshow Season */
            if (tv_show_json_response.has("seasons")) {
                JSONArray seasonArray = tv_show_json_response.getJSONArray("seasons");
                if (seasonArray.length() > 0) {
                    for (int i = 0; i < seasonArray.length(); i++) {
                        JSONObject seasonObject = seasonArray.getJSONObject(i);
                        if (seasonObject.has("air_date")) {
                            seasonAirDate = seasonObject.getString("air_date");
                        }
                        if (seasonObject.has("episode_count")) {
                            seasonEpisodeCount = seasonObject.getInt("episode_count");
                        }
                        if (seasonObject.has("id")) {
                            seasonId = seasonObject.getInt("id");
                        }
                        if (seasonObject.has("poster_path")) {
                            seasonPosterPath = seasonObject.getString("poster_path");
                        }
                        if (seasonObject.has("season_number")) {
                            seasonNumber = seasonObject.getInt("season_number");
                        }
                        seasons.add(new Seasons(seasonAirDate, seasonEpisodeCount, seasonId, seasonPosterPath, seasonNumber));
                    }
                }
            }
            tvShowDetailsBundle.setReviewArrayList(reviews);
            tvShowDetailsBundle.setVideoArrayList(videos);
            tvShowDetailsBundle.setCreditsArrayList(credits);
            tvShowDetailsBundle.setSeasonsArrayList(seasons);
            tvShowDetailsBundle.setTvShow(tvShow);
        } catch (JSONException e) {
            /* handle exception */
            /*if no useful JSON response is returned because of any reason like unavailability of
            * Reviews or Credits or Videos or due to any other network/API query error, then create a
            * new TvShowDetailsBundle so that
            * null point exception can be avoided
            */
        }
        // Return the list of all details
        return tvShowDetailsBundle;
    }
}

