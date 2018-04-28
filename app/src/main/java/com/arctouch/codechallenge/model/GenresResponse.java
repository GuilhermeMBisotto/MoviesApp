package com.arctouch.codechallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResponse {

    @SerializedName("genres")
    public List<Genre> genres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenresResponse)) return false;

        GenresResponse that = (GenresResponse) o;

        return genres != null ? genres.equals(that.genres) : that.genres == null;
    }

    @Override
    public int hashCode() {
        return genres != null ? genres.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GenresResponse{" +
                "genres=" + genres +
                '}';
    }
}
