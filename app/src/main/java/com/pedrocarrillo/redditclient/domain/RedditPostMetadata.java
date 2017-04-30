package com.pedrocarrillo.redditclient.domain;

import com.squareup.moshi.Json;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditPostMetadata {

    String kind;
    @Json(name = "data")
    RedditPost postData;

    public String getKind() {
        return kind;
    }

    public RedditPost getPostData() {
        return postData;
    }

}
