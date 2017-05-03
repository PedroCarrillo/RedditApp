package com.pedrocarrillo.redditclient.domain;

import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.squareup.moshi.Json;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditPostMetadata implements DisplayableItem {

    private String kind;
    @Json(name = "data")
    private RedditPost postData;

    public String getKind() {
        return kind;
    }

    public RedditPost getPostData() {
        return postData;
    }

}
