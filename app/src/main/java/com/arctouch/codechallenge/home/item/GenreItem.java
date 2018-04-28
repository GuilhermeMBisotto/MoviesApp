package com.arctouch.codechallenge.home.item;

import com.arctouch.codechallenge.model.Genre;

public class GenreItem {

    private Genre genre;
    private boolean select;

    public GenreItem(Genre g, boolean s) {
        this.genre = g;
        this.select = s;
    }

    public Genre getGenre() {
        return genre;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}