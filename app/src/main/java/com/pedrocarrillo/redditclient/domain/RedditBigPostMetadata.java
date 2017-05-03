package com.pedrocarrillo.redditclient.domain;

import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.squareup.moshi.Json;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class RedditBigPostMetadata implements DisplayableItem {

    private String kind;
    private RedditPost postData;

    public RedditBigPostMetadata(RedditPostMetadata post) {
        this.kind = post.kind;
        this.postData = post.getPostData();
    }

    public String getKind() {
        return kind;
    }

    public RedditPost getPostData() {
        return postData;
    }

}
