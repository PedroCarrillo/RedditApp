package com.pedrocarrillo.redditclient.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by pedrocarrillo on 5/2/17.
 */

public class AdapterDelegateManager<T> {

    private SparseArray<AdapterDelegate<T>> adapterDelegateList;

    public AdapterDelegateManager() {
        adapterDelegateList = new SparseArray<>();
    }

    public void addDelegate(AdapterDelegate<T> adapterDelegate) {
        adapterDelegateList.put(adapterDelegate.getViewType(), adapterDelegate);
    }

    public void deleteDelegate(AdapterDelegate<T> adapterDelegate) {
        if (adapterDelegateList.size() != 0) {
            adapterDelegateList.remove(adapterDelegate.getViewType());
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate adapterDelegate = getAdapterDelegateByViewType(viewType);
        if (adapterDelegate != null) {
            return adapterDelegate.onCreateViewHolder(parent);
        } else {
            return null;
        }
    }

    public void onBindViewHolder(T items, RecyclerView.ViewHolder viewHolder, int position){
        AdapterDelegate<T> adapterDelegate = getAdapterDelegateByViewType(viewHolder.getItemViewType());
        if (adapterDelegate != null) {
            adapterDelegate.onBindViewHolder(items, viewHolder, position);
        }
    }

    private AdapterDelegate getAdapterDelegateByViewType(int viewType) {
        if (adapterDelegateList.size() > 0) {
            return adapterDelegateList.get(viewType);
        } else {
            return null;
        }
    }


    public int getItemViewType(T items, int position) {
        if (adapterDelegateList.size() > 0) {
            for(int i = 0; i < adapterDelegateList.size(); i++) {
                AdapterDelegate<T> adapterDelegate = adapterDelegateList.valueAt(i);
                if(adapterDelegate.isForViewType(items, position)) {
                    return adapterDelegate.getViewType();
                }
            }
            return -1;
        } else {
            return -1;
        }
    }

}
