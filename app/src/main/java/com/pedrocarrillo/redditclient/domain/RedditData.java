package com.pedrocarrillo.redditclient.domain;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditData {

    private String modhash;
    @Json(name = "children")
    private List<RedditPostMetadata> posts;
    private String after;
    private String before;

    public String getModhash() {
        return modhash;
    }

    public List<RedditPostMetadata> getPosts() {
        return posts;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public void setPosts(List<RedditPostMetadata> posts) {
        this.posts = posts;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
