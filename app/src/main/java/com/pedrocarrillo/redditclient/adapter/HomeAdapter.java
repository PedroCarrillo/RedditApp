package com.pedrocarrillo.redditclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pedrocarrillo.redditclient.adapter.base.AdapterDelegateManager;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.adapter.delegates.RedditBigPostDelegate;
import com.pedrocarrillo.redditclient.adapter.delegates.RedditPostAdapterDelegate;

import java.util.List;

/**
 * Created by pedrocarrillo on 5/2/17.
 */

public class HomeAdapter extends RecyclerView.Adapter {

    private final int TYPE_REDDIT_POST = 1;
    private final int TYPE_BIG_REDDIT_POST = 2;

    private List<DisplayableItem> items;

    private AdapterDelegateManager<List<DisplayableItem>> adapterDelegateManager;

    public HomeAdapter(List<DisplayableItem> items) {
        this.items = items;
        adapterDelegateManager = new AdapterDelegateManager<>();
        adapterDelegateManager.addDelegate(new RedditBigPostDelegate(TYPE_BIG_REDDIT_POST));
        adapterDelegateManager.addDelegate(new RedditPostAdapterDelegate(TYPE_REDDIT_POST));
    }

    @Override
    public int getItemViewType(int position) {
        return adapterDelegateManager.getItemViewType(items, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapterDelegateManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapterDelegateManager.onBindViewHolder(items, holder, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
