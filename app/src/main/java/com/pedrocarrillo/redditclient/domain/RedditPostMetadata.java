package com.pedrocarrillo.redditclient.domain;

import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.squareup.moshi.Json;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditPostMetadata implements DisplayableItem {

    protected String kind;
    @Json(name = "data")
    protected RedditPost postData;

    protected boolean isFavorite;
    protected boolean isBigPost;

    public String getKind() {
        return kind;
    }

    public RedditPostMetadata(RedditPost postData) {
        this.postData = postData;
    }

    public RedditPost getPostData() {
        return postData;
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

}
