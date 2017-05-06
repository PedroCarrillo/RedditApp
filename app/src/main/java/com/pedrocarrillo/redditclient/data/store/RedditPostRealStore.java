package com.pedrocarrillo.redditclient.data.store;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.impl.RealStore;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditResponse;

import java.util.List;

import rx.Observable;


/**
 * Created by pedrocarrillo on 5/5/17.
 */

public class RedditPostRealStore extends RealStore<List<RedditPostMetadata>, RedditPostsRequest> {

    public RedditPostRealStore(Fetcher<List<RedditPostMetadata>, RedditPostsRequest> fetcher) {
        super(fetcher);
    }

    Observable<List<RedditPostMetadata>> getPosts(RedditPostsRequest request) {
        return get(request);
    }

}

