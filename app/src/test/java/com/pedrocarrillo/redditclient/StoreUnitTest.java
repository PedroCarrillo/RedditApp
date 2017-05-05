package com.pedrocarrillo.redditclient;

import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.base.impl.Store;
import com.nytimes.android.external.store.base.impl.StoreBuilder;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditResponse;
import com.pedrocarrillo.redditclient.network.RedditApi;
import com.pedrocarrillo.redditclient.network.RetrofitManager;
import com.pedrocarrillo.redditclient.ui.home.HomePresenter;

import org.junit.Test;

import java.util.Random;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pedrocarrillo on 5/5/17.
 */

public class StoreUnitTest {

    @Test
    public void creatingStore() throws Exception {


    }

}
