package com.pedrocarrillo.redditclient.data.store.post;

import com.pedrocarrillo.redditclient.domain.RedditData;

import rx.Observable;

/**
 * @author pedrocarrillo
 */

public interface PostStoreDataSource {

    Observable<RedditData> getPost(String permalink);

}
