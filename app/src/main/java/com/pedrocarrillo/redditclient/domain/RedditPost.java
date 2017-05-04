package com.pedrocarrillo.redditclient.domain;

import com.squareup.moshi.Json;


/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditPost {

    private String id;
    private String subreddit;
    private String title;
    @Json(name="subreddit_name_prefixed")
    private String subredditNamePrefixed;
    @Json(name="over_18")
    private boolean isNsfw;
    @Json(name = "ups")
    private int upVotes;
    @Json(name="created_utc")
    private long createdAt;
    private String thumbnail;
    private int score;
    @Json(name="num_comments")
    private int numComments;
    private String author;
    private Preview preview;

    public RedditPost(String id, String title, String subredditNamePrefixed, boolean isNsfw, long createdAt, String thumbnail, String author) {
        this.id = id;
        this.title = title;
        this.subredditNamePrefixed = subredditNamePrefixed;
        this.isNsfw = isNsfw;
        this.createdAt = createdAt;
        this.thumbnail = thumbnail;
        this.author = author;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getTitle() {
        return title;
    }

    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    public boolean isNsfw() {
        return isNsfw;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getScore() {
        return score;
    }

    public int getNumComments() {
        return numComments;
    }

    public String getAuthor() {
        return author;
    }

    public Preview getPreview() {
        return preview;
    }

    public String getId() {
        return id;
    }

}
