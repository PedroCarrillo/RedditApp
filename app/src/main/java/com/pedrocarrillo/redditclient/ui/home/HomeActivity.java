package com.pedrocarrillo.redditclient.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.pedrocarrillo.redditclient.R;
import com.pedrocarrillo.redditclient.adapter.HomeAdapter;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.data.store.list.ListPostsStoreDataSource;
import com.pedrocarrillo.redditclient.data.store.list.ListPostsStoreManager;
import com.pedrocarrillo.redditclient.data.store.list.ListsPostStoreList;
import com.pedrocarrillo.redditclient.network.RetrofitManager;
import com.pedrocarrillo.redditclient.ui.custom.BaseActivityWithPresenter;
import com.pedrocarrillo.redditclient.ui.custom.HorizontalDividerDecoration;
import com.pedrocarrillo.redditclient.ui.custom.LoadMoreRecyclerViewListener;
import com.pedrocarrillo.redditclient.ui.singlePost.PostActivity;

import java.util.List;

public class HomeActivity extends BaseActivityWithPresenter<HomeContractor.Presenter> implements HomeContractor.View, HomeAdapter.OnPostClickListener {

    private RecyclerView rvMain;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        ListPostsStoreDataSource remoteStore = new ListsPostStoreList(RetrofitManager.getInstance().getRedditApi());
        ListPostsStoreDataSource postStoreManager = new ListPostsStoreManager(remoteStore);
        setPresenter(new HomePresenter(this, postStoreManager, isConnected));
        setToolbar();
        presenter.start();
    }

    @Override
    public void initView(List<DisplayableItem> displayableItems) {
        homeAdapter = new HomeAdapter(displayableItems, this);
        rvMain.addItemDecoration(new HorizontalDividerDecoration(this));
        rvMain.setAdapter(homeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMain.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void enableScrollListener() {
        rvMain.addOnScrollListener(new LoadMoreRecyclerViewListener((LinearLayoutManager) rvMain.getLayoutManager(), () -> presenter.loadMore()));
    }

    @Override
    public void onFavoriteClicked(int position) {
        presenter.onFavoriteClicked(position);
    }

    @Override
    public void onPostClicked(int position) {
        presenter.onPostClicked(position);
    }

    @Override
    public void modifiedItem(int position) {
        homeAdapter.notifyItemChanged(position);
    }

    private void setupViews() {
        rvMain = (RecyclerView) findViewById(R.id.rv_main);
    }

    @Override
    public void addedItem() {
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPost(String permalink) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.Companion.getPOST_PERMALINK(), permalink);
        startActivity(intent);
    }
}
