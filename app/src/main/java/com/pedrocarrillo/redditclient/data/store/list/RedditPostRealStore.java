package com.pedrocarrillo.redditclient.data.store.list;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.impl.RealStore;
import com.pedrocarrillo.redditclient.data.store.RedditPostsRequest;
import com.pedrocarrillo.redditclient.domain.RedditContent;

import java.util.List;

import rx.Observable;


/**
 * Created by pedrocarrillo on 5/5/17.
 */

public class RedditPostRealStore extends RealStore<List<RedditContent>, RedditPostsRequest> {

    public RedditPostRealStore(Fetcher<List<RedditContent>, RedditPostsRequest> fetcher) {
        super(fetcher);
    }

    Observable<List<RedditContent>> getPosts(RedditPostsRequest request) {
        return get(request);
    }

}

