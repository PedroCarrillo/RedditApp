package com.pedrocarrillo.redditclient.network;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public interface RedditClientCallback <T> {

    void onSuccess(T t);

    void onFailure(String error);

}
