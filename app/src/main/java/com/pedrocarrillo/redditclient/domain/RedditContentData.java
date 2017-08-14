package com.pedrocarrillo.redditclient.domain;

import com.google.gson.annotations.SerializedName;

/**
 * @author pedrocarrillo.
 */

public class RedditContentData {

    private String id;
    private String title;
    @SerializedName("subreddit_name_prefixed")
    private String subredditNamePrefixed;
    @SerializedName("over_18")
    private boolean isNsfw;
    @SerializedName("created_utc")
    private Long createdAt;
    private String thumbnail;
    private String author;
    private String subreddit;
    @SerializedName("ups")
    private int upVotes;
    private int score;
    @SerializedName("num_comments")
    private int numComments;
    @SerializedName("body")
    private String body;
    private Preview preview;
    private String permalink;
    private RedditResponse replies;

    public RedditContentData() {
    }

    public RedditContentData(String id, String title, String subredditNamePrefixed, boolean isNsfw, Long createdAt, String thumbnail, String author) {
        this.id = id;
        this.title = title;
        this.subredditNamePrefixed = subredditNamePrefixed;
        this.isNsfw = isNsfw;
        this.createdAt = createdAt;
        this.thumbnail = thumbnail;
        this.author = author;
    }

    public String getId() {
        return id;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getScore() {
        return score;
    }

    public int getNumComments() {
        return numComments;
    }

    public String getBody() {
        return body;
    }

    public Preview getPreview() {
        return preview;
    }

    public String getPermalink() {
        return permalink;
    }

    public RedditResponse getReplies() {
        return replies;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubredditNamePrefixed(String subredditNamePrefixed) {
        this.subredditNamePrefixed = subredditNamePrefixed;
    }

    public void setNsfw(boolean nsfw) {
        isNsfw = nsfw;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public void setReplies(RedditResponse replies) {
        this.replies = replies;
    }
}
