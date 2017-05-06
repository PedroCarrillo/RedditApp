package com.pedrocarrillo.redditclient.ui.home;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.impl.RealStore;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.data.PostsDataSource;
import com.pedrocarrillo.redditclient.data.store.PostsStoreDataSource;
import com.pedrocarrillo.redditclient.data.store.RedditPostRealStore;
import com.pedrocarrillo.redditclient.data.store.RedditPostsRequest;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditResponse;
import com.pedrocarrillo.redditclient.network.RedditApi;
import com.pedrocarrillo.redditclient.network.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class HomePresenter implements HomeContractor.Presenter {

    private HomeContractor.View view;
    private Subscription subscription;

    private boolean isInternetAvailable;
    private List<DisplayableItem> displayableItemList;
    private PostsStoreDataSource postsRepository;

    public HomePresenter(HomeContractor.View view, PostsStoreDataSource postsRepository, boolean isInternetAvailable) {
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
                postsRepository.getPosts("popular")
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(redditPostMetadata ->{
                    displayableItemList.add(redditPostMetadata);
                    view.addedItem();
                }, e -> view.showError(e.getLocalizedMessage()));
    }

    private void getPaginatedPosts() {
        subscription = postsRepository.getPaginatedPosts("popular")
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(redditPostMetadata ->{
                    displayableItemList.add(redditPostMetadata);
                    view.addedItem();
                }, e -> view.showError(e.getLocalizedMessage()));
    }
}
