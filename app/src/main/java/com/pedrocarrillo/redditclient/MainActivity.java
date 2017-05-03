package com.pedrocarrillo.redditclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.pedrocarrillo.redditclient.adapter.MainAdapter;
import com.pedrocarrillo.redditclient.adapter.base.DisplayableItem;
import com.pedrocarrillo.redditclient.domain.RedditBigPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditPost;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditResponse;
import com.pedrocarrillo.redditclient.network.RedditClientCallback;
import com.pedrocarrillo.redditclient.network.RedditClientRetrofitCallback;
import com.pedrocarrillo.redditclient.network.RedditClientSubscriber;
import com.pedrocarrillo.redditclient.network.RetrofitManager;
import com.pedrocarrillo.redditclient.ui.custom.HorizontalDividerDecoration;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMain;
    private MainAdapter mainAdapter;

    private Subscription subscription;

    private Random random = new Random();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = (RecyclerView) findViewById(R.id.rv_main);
        List<DisplayableItem> mainList = new ArrayList<>();
        mainAdapter = new MainAdapter(mainList);
        rvMain.addItemDecoration(new HorizontalDividerDecoration(this));
        rvMain.setAdapter(mainAdapter);
        rvMain.setLayoutManager(new LinearLayoutManager(this));

//        subscription = RetrofitManager.getInstance().getRedditApi().getSubreddit("popular")
//                .subscribeOn(Schedulers.io())
//                .flatMap(response -> Observable.from(response.getData().getPosts()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    mainList.add(response);
//                    mainAdapter.notifyDataSetChanged();
//                });

        subscription = RetrofitManager.getInstance().getRedditApi().getSubreddit("popular")
        .subscribeOn(Schedulers.io())
        .flatMap(response -> Observable.from(response.getData().getPosts()))
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
            mainList.add(response);
            mainAdapter.notifyDataSetChanged();
        });

    }
}
