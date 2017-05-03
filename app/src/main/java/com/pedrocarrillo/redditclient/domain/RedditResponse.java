package com.pedrocarrillo.redditclient.domain;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditResponse {

    private String kind;
    private RedditData data;

    public String getKind() {
        return kind;
    }

    public RedditData getData() {
        return data;
    }
}
