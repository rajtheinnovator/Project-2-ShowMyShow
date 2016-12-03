package me.abhishekraj.showmyshow;

/**
 * Created by ABHISHEK RAJ on 11/15/2016.
 */

public class UrlsAndConstants {
    public class DefaultQuery {
        /*
        * General URL for discovering a movie from the TheMovieDb API
        * @link: "https://api.themoviedb.org/3/discover/movie?api_key=4182aa25bab27d06344e404f65c4ae76"
        */

        public static final String DEFAULT_URL =
                "https://api.themoviedb.org/3/discover/movie";
        public static final String API_KEY_PARAM = "api_key";
        public static final String API_KEY_PARAM_VALUE = BuildConfig.THE_MOVIEDB_API_KEY;
        public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
        public static final String SORT_BY_KEY = "sort_by";
        public static final String SORT_BY_POPULARITY_VALUE_DESCENDING = "popularity.desc";
        public static final String SORT_BY_POPULARITY_VALUE_ASCENDING = "popularity.desc";
        public static final String SORT_BY_TOP_RATED_VALUE_DESCENDING = "vote_average.desc";
        public static final String DESCENDING = ".desc";
        public static final String ASCENDING = ".asc";
    }
    public class  DetailQuery{
        /*
        * General URL for discovering a movie details by making use of its id from the TheMovieDb API
        * @link" "https://api.themoviedb.org/3/movie/284052?api_key=4182aa25bab27d06344e404f65c4ae76&append_to_response=videos,reviews"
        */
        public static final String DEFAULT_URL =
        "https://api.themoviedb.org/3/movie/";
        public static final String API_KEY_PARAM = "api_key";
        public static final String API_KEY_PARAM_VALUE = BuildConfig.THE_MOVIEDB_API_KEY;
        public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
        public static final String APPEND_TO_RESPONSE = "append_to_response";
        public static final String VIDEOS = "videos";
        public static final String VIDEOS_AND_REVIEWS = "videos,reviews";

    }
}
