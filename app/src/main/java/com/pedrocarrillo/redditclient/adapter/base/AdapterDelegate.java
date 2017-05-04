package com.pedrocarrillo.redditclient.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by pedrocarrillo on 5/2/17.
 */

public abstract class AdapterDelegate<T> {

    protected abstract int getViewType();

    protected abstract boolean isForViewType(T items, int position);

    protected abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    protected abstract void onBindViewHolder(T items, RecyclerView.ViewHolder holder, int position);

}
