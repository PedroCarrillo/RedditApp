package com.pedrocarrillo.redditclient.adapter.delegates;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.pedrocarrillo.redditclient.R;
import com.pedrocarrillo.redditclient.adapter.HomeAdapter;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.adapter.base.AdapterDelegate;
import com.pedrocarrillo.redditclient.domain.RedditContentData;
import com.pedrocarrillo.redditclient.domain.RedditContent;

import java.util.Date;
import java.util.List;

/**
 * Created by pedrocarrillo on 5/2/17.
 */

public class RedditPostAdapterDelegate extends AdapterDelegate<List<DisplayableItem>>{

    private int viewType;
    private HomeAdapter.OnPostClickListener onPostClickListener;

    public RedditPostAdapterDelegate(int viewType, HomeAdapter.OnPostClickListener onPostClickListener) {
        this.viewType = viewType;
        this.onPostClickListener = onPostClickListener;
    }

    @Override
    public int getViewType() {
        return viewType;
    }

    @Override
    protected boolean isForViewType(List<DisplayableItem> items, int position) {
        return items.get(position) instanceof RedditContent && !((RedditContent)items.get(position)).isBigPost();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new RedditPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_post_with_image_row, parent, false), onPostClickListener);
    }

    @Override
    protected void onBindViewHolder(List<DisplayableItem> items, RecyclerView.ViewHolder holder, int position) {
        RedditContent redditContent = (RedditContent) items.get(position);
        RedditPostViewHolder redditPostViewHolder = (RedditPostViewHolder) holder;
        RedditContentData redditContentData = redditContent.getRedditContentData();
        redditPostViewHolder.tvTitle.setText(redditContentData.getTitle());
        redditPostViewHolder.tvSubreddit.setText(redditContentData.getSubredditNamePrefixed());
        redditPostViewHolder.tvAuthor.setText(redditContentData.getAuthor());
        redditPostViewHolder.tvCommentsCount.setText(String.valueOf(redditContentData.getNumComments()));
        redditPostViewHolder.tvScore.setText(String.valueOf(redditContentData.getScore()));
        Glide.with(redditPostViewHolder.ivThumbnail.getContext()).load(redditContentData.getThumbnail()).centerCrop().into(redditPostViewHolder.ivThumbnail);
        Date date = new Date(redditContentData.getCreatedAt() * 1000L);
        redditPostViewHolder.tvTime.setReferenceTime(date.getTime());
        if(redditContent.isFavorite()) {
            redditPostViewHolder.ivFavorite.setBackground(ContextCompat.getDrawable(redditPostViewHolder.tvTitle.getContext(), R.drawable.ic_favorite_black_24dp));
        } else {
            redditPostViewHolder.ivFavorite.setBackground(ContextCompat.getDrawable(redditPostViewHolder.tvTitle.getContext(), R.drawable.ic_favorite_border_black_24dp));
        }
}

    static class RedditPostViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvSubreddit;
        public RelativeTimeTextView tvTime;
        public TextView tvAuthor;
        public TextView tvCommentsCount;
        public TextView tvScore;
        public ImageView ivThumbnail;
        public ImageView ivFavorite;

        public RedditPostViewHolder(View view, HomeAdapter.OnPostClickListener onPostClickListener) {
            super(view);
            tvTitle = (TextView)view.findViewById(R.id.tv_title);
            tvSubreddit =(TextView)view.findViewById(R.id.tv_subreddit);
            tvTime =(RelativeTimeTextView)view.findViewById(R.id.tv_time);
            tvAuthor =(TextView)view.findViewById(R.id.tv_user);
            tvCommentsCount = (TextView) view.findViewById(R.id.tv_comments_count);
            tvScore = (TextView)view.findViewById(R.id.tv_upvotes_count);
            ivThumbnail = (ImageView) view.findViewById(R.id.iv_thumb);
            ivFavorite = (ImageView) view.findViewById(R.id.iv_favorite);
            ivFavorite.setOnClickListener(favoriteView -> onPostClickListener.onFavoriteClicked(getAdapterPosition()));
            view.setOnClickListener(itemview -> onPostClickListener.onPostClicked(getAdapterPosition()));
        }

    }

}
