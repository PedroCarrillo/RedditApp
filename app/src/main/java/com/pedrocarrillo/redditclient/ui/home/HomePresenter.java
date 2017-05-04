package com.pedrocarrillo.redditclient.ui.home;

import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.data.PostsDataSource;
import com.pedrocarrillo.redditclient.domain.RedditBigPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditResponse;
import com.pedrocarrillo.redditclient.network.RetrofitManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
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

    private List<DisplayableItem> displayableItemList;
    private String after;
    private Random random = new Random();

    public HomePresenter(HomeContractor.View view, PostsDataSource postsRepository) {
        this.view = view;
        displayableItemList = new ArrayList<>();
        this.postsRepository = postsRepository;
    }

    @Override
    public void start() {
        view.initView(displayableItemList);
        getRedditPosts(null);
    }

    @Override
    public void loadMore() {
        getRedditPosts(after);
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
                .subscribeOn(Schedulers.io())
                .flatMap(redditData -> {
                    HomePresenter.this.after = redditData.getAfter();
                    return Observable.from(redditData.getPosts());
                })
                .filter(redditPostMetadata -> !redditPostMetadata.getPostData().isNsfw())
                .map(redditPostMetadata -> {
                    int r = random.nextInt(10);
                    if (r % 5 == 0) {
                        return new RedditBigPostMetadata(redditPostMetadata);
                    } else {
                        return redditPostMetadata;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    displayableItemList.add(response);
                    view.addedItem();
                });
    }
}
