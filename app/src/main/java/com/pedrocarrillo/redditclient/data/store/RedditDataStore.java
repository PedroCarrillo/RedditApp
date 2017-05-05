package com.pedrocarrillo.redditclient.data.store;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.impl.RealStore;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditResponse;

import rx.Observable;


/**
 * Created by pedrocarrillo on 5/5/17.
 */

public class RedditDataStore extends RealStore<RedditData, RedditPostsRequest> {

    public RedditDataStore(Fetcher<RedditData, RedditPostsRequest> fetcher) {
        super(fetcher);
    }

    Observable<RedditData> getPosts(RedditPostsRequest request) {
        return get(request);
    }


}

