package com.pedrocarrillo.redditclient.network;

import android.util.Log;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditClientSubscriber<T> extends Subscriber<T> {

    Subscriber<T> subscriber;

    public RedditClientSubscriber(Subscriber<T> subscriber1) {
        this.subscriber = subscriber1;
    }

    @Override
    public void onCompleted() {
        subscriber.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            Log.e("excep" , e.getLocalizedMessage());
        } else {
            subscriber.onError(e);
        }
    }

    @Override
    public void onNext(T t) {
       subscriber.onNext(t);
    }
}
