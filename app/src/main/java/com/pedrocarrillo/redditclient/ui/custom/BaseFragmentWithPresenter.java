package com.pedrocarrillo.redditclient.ui.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class BaseFragmentWithPresenter<T extends BasePresenter> extends BaseFragment implements BaseView<T> {

    protected T mPresenter;

    @Override
    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(String error) {
        //TODO: Show error dialog.
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) mPresenter.start();
    }

}
