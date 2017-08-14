package com.pedrocarrillo.redditclient.data.store.post;

import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditResponse;

import java.util.List;

import rx.Observable;

/**
 * @author pedrocarrillo
 */

public interface PostStoreDataSource {

    Observable<List<RedditResponse>> getPost(String permalink);

}
