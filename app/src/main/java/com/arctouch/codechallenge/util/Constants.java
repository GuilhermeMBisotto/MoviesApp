package com.arctouch.codechallenge.util;

public class Constants {

    public static final String BASE_API_URL = "https://api.themoviedb.org/3";
    public static final String API_KEY = "1f54bd990f1cdfb230adb312546d765d";
    public static final String DEFAULT_LANGUAGE = "en-US";
    public static final String DEFAULT_REGION = "US";
    private static final String BACKDROP_URL = "https://image.tmdb.org/t/p/w780";
    private static final String POSTER_URL = "https://image.tmdb.org/t/p/w154";
    private static final String API_KEY_PREX = "?api_key=";


    public static String buildPosterUrl(String posterPath) {
        return Constants.POSTER_URL + posterPath + API_KEY_PREX + API_KEY;
    }

    public static String buildBackdropUrl(String backdropPath) {
        return Constants.BACKDROP_URL + backdropPath + API_KEY_PREX + API_KEY;
    }
}
