package com.pedrocarrillo.redditclient.data

import com.pedrocarrillo.redditclient.domain.RedditContent
import com.pedrocarrillo.redditclient.domain.RedditData
import com.pedrocarrillo.redditclient.domain.RedditResponse
import rx.Observable
import rx.functions.Func1

/**
 * @author pedrocarrillo.
 */
class RedditDataParser : Func1<RedditResponse, Observable<RedditContent>> {

    var after: String? = null

    override fun call(redditResponse : RedditResponse?) : Observable<RedditContent> {
        return Observable
                .just(redditResponse?.data)
                .flatMap { redditData: RedditData? ->
                    this.after = redditData?.after
                    if (redditData == null) {
                        Observable.empty()
                    } else {
                        Observable.from(redditData.children)
                    }
                }

    }
}