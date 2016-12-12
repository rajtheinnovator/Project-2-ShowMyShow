package me.abhishekraj.showmyshow.Utils;

import me.abhishekraj.showmyshow.BuildConfig;

/**
 * Created by ABHISHEK RAJ on 11/15/2016.
 */

public class UrlsAndConstants {
    public static class TvShowDetailQuery {
        public static final String DEFAULT_URL =
                "https://api.themoviedb.org/3/tv/";
    }

    public class MoviePosterQuery {
        /*
        * General URL for discovering a movie from the TheMovieDb API
        * @link: "https://api.themoviedb.org/3/discover/movie?api_key=4182aa25bab27d06344e404f65c4ae76"
        */

        public static final String DEFAULT_URL =
                "https://api.themoviedb.org/3/discover/movie";
        public static final String UPCOMING_MOVIE_BASE_URL =
                "https://api.themoviedb.org/3/movie/upcoming";
        public static final String API_KEY_PARAM = "api_key";

        /*Put your API KEY either here directly or use BuildConfig to retrieve it*/
        public static final String API_KEY_PARAM_VALUE = BuildConfig.THE_MOVIEDB_API_KEY;
        public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
        public static final String SORT_BY_KEY = "sort_by";
        public static final String SORT_BY_POPULARITY_VALUE_DESCENDING = "popularity.desc";
        public static final String SORT_BY_POPULARITY_VALUE_ASCENDING = "popularity.desc";
        public static final String SORT_BY_TOP_RATED_VALUE_DESCENDING = "vote_average.desc";
        public static final String DESCENDING = ".desc";
        public static final String ASCENDING = ".asc";
    }

    public class MovieDetailQuery {
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
        public static final String REVIEWS = "reviews";
        public static final String CREDITS = "credits";
        public static final String VIDEOS_AND_REVIEWS = "videos,reviews";
        public static final String VIDEOS_AND_REVIEWS_AND_CREDITS = "videos,reviews,credits";

    }

    public class TvPosterQuery {
        public static final String DISCOVER_TV_SHOW_DEFAULT_URL = "https://api.themoviedb.org/3/discover/tv";
        public static final String SORT_BY_FIRST_AIR_DATES_DESCENDING = "first_air_dates.desc";
        public static final String AIR_DATE_GREATER_THAN = "air_date.gte";
        public static final String AIR_DATE_GREATER_THAN_VALUE_DECEMBER_START = "2016-12-01";
        public static final String AIR_DATE_GREATER_THAN_VALUE_NOVEMBER_START = "2016-11-01";
        public static final String PAGE_OF_RESULT_TO_QUERY = "page";
        public static final String WITH_RUNTIME_GREATER_THAN = "with_runtime.gte";
        public static final String VOTE_AVERAGE_GREATER_THAN = "vote_average.gte";


    }
}
