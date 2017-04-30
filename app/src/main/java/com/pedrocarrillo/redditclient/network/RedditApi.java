package com.pedrocarrillo.redditclient.network;

import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public interface RedditApi {

    @GET("/r/{subreddit}/.json")
    Call<RedditResponse> getSubreddit(@Path("subreddit") String subreddit);


}
