package com.pedrocarrillo.redditclient.data.store.list;

import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;

import java.util.List;

import rx.Observable;

/**
 * Created by pedrocarrillo on 5/5/17.
 */

public interface ListPostsStoreDataSource {

    Observable<List<RedditPostMetadata>> getPosts(String subreddit);

    Observable<List<RedditPostMetadata>> getPaginatedPosts(String subreddit);

}
