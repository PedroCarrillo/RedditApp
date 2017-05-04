package com.pedrocarrillo.redditclient.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pedrocarrillo.redditclient.R;
import com.pedrocarrillo.redditclient.adapter.HomeAdapter;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.ui.custom.BaseActivityWithPresenter;
import com.pedrocarrillo.redditclient.ui.custom.HorizontalDividerDecoration;
import com.pedrocarrillo.redditclient.ui.custom.LoadMoreRecyclerViewListener;

import java.util.List;

public class HomeActivity extends BaseActivityWithPresenter<HomeContractor.Presenter> implements HomeContractor.View {

    private RecyclerView rvMain;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setPresenter(new HomePresenter(this));
        presenter.start();
    }

    @Override
    public void initView(List<DisplayableItem> displayableItems) {
        homeAdapter = new HomeAdapter(displayableItems);
        rvMain.addItemDecoration(new HorizontalDividerDecoration(this));
        rvMain.setAdapter(homeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMain.setLayoutManager(linearLayoutManager);

        rvMain.addOnScrollListener(new LoadMoreRecyclerViewListener(linearLayoutManager, new LoadMoreRecyclerViewListener.LoadMoreListener() {
            @Override
            public void load() {
                presenter.loadMore();
            }
        }));

    }

    private void setupViews() {
        rvMain = (RecyclerView) findViewById(R.id.rv_main);
    }

    @Override
    public void addedItem() {
        homeAdapter.notifyDataSetChanged();
    }

}
