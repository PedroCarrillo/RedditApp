package com.pedrocarrillo.redditclient.ui.singlePost

import android.util.Log
import com.pedrocarrillo.redditclient.data.CommentParser
import com.pedrocarrillo.redditclient.data.RedditDataParser
import com.pedrocarrillo.redditclient.data.store.post.PostStore
import com.pedrocarrillo.redditclient.domain.Comment
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author pedrocarrillo.
 */

class PostPresenter(internal var view: PostContractor.View,
                    internal var postStore: PostStore) : PostContractor.Presenter {

    override fun start() {

    }

    override fun unsubscribe() {

    }

    override fun loadInfo(permalink: String) {
        postStore.getPost(permalink)
                .subscribeOn(Schedulers.io())
                .flatMapIterable({ redditResponses -> redditResponses })
                .flatMap(RedditDataParser())
                .filter({ redditContent -> redditContent.kind == "t1" && redditContent.redditContentData != null})
                .map(CommentParser())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Comment> {
                    override fun onCompleted() {
                        Log.e("tag", "completed")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("tag", "e " + e.printStackTrace())
                    }

                    override fun onNext(comment: Comment) {
                        view.showComment(comment)
                    }
                })
    }
}
