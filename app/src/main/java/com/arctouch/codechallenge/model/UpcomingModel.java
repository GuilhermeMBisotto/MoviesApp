package com.arctouch.codechallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UpcomingModel {

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    private List<Movie> movieList;
    public List<Movie> getMovieList() {
        return movieList != null ? movieList : new ArrayList<>();
    }

    @SerializedName("dates")
    private MovieDates dates;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;
}
