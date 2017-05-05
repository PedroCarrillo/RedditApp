package com.pedrocarrillo.redditclient.data.store;

/**
 * Created by pedrocarrillo on 5/5/17.
 */

public class RedditPostsRequest {

    private String key;
    private String type;
    private String after;

    public RedditPostsRequest(String key, String type, String after) {
        this.key = key;
        this.type = type;
        this.after = after;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

}
