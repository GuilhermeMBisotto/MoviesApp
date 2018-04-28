package com.arctouch.codechallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieReview {

    @SerializedName("results")
    private ArrayList<Review> reviews;
    public ArrayList<Review> getReviews() {
        if (reviews == null)
            reviews = new ArrayList<>();

        return reviews;
    }
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int total_pages;

    @SerializedName("total_results")
    public int total_results;

}
