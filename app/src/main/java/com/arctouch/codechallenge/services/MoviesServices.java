package com.arctouch.codechallenge.services;

import com.arctouch.codechallenge.api.APIHandler;
import com.arctouch.codechallenge.api.APIService;
import com.arctouch.codechallenge.model.GenresResponse;
import com.arctouch.codechallenge.model.MovieDetail;
import com.arctouch.codechallenge.model.MovieReview;
import com.arctouch.codechallenge.model.UpcomingModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

public class MoviesServices {

    private static final String GET_UPCOMING_URL = "movie/upcoming";
    private static final String GET_GENRES_URL = "genre/movie/list";
    private static final String GET_MOVIE_URL = "movie/{id}";
    private static final String GET_REVIEWS_URL = "movie/{id}/reviews";

    public static void getUpcomingMovies(final int page, final APIHandler.Controller callback) {

        final List<Pair<String, String>> params = new ArrayList<Pair<String, String>>() {{
            add(new Pair<>("page",Integer.toString(page)));
        }};

//        add(new Pair<>("region", Constants.DEFAULT_REGION));

        APIService.GET(GET_UPCOMING_URL, params, new APIHandler.Service(callback) {
            @Override
            public void onSuccess(Object obj) {
                super.onSuccess(new Gson().fromJson((String)obj, UpcomingModel.class));
            }
        });
    }

    public static void getGenres(final APIHandler.Controller callback) {

        final List<Pair<String, String>> params = new ArrayList<Pair<String, String>>() {{ }};

        APIService.GET(GET_GENRES_URL, params, new APIHandler.Service(callback) {
            @Override
            public void onSuccess(Object obj) {
                super.onSuccess(new Gson().fromJson((String)obj, GenresResponse.class));
            }
        });
    }

    public static void getDetailMovie(final int id, final APIHandler.Controller callback) {

        final List<Pair<String, String>> params = new ArrayList<Pair<String, String>>() {{ }};

        APIService.GET(GET_MOVIE_URL.replace("{id}",Integer.toString(id)), params, new APIHandler.Service(callback) {
            @Override
            public void onSuccess(Object obj) {
                super.onSuccess(new Gson().fromJson((String)obj, MovieDetail.class));
            }
        });
    }

    public static void getReview(final int id, final APIHandler.Controller callback) {

        final List<Pair<String, String>> params = new ArrayList<Pair<String, String>>() {{ }};

        APIService.GET(GET_REVIEWS_URL.replace("{id}",Integer.toString(id)), params, new APIHandler.Service(callback) {
            @Override
            public void onSuccess(Object obj) {
                super.onSuccess(new Gson().fromJson((String)obj, MovieReview.class));
            }
        });
    }
}
