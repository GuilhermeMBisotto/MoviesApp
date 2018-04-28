package com.arctouch.codechallenge.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("author")
    private String author;
    public String getAuthor() { return author != null ? author : ""; }

    @SerializedName("content")
    private String content;
    public String getContent() { return content != null ? content : ""; }

    @SerializedName("id")
    private String id;
    public String getId() { return id != null ? id : ""; }

    @SerializedName("url")
    private String url;
    public String getUrl() { return url != null ? url : ""; }
}
