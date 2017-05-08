package com.pedrocarrillo.redditclient.data;

import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;

import rx.Observable;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public interface PostsDataSource {

    Observable<RedditPostMetadata> getPosts();

    Observable<RedditPostMetadata> getPaginatedPosts();

    void setFavorite(RedditPostMetadata redditPostMetadata, boolean favorite);

}
