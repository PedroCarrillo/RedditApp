package com.pedrocarrillo.redditclient.data.store;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.impl.RealStore;
import com.pedrocarrillo.redditclient.data.remote.RemotePostsRepository;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditResponse;
import com.pedrocarrillo.redditclient.network.RedditApi;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by pedrocarrillo on 5/5/17.
 */

public class RemotePostsStore implements PostsStoreDataSource {

    private RedditApi redditApi;
    private String after;
    private Random random = new Random();
    private RealStore<List<RedditPostMetadata>, RedditPostsRequest> remoteStore;

    private Fetcher<List<RedditPostMetadata>, RedditPostsRequest> fetcher = new Fetcher<List<RedditPostMetadata>, RedditPostsRequest>() {
        @Nonnull
        @Override
        public Observable<List<RedditPostMetadata>> fetch(@Nonnull RedditPostsRequest request) {
            return getPosts(request.getKey(), request.getAfter());
        }
    };

    private Observable<List<RedditPostMetadata>> getPosts(String subreddit, String after) {
        return redditApi.getSubreddit(subreddit, after)
                .map(RedditResponse::getData)
                .subscribeOn(Schedulers.io())
                .flatMap(redditData -> {
                    RemotePostsStore.this.after = redditData.getAfter();
                    return Observable.from(redditData.getPosts());
                })
                .filter(redditPostMetadata -> !redditPostMetadata.getPostData().isNsfw())
                .map(redditPostMetadata -> {
                    int r = random.nextInt(10);
                    if (r % 5 == 0) {
                        redditPostMetadata.setBigPost(true);
                        return redditPostMetadata;
                    } else {
                        return redditPostMetadata;
                    }
                })
                .toList();
    }


    public RemotePostsStore(RedditApi redditApi) {
        this.redditApi = redditApi;
        this.remoteStore = new RedditPostRealStore(fetcher);
    }

    @Override
    public Observable<List<RedditPostMetadata>> getPosts(String subreddit) {
        RedditPostsRequest redditPostsRequest = new RedditPostsRequest(subreddit, RedditPostMetadata.class.getSimpleName(), null);
        return remoteStore.get(redditPostsRequest);
    }

    @Override
    public Observable<List<RedditPostMetadata>> getPaginatedPosts(String subreddit) {
        RedditPostsRequest redditPostsRequest = new RedditPostsRequest(subreddit, RedditPostMetadata.class.getSimpleName(), after);
        return remoteStore.get(redditPostsRequest);
    }

    @Override
    public void setFavorite(RedditPostMetadata redditPostMetadata, boolean favorite) {
        // do nothing
    }

}
