package com.pedrocarrillo.redditclient.ui.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class BaseActivityWithPresenter<T extends BasePresenter> extends BaseActivity implements BaseView<T> {

    protected T presenter;

    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String error) {
        //TODO: Show error dialog.
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

}
