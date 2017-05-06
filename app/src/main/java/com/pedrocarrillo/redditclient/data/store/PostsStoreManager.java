package com.pedrocarrillo.redditclient.data.store;

import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;

import java.util.List;

import rx.Observable;

/**
 * Created by pedrocarrillo on 5/5/17.
 */

public class PostsStoreManager implements PostsStoreDataSource {

    private PostsStoreDataSource mRemotePostsStore;
    private PostsStoreDataSource mLocalPostsStore;
    private boolean isInternetAvailable;

    public PostsStoreManager(PostsStoreDataSource mRemotePostsStore, PostsStoreDataSource mLocalPostsStore, boolean isInternetAvailable) {
        this.mRemotePostsStore = mRemotePostsStore;
        this.mLocalPostsStore = mLocalPostsStore;
        this.isInternetAvailable = isInternetAvailable;
    }

    public PostsStoreManager(PostsStoreDataSource mRemotePostsStore) {
        this.mRemotePostsStore = mRemotePostsStore;
        this.isInternetAvailable = isInternetAvailable;
    }

    @Override
    public Observable<List<RedditPostMetadata>> getPosts(String subreddit) {
//        if (isInternetAvailable) {
            return mRemotePostsStore.getPosts(subreddit);
//        } else {
//            return mLocalPostsStore.getPosts(subreddit);
//        }
    }

    @Override
    public Observable<List<RedditPostMetadata>> getPaginatedPosts(String subreddit) {
//        if (isInternetAvailable) {
            return mRemotePostsStore.getPaginatedPosts(subreddit);
//        } else {
//            return mLocalPostsStore.getPaginatedPosts(subreddit);
//        }
    }

    @Override
    public void setFavorite(RedditPostMetadata redditPostMetadata, boolean favorite) {
//        mLocalPostsStore.setFavorite(redditPostMetadata, favorite);
    }


}
