# ShowMyShow
Udacity Android Developer ND, Project p01: Popular Movies, Stage 1. This app makes use of the TheMovieDb api to collect info on movies.
To make this App build, you'll first of all need an API key from TheMovieDb API and then you'll have to add that key in the app level
build.gradle file in single quotes such that it looks like: 
 buildTypes.each {
        it.buildConfigField 'String', 'THE_MOVIEDB_API_KEY', 'your_api_key'
    }
    
where your_api_key is the key you get from the TheMovieDb API.
The api key can be obtained from the TheMovieDb api after signing up on the link: https://www.themoviedb.org/documentation/api

Here's how it looks like on the device:

The Home screen: 
![poster_movie](https://cloud.githubusercontent.com/assets/8430154/21229439/9a62927c-c307-11e6-9883-c1b97500284d.png) ![poster_tv](https://cloud.githubusercontent.com/assets/8430154/21229450/a2c0e022-c307-11e6-9f94-78e35640ef99.png)

The Details screen: 
![movie_detail](https://cloud.githubusercontent.com/assets/8430154/21229460/addaaac4-c307-11e6-9968-fb172d748352.png) ![tv_show_detail](https://cloud.githubusercontent.com/assets/8430154/21229473/b654f56a-c307-11e6-8bd6-e7ddc83e689a.png)

More details there:
![movie_detail1](https://cloud.githubusercontent.com/assets/8430154/21229469/b270ce06-c307-11e6-9e6b-5edd2dd80953.png) ![tv_show_detail1](https://cloud.githubusercontent.com/assets/8430154/21229476/ba2f4f14-c307-11e6-81d0-e7bf317997e7.png)
