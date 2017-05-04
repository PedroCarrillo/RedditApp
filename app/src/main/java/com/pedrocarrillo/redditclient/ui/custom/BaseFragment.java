package com.pedrocarrillo.redditclient.ui.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class BaseFragment extends Fragment {

    protected FragmentInteractor fragmentInteractor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInteractor = (FragmentInteractor) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractor = null;
    }

    public String getStringFromResourceId(@StringRes int stringRef) {
        return getContext().getResources().getString(stringRef);
    }

    public void onBackPressed() {}

}
