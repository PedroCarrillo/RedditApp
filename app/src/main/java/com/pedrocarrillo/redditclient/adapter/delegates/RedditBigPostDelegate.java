package com.pedrocarrillo.redditclient.adapter.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pedrocarrillo.redditclient.R;
import com.pedrocarrillo.redditclient.adapter.base.AdapterDelegate;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.domain.RedditBigPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditImage;
import com.pedrocarrillo.redditclient.domain.RedditPost;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;

import java.util.Date;
import java.util.List;

/**
 * Created by pedrocarrillo on 5/2/17.
 */

public class RedditBigPostDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private int viewType;

    public RedditBigPostDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getViewType() {
        return viewType;
    }

    @Override
    protected boolean isForViewType(List<DisplayableItem> items, int position) {
        return items.get(position) instanceof RedditBigPostMetadata;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new RedditBigPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_post_with_big_image_row, parent, false));
    }

    @Override
    protected void onBindViewHolder(List<DisplayableItem> items, RecyclerView.ViewHolder holder, int position) {
        RedditBigPostMetadata redditPostMetadata = (RedditBigPostMetadata) items.get(position);
        RedditBigPostDelegate.RedditBigPostViewHolder redditPostViewHolder = (RedditBigPostDelegate.RedditBigPostViewHolder) holder;
        RedditPost redditPost = redditPostMetadata.getPostData();
        redditPostViewHolder.tvTitle.setText(redditPost.getTitle());
        redditPostViewHolder.tvSubreddit.setText(redditPost.getSubredditNamePrefixed());
        redditPostViewHolder.tvAuthor.setText(redditPost.getAuthor());
        redditPostViewHolder.tvCommentsCount.setText(String.valueOf(redditPost.getNumComments()));
        redditPostViewHolder.tvScore.setText(String.valueOf(redditPost.getScore()));
        List<RedditImage> redditImages = redditPost.getPreview().getImages();
        if (!redditImages.isEmpty()) {
            Glide.with(redditPostViewHolder.ivBigImage.getContext()).load(redditPost.getPreview().getImages().get(0).getSource().getUrl()).centerCrop().into(redditPostViewHolder.ivBigImage);
        }
        Date date = new Date(redditPost.getCreatedAt() * 1000L);
        redditPostViewHolder.tvTime.setReferenceTime(date.getTime());
    }

    static class RedditBigPostViewHolder extends RedditPostAdapterDelegate.RedditPostViewHolder {

        public ImageView ivBigImage;

        public RedditBigPostViewHolder(View view) {
            super(view);
            ivBigImage = (ImageView) view.findViewById(R.id.iv_big_image);
        }

    }

}
