package com.pedrocarrillo.redditclient.adapter.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.pedrocarrillo.redditclient.R;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.adapter.base.AdapterDelegate;
import com.pedrocarrillo.redditclient.domain.RedditPost;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by pedrocarrillo on 5/2/17.
 */

public class RedditPostAdapterDelegate extends AdapterDelegate<List<DisplayableItem>>{

    private int viewType;

    public RedditPostAdapterDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getViewType() {
        return viewType;
    }

    @Override
    protected boolean isForViewType(List<DisplayableItem> items, int position) {
        return items.get(position) instanceof RedditPostMetadata;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new RedditPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_post_with_image_row, parent, false));
    }

    @Override
    protected void onBindViewHolder(List<DisplayableItem> items, RecyclerView.ViewHolder holder, int position) {
        RedditPostMetadata redditPostMetadata = (RedditPostMetadata) items.get(position);
        RedditPostViewHolder redditPostViewHolder = (RedditPostViewHolder) holder;
        RedditPost redditPost = redditPostMetadata.getPostData();
        redditPostViewHolder.tvTitle.setText(redditPost.getTitle());
        redditPostViewHolder.tvSubreddit.setText(redditPost.getSubredditNamePrefixed());
        redditPostViewHolder.tvAuthor.setText(redditPost.getAuthor());
        redditPostViewHolder.tvCommentsCount.setText(String.valueOf(redditPost.getNumComments()));
        redditPostViewHolder.tvScore.setText(String.valueOf(redditPost.getScore()));
        Glide.with(redditPostViewHolder.ivThumbnail.getContext()).load(redditPost.getThumbnail()).centerCrop().into(redditPostViewHolder.ivThumbnail);
        Date date = new Date(redditPost.getCreatedAt() * 1000L);
        redditPostViewHolder.tvTime.setReferenceTime(date.getTime());
}

    static class RedditPostViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvSubreddit;
        public RelativeTimeTextView tvTime;
        public TextView tvAuthor;
        public TextView tvCommentsCount;
        public TextView tvScore;
        public ImageView ivThumbnail;

        public RedditPostViewHolder(View view) {
            super(view);
            tvTitle = (TextView)view.findViewById(R.id.tv_title);
            tvSubreddit =(TextView)view.findViewById(R.id.tv_subreddit);
            tvTime =(RelativeTimeTextView)view.findViewById(R.id.tv_time);
            tvAuthor =(TextView)view.findViewById(R.id.tv_user);
            tvCommentsCount = (TextView) view.findViewById(R.id.tv_comments_count);
            tvScore = (TextView)view.findViewById(R.id.tv_upvotes_count);
            ivThumbnail = (ImageView) view.findViewById(R.id.iv_thumb);
        }

    }

}
