package com.pedrocarrillo.redditclient.domain;

import com.google.gson.annotations.SerializedName;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditContent implements DisplayableItem {

    protected String kind;
    @SerializedName("data")
    protected RedditContentData redditContentData;

    protected boolean isFavorite;
    protected boolean isBigPost;

    public String getKind() {
        return kind;
    }

    public RedditContent() {
    }

    public RedditContent(RedditContentData redditContentData) {
        this.redditContentData = redditContentData;
    }

    public RedditContentData getRedditContentData() {
        return redditContentData;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isBigPost() {
        return isBigPost;
    }

    public void setBigPost(boolean bigPost) {
        isBigPost = bigPost;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setRedditContentData(RedditContentData redditContentData) {
        this.redditContentData = redditContentData;
    }
}
