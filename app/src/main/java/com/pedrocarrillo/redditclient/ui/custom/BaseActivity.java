package com.pedrocarrillo.redditclient.ui.custom;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pedrocarrillo.redditclient.R;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class BaseActivity extends AppCompatActivity implements FragmentInteractor {

    @Nullable
    protected Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        replaceFragment(R.id.main_content, fragment, addToBackStack);
    }

    @Override
    public void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        transaction.replace(containerId, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showError(String message) {
    }

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected void setToolbar() {
        setSupportActionBar(toolbar);
    }

    protected void setNavigationToolbar() {
        setToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateBack();
                }
            });
        }
    }

    @Override
    public void setToolbar(Toolbar customToolbar) {
        setSupportActionBar(customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (customToolbar != null) {
            customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected void navigateBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        BaseFragment baseFragment = (BaseFragment)getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (baseFragment != null) {
            baseFragment.onBackPressed();
        }
        super.onBackPressed();
    }

}