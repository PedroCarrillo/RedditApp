package com.pedrocarrillo.redditclient.ui.singlePost;

import android.os.Bundle;
import android.util.Log;

import com.pedrocarrillo.redditclient.R;
import com.pedrocarrillo.redditclient.data.store.post.PostStore;
import com.pedrocarrillo.redditclient.network.RetrofitManager;
import com.pedrocarrillo.redditclient.ui.custom.BaseActivityWithPresenter;
import com.pedrocarrillo.redditclient.ui.home.HomeContractor;

/**
 * @author pedrocarrillo.
 */

public class PostActivity extends BaseActivityWithPresenter<PostContractor.Presenter>
        implements PostContractor.View {

    public static final String POST_PERMALINK = "_post_permalink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setupViews();
        PostStore postStore = new PostStore(RetrofitManager.getInstance().getRedditApi());
        setPresenter(new PostPresenter(this, postStore));
        initData();
    }

    private void initData() {
        String permalink = getIntent().getStringExtra(POST_PERMALINK);
        presenter.loadInfo(permalink);
    }

    private void setupViews() {

    }

    @Override
    public void showInfo() {

    }
}
