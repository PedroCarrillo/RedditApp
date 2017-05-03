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

        subscription = RetrofitManager.getInstance().getRedditApi().getSubreddit("popular")
                .subscribeOn(Schedulers.io())
                .flatMap(response -> Observable.from(response.getData().getPosts()))
//                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mainList.add(response);
                    mainAdapter.notifyDataSetChanged();
                });
//                .flatMap(redditPostMetadata -> RetrofitManager.getInstance().getRedditApi().getSubreddit(redditPostMetadata.getPostData().getSubreddit()))
//                .subscribe((RedditResponse r) -> Log.d("res" , r.getKind()), e -> Log.e("excp", e.getLocalizedMessage()));


//        Subscription subscription = RetrofitManager.getInstance().getRedditApi().getSubreddit("popular")
//                .subscribeOn(Schedulers.io())
//                .flatMapIterable(list -> list.getData().getPosts())
//                .doOnNext(redditPostMetadata -> {
//                    Log.d("TESTING", ":) "+ redditPostMetadata.getPostData().getTitle());
//                    Subscription subscription1 = RetrofitManager.getInstance().getRedditApi().getSubreddit(redditPostMetadata.getPostData().getSubreddit())
//                            .subscribe();
//                }).subscribe(new Subscriber<RedditPostMetadata>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(RedditPostMetadata redditPostMetadata) {
//                        Log.e("asd", redditPostMetadata.getPostData().getTitle());
//                    }
//                });

//                .subscribe(new RedditClientSubscriber<RedditResponse>(new Subscriber<RedditResponse>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("a", e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onNext(RedditResponse redditResponse) {
//                        for (RedditPostMetadata postMetadata : redditResponse.getData().getPosts()) {
//                            Log.e("test", postMetadata.getPostData().getTitle());
//                        }
//                    }
//                }));





//                Call < RedditResponse > call = RetrofitManager.getInstance().getRedditApi().getSubreddit("nintendoswitch");
//        call.enqueue(new RedditClientRetrofitCallback<RedditResponse>(new RedditClientCallback<RedditResponse>() {
//            @Override
//            public void onSuccess(RedditResponse redditResponse) {
//                for (RedditPostMetadata postMetadata : redditResponse.getData().getPosts()) {
//                    Log.e("test", postMetadata.getPostData().getTitle());
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//
//            }
//        }));

    }
}
