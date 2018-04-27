package com.arctouch.codechallenge.model;

import com.google.gson.annotations.SerializedName;

public class MovieDetail extends Movie {

    @SerializedName("tagline")
    private String tagline;
    public String getTagline() { return tagline != null ? tagline : ""; }

    @SerializedName("runtime")
    private int runtime;
    public String getRuntime() { return runtime != 0 ? formatRuntime(runtime) : ""; }

    @SerializedName("overview")
    private String overview;
    public String getOverview() { return overview != null ? overview : ""; }

    private String formatRuntime(int time) {
        return String.format("%s min", Integer.toString(time));
    }
}