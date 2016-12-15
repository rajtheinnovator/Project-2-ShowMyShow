# ShowMyShow
Udacity Android Developer ND, Project p01: Popular Movies, Stage 1. This app makes use of the TheMovieDb api to collect info on movies.
To make this App build, you'll first of all need an API key from TheMovieDb API and then you'll have to add that key in the app level
build.gradle file in single quotes such that it looks like: 
 buildTypes.each {
        it.buildConfigField 'String', 'THE_MOVIEDB_API_KEY', 'your_api_key'
    }
    
where your_api_key is the key you get from the TheMovieDb API.
The api key can be obtained from the TheMovieDb api after signing up on the link: https://www.themoviedb.org/documentation/api
