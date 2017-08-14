package com.pedrocarrillo.redditclient.ui.singlePost

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.pedrocarrillo.redditclient.R
import com.pedrocarrillo.redditclient.adapter.ExpandableCommentGroup
import com.pedrocarrillo.redditclient.data.store.post.PostStore
import com.pedrocarrillo.redditclient.domain.Comment
import com.pedrocarrillo.redditclient.network.RetrofitManager
import com.pedrocarrillo.redditclient.ui.custom.BaseActivityWithPresenter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_single_post.*

/**
 * @author pedrocarrillo.
 */

class PostActivity : BaseActivityWithPresenter<PostContractor.Presenter>(), PostContractor.View {

    private val groupAdapter = GroupAdapter<ViewHolder>()
    private lateinit var groupLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_post)
        setupViews()
        val postStore = PostStore(RetrofitManager.getInstance().redditApi)
        setPresenter(PostPresenter(this, postStore))
        populateAdapter()

        groupLayoutManager = GridLayoutManager(this, groupAdapter.spanCount).apply {
            spanSizeLookup = groupAdapter.spanSizeLookup
        }

        rv_comments.apply {
            layoutManager = groupLayoutManager
            adapter = groupAdapter
        }

    }

    private fun populateAdapter() {
        initData()
    }

    override fun showComment(comment: Comment) {
        groupAdapter.add(ExpandableCommentGroup(comment))
    }

    private fun initData() {
        val permalink = intent.getStringExtra(POST_PERMALINK)
        presenter.loadInfo(permalink)
    }

    private fun setupViews() {
        setToolbar()
    }

    override fun showInfo() {

    }

    companion object {
        val POST_PERMALINK = "_post_permalink"
    }
}
