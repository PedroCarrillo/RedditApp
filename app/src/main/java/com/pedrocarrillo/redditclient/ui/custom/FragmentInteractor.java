package com.pedrocarrillo.redditclient.ui.custom;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public interface FragmentInteractor {

    void finish();

    void setTitle(String title);

    void replaceFragment(Fragment fragment, boolean addToBackStack);

    void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack);

    void showError(String message);

    void setToolbar(Toolbar toolbar);

}
