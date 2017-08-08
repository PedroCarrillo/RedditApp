package com.pedrocarrillo.redditclient.ui.singlePost;

import com.pedrocarrillo.redditclient.ui.custom.BasePresenter;
import com.pedrocarrillo.redditclient.ui.custom.BaseView;

/**
 * @author pedrocarrillo.
 */

public interface PostContractor {

    interface View extends BaseView<PostContractor.Presenter> {

        void showInfo();

    }

    interface Presenter extends BasePresenter {

        void loadInfo(String permalink);

    }

}
