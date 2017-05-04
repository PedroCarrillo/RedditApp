package com.pedrocarrillo.redditclient.data.repositories;

import android.support.annotation.NonNull;

import com.pedrocarrillo.redditclient.data.PostsDataSource;
import com.pedrocarrillo.redditclient.domain.RedditData;

import rx.Observable;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class PostsRepository implements PostsDataSource {

    private PostsDataSource mPostsRemoteDataSource;
    private PostsDataSource mPostsLocalDataSource;

    private static PostsRepository ourInstance;

    public static PostsRepository getInstance(PostsDataSource tasksRemoteDataSource) {
        if (ourInstance != null) {
            ourInstance = new PostsRepository(tasksRemoteDataSource);
        }
        return ourInstance;
    }

    private PostsRepository(@NonNull PostsDataSource tasksRemoteDataSource) {
        mPostsRemoteDataSource = tasksRemoteDataSource;
    }

    @Override
    public Observable<RedditData> getPosts(String after) {
        return mPostsRemoteDataSource.getPosts(after);
    }

}
