package com.pedrocarrillo.redditclient.data;

import com.pedrocarrillo.redditclient.domain.RedditData;

import rx.Observable;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public interface PostsDataSource {

    Observable<RedditData> getPosts(String after);

}
