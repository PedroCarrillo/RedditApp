package com.pedrocarrillo.redditclient.ui.home;

import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.ui.custom.BasePresenter;
import com.pedrocarrillo.redditclient.ui.custom.BaseView;

import java.util.List;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public interface HomeContractor {

    interface View extends BaseView<HomeContractor.Presenter> {

        void addedItem();

        void modifiedItem(int position);

        void enableScrollListener();

        void initView(List<DisplayableItem> displayableItems);

        void showPost(String permalink);
    }

    interface Presenter extends BasePresenter {

        void loadMore();

        void onPostClicked(int position);

        void onFavoriteClicked(int position);
    }

}
