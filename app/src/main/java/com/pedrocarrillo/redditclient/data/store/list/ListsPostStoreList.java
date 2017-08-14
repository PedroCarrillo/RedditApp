package com.pedrocarrillo.redditclient.data.store.list;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.impl.RealStore;
import com.pedrocarrillo.redditclient.data.RedditDataParser;
import com.pedrocarrillo.redditclient.data.store.RedditPostsRequest;
import com.pedrocarrillo.redditclient.domain.RedditContent;
import com.pedrocarrillo.redditclient.network.RedditApi;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by pedrocarrillo on 5/5/17.
 */

public class ListsPostStoreList implements ListPostsStoreDataSource {

    private RedditApi redditApi;
    private String after;
    private Random random = new Random();
    private RealStore<List<RedditContent>, RedditPostsRequest> remoteStore;

    private Fetcher<List<RedditContent>, RedditPostsRequest> fetcher = new Fetcher<List<RedditContent>, RedditPostsRequest>() {
        @Nonnull
        @Override
        public Observable<List<RedditContent>> fetch(@Nonnull RedditPostsRequest request) {
            return getPosts(request.getKey(), request.getAfter());
        }
    };

    private Observable<List<RedditContent>> getPosts(String subreddit, String after) {
        return redditApi.getSubreddit(subreddit, after)
                .flatMap(new RedditDataParser())
                .subscribeOn(Schedulers.io())
                .filter(redditContent -> !redditContent.getRedditContentData().isNsfw())
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


    public ListsPostStoreList(RedditApi redditApi) {
        this.redditApi = redditApi;
        this.remoteStore = new RedditPostRealStore(fetcher);
    }

    @Override
    public Observable<List<RedditContent>> getPosts(String subreddit) {
        RedditPostsRequest redditPostsRequest = new RedditPostsRequest(subreddit, RedditContent.class.getSimpleName(), null);
        return remoteStore.get(redditPostsRequest);
    }

    @Override
    public Observable<List<RedditContent>> getPaginatedPosts(String subreddit) {
        RedditPostsRequest redditPostsRequest = new RedditPostsRequest(subreddit, RedditContent.class.getSimpleName(), after);
        return remoteStore.get(redditPostsRequest);
    }

}
