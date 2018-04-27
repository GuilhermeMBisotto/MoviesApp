package com.arctouch.codechallenge.model;

import com.google.gson.annotations.SerializedName;

public class MovieDates {
    @SerializedName("maximum")
    private String maximum;
    public String getMaximum() {
        return maximum != null ? maximum : "";
    }

    @SerializedName("minimum")
    private String minimum;
    public String getMinimum() {
        return minimum != null ? minimum : "";
    }
}
