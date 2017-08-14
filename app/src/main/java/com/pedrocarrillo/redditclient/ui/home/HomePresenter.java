package com.pedrocarrillo.redditclient.ui.home;

import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.data.store.list.ListPostsStoreDataSource;
import com.pedrocarrillo.redditclient.domain.RedditContent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class HomePresenter implements HomeContractor.Presenter {

    private HomeContractor.View view;
    private Subscription subscription;

    private boolean isInternetAvailable;
    private List<DisplayableItem> displayableItemList;
    private ListPostsStoreDataSource postsRepository;

    public HomePresenter(HomeContractor.View view, ListPostsStoreDataSource postsRepository, boolean isInternetAvailable) {
        this.view = view;
        displayableItemList = new ArrayList<>();
        this.postsRepository = postsRepository;
        this.isInternetAvailable = isInternetAvailable;
    }

    @Override
    public void start() {
        view.initView(displayableItemList);
        getRedditPosts();
//        getPaginatedPosts();
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
        DisplayableItem item = displayableItemList.get(position);
        RedditContent redditContent = (RedditContent) item;
        view.showPost(redditContent.getRedditContentData().getPermalink());
    }

    @Override
    public void onFavoriteClicked(int position) {
        DisplayableItem item = displayableItemList.get(position);
        RedditContent redditContent = (RedditContent) item;
        redditContent.setFavorite(!redditContent.isFavorite());
//        postsRepository.setFavorite(redditPostMetadata, redditPostMetadata.isFavorite());
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
                postsRepository.getPosts("nintendoswitch")
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(redditPostMetadata ->{
                    displayableItemList.add(redditPostMetadata);
                    view.addedItem();
                }, e -> view.showError(e.getLocalizedMessage()));
    }

    private void getPaginatedPosts() {
        subscription = postsRepository.getPaginatedPosts("nintendoswitch")
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(redditPostMetadata ->{
                    displayableItemList.add(redditPostMetadata);
                    view.addedItem();
                }, e -> view.showError(e.getLocalizedMessage()));
    }
}
