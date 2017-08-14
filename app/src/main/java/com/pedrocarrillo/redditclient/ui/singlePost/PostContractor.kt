package com.pedrocarrillo.redditclient.ui.singlePost

import com.pedrocarrillo.redditclient.domain.Comment
import com.pedrocarrillo.redditclient.ui.custom.BasePresenter
import com.pedrocarrillo.redditclient.ui.custom.BaseView

/**
 * @author pedrocarrillo.
 */

interface PostContractor {

    interface View : BaseView<PostContractor.Presenter> {

        fun showInfo()
        fun showComment(comment : Comment)

    }

    interface Presenter : BasePresenter {

        fun loadInfo(permalink: String)

    }

}
