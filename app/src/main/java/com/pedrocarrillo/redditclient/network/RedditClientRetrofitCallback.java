package com.pedrocarrillo.redditclient.network;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditClientRetrofitCallback<T> implements Callback<T> {

    private RedditClientCallback<T> clientCallback;

    public RedditClientRetrofitCallback(RedditClientCallback<T> clientCallback){
        this.clientCallback = clientCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            clientCallback.onSuccess(response.body());
        } else {
            try {
                clientCallback.onFailure(response.errorBody().string());
            } catch (IOException e) {
                clientCallback.onFailure("Network Error");
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        clientCallback.onFailure(throwable.getLocalizedMessage());
    }

}
