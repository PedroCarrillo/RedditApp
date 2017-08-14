package com.pedrocarrillo.redditclient

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import com.pedrocarrillo.redditclient.domain.Comment
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_expandable_comment.view.*

/**
 * @author pedrocarrillo.
 */
open class ExpandableCommentItem constructor(
        private val comment : Comment,
        private val depth : Int) : Item<ViewHolder>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(viewHolder: ViewHolder, position: Int) {
        addingDepthViews(viewHolder)

        viewHolder.itemView.tv_user.setText(comment.author)
        viewHolder.itemView.body.setText(Html.fromHtml(comment.body))
        viewHolder.itemView.tv_votes.setText(comment.score.toString())
        viewHolder.itemView.apply {
            setOnLongClickListener {
                expandableGroup.onToggleExpanded()
                true
            }
        }
    }

    private fun addingDepthViews(viewHolder: ViewHolder) {
        viewHolder.itemView.separatorContainer.removeAllViews()
        viewHolder.itemView.separatorContainer.visibility =
                if (depth > 0) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        for (i in 1..depth) {
            val v : View = LayoutInflater.from(viewHolder.itemView.context)
                    .inflate(R.layout.layout_separator_view, viewHolder.itemView.separatorContainer, false)
            viewHolder.itemView.separatorContainer.addView(v);
        }
        viewHolder.itemView.body.requestLayout()
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    override fun getLayout(): Int {
        return R.layout.item_expandable_comment
    }

}