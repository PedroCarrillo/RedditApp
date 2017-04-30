package com.pedrocarrillo.redditclient.domain;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditData {

    String modhash;
    @Json(name = "children")
    List<RedditPostMetadata> posts;
    String after;
    String before;

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
}
