package com.pedrocarrillo.redditclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pedrocarrillo.redditclient.domain.RedditPost;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;
import com.pedrocarrillo.redditclient.domain.RedditResponse;
import com.pedrocarrillo.redditclient.network.RedditClientCallback;
import com.pedrocarrillo.redditclient.network.RedditClientRetrofitCallback;
import com.pedrocarrillo.redditclient.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<RedditResponse> call = RetrofitManager.getInstance().getRedditApi().getSubreddit("nintendoswitch");
        call.enqueue(new RedditClientRetrofitCallback<RedditResponse>(new RedditClientCallback<RedditResponse>() {
            @Override
            public void onSuccess(RedditResponse redditResponse) {
                for (RedditPostMetadata postMetadata : redditResponse.getData().getPosts()) {
                    Log.e("test", postMetadata.getPostData().getTitle());
                }
            }

            @Override
            public void onFailure(String error) {

            }
        }));

    }
}
