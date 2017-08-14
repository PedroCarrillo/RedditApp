package com.pedrocarrillo.redditclient.adapter

import com.pedrocarrillo.redditclient.ExpandableCommentItem
import com.pedrocarrillo.redditclient.domain.Comment
import com.xwray.groupie.ExpandableGroup

/**
 * @author pedrocarrillo.
 */
class ExpandableCommentGroup constructor(
        comment : Comment,
        depth : Int = 0) : ExpandableGroup(ExpandableCommentItem(comment, depth)) {

    init {
        for (comm in comment.children) {
            add(ExpandableCommentGroup(comm, depth.plus(1)))
        }
    }

}
