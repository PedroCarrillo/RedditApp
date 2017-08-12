package com.pedrocarrillo.redditclient.data.store.post;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.base.impl.Store;
import com.nytimes.android.external.store.base.impl.StoreBuilder;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.network.RedditApi;

import javax.annotation.Nonnull;

import rx.Observable;

/**
 * @author pedrocarrillo
 */

public class PostStore implements PostStoreDataSource {

    private RedditApi redditApi;

    private Store<RedditData, BarCode> store;

    public PostStore(RedditApi redditApi) {
        this.redditApi = redditApi;
        this.store = StoreBuilder.<RedditData>barcode()
                .fetcher(fetcher)
                .open();
    }

    private Fetcher<RedditData, BarCode> fetcher = new Fetcher<RedditData, BarCode>() {
        @Nonnull
        @Override
        public Observable<RedditData> fetch(@Nonnull BarCode barCode) {
            return getRemotePost(barCode.getKey());
        }
    };

    private Observable<RedditData> getRemotePost(String key) {
        return redditApi.getPost(key);
    }

    @Override
    public Observable<RedditData> getPost(String permalink) {
        BarCode barCode = new BarCode("Post", permalink);
        return store.get(barCode);
    }

}
