package com.pedrocarrillo.redditclient.ui.home;

import android.util.Log;

import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.data.PostsDataSource;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class HomePresenter implements HomeContractor.Presenter {

    private HomeContractor.View view;
    private PostsDataSource postsRepository;
    private Subscription subscription;

    private boolean isInternetAvailable;
    private List<DisplayableItem> displayableItemList;

    public HomePresenter(HomeContractor.View view, PostsDataSource postsRepository, boolean isInternetAvailable) {
        this.view = view;
        displayableItemList = new ArrayList<>();
        this.postsRepository = postsRepository;
        this.isInternetAvailable = isInternetAvailable;
    }

    @Override
    public void start() {
        view.initView(displayableItemList);
        getRedditPosts();
        if (isInternetAvailable) {
            view.enableScrollListener();
        }
    }

    @Override
    public void loadMore() {
        getPaginatedPosts();
    }

    @Override
    public void onPostClicked(int position) {

    }

    @Override
    public void onFavoriteClicked(int position) {
        DisplayableItem item = displayableItemList.get(position);
        RedditPostMetadata redditPostMetadata = (RedditPostMetadata) item;
        redditPostMetadata.setFavorite(!redditPostMetadata.isFavorite());
        postsRepository.setFavorite(redditPostMetadata, redditPostMetadata.isFavorite());
        view.modifiedItem(position);
    }

    @Override
    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void getRedditPosts() {
        subscription =
                postsRepository.getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(redditPostMetadata ->{
                    displayableItemList.add(redditPostMetadata);
                    view.addedItem();
                }, e -> view.showError(e.getLocalizedMessage()));
    }

    private void getPaginatedPosts() {
        subscription = postsRepository.getPaginatedPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(redditPostMetadata ->{
                    displayableItemList.add(redditPostMetadata);
                    view.addedItem();
                }, e -> view.showError(e.getLocalizedMessage()));
    }
}
