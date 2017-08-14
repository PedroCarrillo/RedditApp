package com.pedrocarrillo.redditclient.data;

import com.pedrocarrillo.redditclient.domain.RedditContent;

import rx.Observable;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public interface PostsDataSource {

    Observable<RedditContent> getPosts();

    Observable<RedditContent> getPaginatedPosts();

    void setFavorite(RedditContent redditContent, boolean favorite);

}
