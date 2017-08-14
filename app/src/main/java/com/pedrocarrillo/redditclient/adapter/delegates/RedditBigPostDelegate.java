package com.pedrocarrillo.redditclient.adapter.delegates;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pedrocarrillo.redditclient.R;
import com.pedrocarrillo.redditclient.adapter.HomeAdapter;
import com.pedrocarrillo.redditclient.adapter.base.AdapterDelegate;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.domain.RedditImage;
import com.pedrocarrillo.redditclient.domain.RedditContentData;
import com.pedrocarrillo.redditclient.domain.RedditContent;

import java.util.Date;
import java.util.List;

/**
 * Created by pedrocarrillo on 5/2/17.
 */

public class RedditBigPostDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private int viewType;
    private HomeAdapter.OnPostClickListener onPostClickListener;

    public RedditBigPostDelegate(int viewType, HomeAdapter.OnPostClickListener onPostClickListener) {
        this.viewType = viewType;
        this.onPostClickListener = onPostClickListener;
    }

    @Override
    public int getViewType() {
        return viewType;
    }

    @Override
    protected boolean isForViewType(List<DisplayableItem> items, int position) {
        return items.get(position) instanceof RedditContent && ((RedditContent)items.get(position)).isBigPost();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new RedditBigPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_post_with_big_image_row, parent, false), onPostClickListener);
    }

    @Override
    protected void onBindViewHolder(List<DisplayableItem> items, RecyclerView.ViewHolder holder, int position) {
        RedditContent redditContent = (RedditContent) items.get(position);
        RedditBigPostDelegate.RedditBigPostViewHolder redditPostViewHolder = (RedditBigPostDelegate.RedditBigPostViewHolder) holder;
        RedditContentData redditContentData = redditContent.getRedditContentData();
        redditPostViewHolder.tvTitle.setText(redditContentData.getTitle());
        redditPostViewHolder.tvSubreddit.setText(redditContentData.getSubredditNamePrefixed());
        redditPostViewHolder.tvAuthor.setText(redditContentData.getAuthor());
        redditPostViewHolder.tvCommentsCount.setText(String.valueOf(redditContentData.getNumComments()));
        redditPostViewHolder.tvScore.setText(String.valueOf(redditContentData.getScore()));
        if (redditContentData.getPreview() != null && redditContentData.getPreview().getImages() != null) {
            List<RedditImage> redditImages = redditContentData.getPreview().getImages();
            if (!redditImages.isEmpty()) {
                Glide.with(redditPostViewHolder.ivBigImage.getContext()).load(redditContentData.getPreview().getImages().get(0).getSource().getUrl()).centerCrop().into(redditPostViewHolder.ivBigImage);
            }
        }
        Date date = new Date(redditContentData.getCreatedAt() * 1000L);
        redditPostViewHolder.tvTime.setReferenceTime(date.getTime());
        if(redditContent.isFavorite()) {
            redditPostViewHolder.ivFavorite.setBackground(ContextCompat.getDrawable(redditPostViewHolder.tvTitle.getContext(), R.drawable.ic_favorite_black_24dp));
        } else {
            redditPostViewHolder.ivFavorite.setBackground(ContextCompat.getDrawable(redditPostViewHolder.tvTitle.getContext(), R.drawable.ic_favorite_border_black_24dp));
        }
    }

    static class RedditBigPostViewHolder extends RedditPostAdapterDelegate.RedditPostViewHolder {

        public ImageView ivBigImage;

        public RedditBigPostViewHolder(View view, HomeAdapter.OnPostClickListener onPostClickListener) {
            super(view, onPostClickListener);
            ivBigImage = (ImageView) view.findViewById(R.id.iv_big_image);
        }

    }

}
