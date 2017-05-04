package com.pedrocarrillo.redditclient.data.remote;

import com.pedrocarrillo.redditclient.data.PostsDataSource;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditResponse;
import com.pedrocarrillo.redditclient.network.RedditApi;
import com.pedrocarrillo.redditclient.network.RetrofitManager;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class RemotePostsRepository implements PostsDataSource {

    private RedditApi redditApi;

    private static RemotePostsRepository ourInstance;

    public static RemotePostsRepository getInstance(RedditApi redditApi) {
        if (ourInstance == null) {
            ourInstance = new RemotePostsRepository(redditApi);
        }
        return ourInstance;
    }

    private RemotePostsRepository(RedditApi redditApi) {
        this.redditApi = redditApi;
    }

    @Override
    public Observable<RedditData> getPosts(String after) {
        return redditApi.getSubreddit("popular", after)
                .map(RedditResponse::getData)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void setFavorite(RedditPostMetadata redditPostMetadata, boolean favorite) {
        // Nothing to do as this is only local.
        redditPostMetadata.setFavorite(favorite);
    }

}
