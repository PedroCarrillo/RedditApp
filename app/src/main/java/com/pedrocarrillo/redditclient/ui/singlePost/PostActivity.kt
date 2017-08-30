package com.pedrocarrillo.redditclient.ui.singlePost

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.view.View
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

    private val threshold = 60;
    private var initialPosition = 0.0f

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
            isNestedScrollingEnabled = false
        }
        setPostBarAnimation()
    }

    private fun setPostBarAnimation() {
        val scrollBounds = Rect()
        content_scroll_view.getHitRect(scrollBounds)
        realView.viewTreeObserver.addOnGlobalLayoutListener {
            if (initialPosition == 0f) {
                initialPosition = realView.y
                updateInitialPosition()
                content_scroll_view.setOnScrollChangeListener { v: NestedScrollView?,
                                                                scrollX: Int,
                                                                scrollY: Int,
                                                                oldScrollX: Int,
                                                                oldScrollY: Int ->
                    val location = intArrayOf(0, 0)
                    placeholder.getLocationOnScreen(location)
                    if (placeholder.getLocalVisibleRect(scrollBounds)) {
                        if (realView.visibility == View.GONE) {
                            realView.visibility = View.VISIBLE
                        }
                        realView.y = location.get(1).toFloat() - threshold
                        if (location.get(1) < placeholder.height + threshold) {
                            realView.y = initialPosition
                            realView.visibility = View.VISIBLE
                        }
                    } else {
                        realView.y = initialPosition
                        realView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun updateInitialPosition() {
        realView.y = placeholder.y
        realView.requestLayout()
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
