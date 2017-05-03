package com.pedrocarrillo.redditclient.ui.custom;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class LoadMoreRecyclerViewListener extends RecyclerView.OnScrollListener {

    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private LoadMoreListener loadMoreListener;

    public LoadMoreRecyclerViewListener(LinearLayoutManager layoutManager, LoadMoreListener loadMoreListener) {
        this.layoutManager = layoutManager;
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            loading = true;
            loadMoreListener.load();
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public interface LoadMoreListener {
        void load();
    }
}
