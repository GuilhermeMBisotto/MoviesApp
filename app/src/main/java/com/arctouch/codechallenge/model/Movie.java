package com.arctouch.codechallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    private String title;
    public String getTitle() { return title != null ? title : ""; }

    @SerializedName("backdrop_path")
    private String backdropPath;
    public String getBackdropPath() { return backdropPath != null ? backdropPath : ""; }

    @SerializedName("genre_ids")
    public List<Integer> genreIds;
    public List<Genre> genres;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("poster_path")
    private String posterPath;
    public String getPosterPath() { return posterPath != null ? posterPath : ""; }

    @SerializedName("release_date")
    private String releaseDate;
    public String getReleaseDate() { return releaseDate != null ? formatDate(releaseDate) : ""; }


    private static String formatDate(String release) {
        String date[] = release.split("-");
        return String.format("%s/%s/%s",date[1], date[2], date[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (genres != null ? !genres.equals(movie.genres) : movie.genres != null) return false;
        if (genreIds != null ? !genreIds.equals(movie.genreIds) : movie.genreIds != null)
            return false;
        if (posterPath != null ? !posterPath.equals(movie.posterPath) : movie.posterPath != null)
            return false;
        if (backdropPath != null ? !backdropPath.equals(movie.backdropPath) : movie.backdropPath != null)
            return false;
        return releaseDate != null ? releaseDate.equals(movie.releaseDate) : movie.releaseDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (genreIds != null ? genreIds.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + getTitle() + '\'' +
                ", genres=" + genres +
                ", genreIds=" + genreIds +
                ", posterPath='" + getPosterPath() + '\'' +
                ", backdropPath='" + getBackdropPath() + '\'' +
                ", releaseDate='" + getReleaseDate() + '\'' +
                '}';
    }

    public static ArrayList<Movie> filterList(ArrayList<Movie> actual, List<Movie> newMovies) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (Movie m : newMovies) {
            if (!actual.contains(m)) {
                movies.add(m);
            }
        }

        return movies;
    }
}
