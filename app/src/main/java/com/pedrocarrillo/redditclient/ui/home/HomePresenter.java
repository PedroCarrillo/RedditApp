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
    private String after;
    private Random random = new Random();

    public HomePresenter(HomeContractor.View view, PostsDataSource postsRepository, boolean isInternetAvailable) {
        this.view = view;
        displayableItemList = new ArrayList<>();
        this.postsRepository = postsRepository;
        this.isInternetAvailable = isInternetAvailable;
    }

    @Override
    public void start() {
        view.initView(displayableItemList);
        getRedditPosts(null);
        if (isInternetAvailable) {
            view.enableScrollListener();
        }
    }

    @Override
    public void loadMore() {
        getRedditPosts(after);
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

    private void getRedditPosts(String after) {
        subscription =
                postsRepository.getPosts(after)
                .flatMap(redditData -> {
                    HomePresenter.this.after = redditData.getAfter();
                    return Observable.from(redditData.getPosts());
                })
                .filter(redditPostMetadata -> !redditPostMetadata.getPostData().isNsfw())
                .map(redditPostMetadata -> {
                    int r = random.nextInt(10);
                    if (r % 5 == 0) {
                        redditPostMetadata.setBigPost(true);
                        return redditPostMetadata;
                    } else {
                        return redditPostMetadata;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RedditPostMetadata>() {
                               @Override
                               public void onCompleted() {
                                   if (!isInternetAvailable) unsubscribe();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   view.showError(e.getLocalizedMessage());
                               }

                               @Override
                               public void onNext(RedditPostMetadata redditPostMetadata) {
                                    displayableItemList.add(redditPostMetadata);
                                    view.addedItem();
                               }
                });

    }
}
