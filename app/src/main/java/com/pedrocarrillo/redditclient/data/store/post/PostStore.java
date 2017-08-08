package com.pedrocarrillo.redditclient.data.store.post;

import android.util.Log;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.base.impl.MemoryPolicy;
import com.nytimes.android.external.store.base.impl.RealStore;
import com.nytimes.android.external.store.base.impl.RealStoreBuilder;
import com.nytimes.android.external.store.base.impl.Store;
import com.nytimes.android.external.store.base.impl.StoreBuilder;
import com.pedrocarrillo.redditclient.data.store.list.RedditPostRealStore;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.network.RedditApi;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import rx.Observable;

/**
 * Created by pedrocarrillo on 8/8/17.
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
        Log.e("redditApi", "getRemotePost");
        return redditApi.getPost(key);
    }

    @Override
    public Observable<RedditData> getPost(String permalink) {
        BarCode barCode = new BarCode("Post", permalink);
        return store.get(barCode);
    }

}
