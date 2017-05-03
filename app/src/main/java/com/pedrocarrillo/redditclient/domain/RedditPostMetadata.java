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

    public String getKind() {
        return kind;
    }

    public RedditPost getPostData() {
        return postData;
    }

}
