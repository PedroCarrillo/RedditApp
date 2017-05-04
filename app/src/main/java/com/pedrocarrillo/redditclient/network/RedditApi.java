package com.pedrocarrillo.redditclient.network;

import com.pedrocarrillo.redditclient.domain.RedditResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public interface RedditApi {

    @GET("/r/{subreddit}/.json")
    Observable<RedditResponse> getSubreddit(@Path("subreddit") String subreddit, @Query("after") String after);

}
