package com.pedrocarrillo.redditclient.ui.custom;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);
    void showError(String error);

}
