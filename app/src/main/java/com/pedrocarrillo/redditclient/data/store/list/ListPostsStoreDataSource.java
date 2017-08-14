package com.pedrocarrillo.redditclient.data.store.list;

import com.pedrocarrillo.redditclient.domain.RedditContent;

import java.util.List;

import rx.Observable;

/**
 * Created by pedrocarrillo on 5/5/17.
 */

public interface ListPostsStoreDataSource {

    Observable<List<RedditContent>> getPosts(String subreddit);

    Observable<List<RedditContent>> getPaginatedPosts(String subreddit);

}
