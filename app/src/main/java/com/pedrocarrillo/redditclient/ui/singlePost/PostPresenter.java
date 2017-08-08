package com.pedrocarrillo.redditclient.ui.singlePost;

import android.util.Log;

import com.pedrocarrillo.redditclient.data.store.post.PostStore;
import com.pedrocarrillo.redditclient.domain.RedditData;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * @author pedrocarrillo.
 */

public class PostPresenter implements PostContractor.Presenter {

    PostContractor.View view;
    PostStore postStore;

    public PostPresenter(PostContractor.View view, PostStore postStore) {
        this.view  = view;
        this.postStore = postStore;
    }

    @Override
    public void start() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadInfo(String permalink) {

        Log.e("test", "loadInfo: " + permalink );
        Observable<RedditData> observable = postStore.getPost(permalink).subscribeOn(Schedulers.io());
        observable.subscribe(new Observer<RedditData>() {
            @Override
            public void onCompleted() {
                Log.e("tag", "completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("tag", "e " + e.getLocalizedMessage());
            }

            @Override
            public void onNext(RedditData redditData) {
                Log.e("tag", "e " + redditData);
            }
        });

    }
}
