package com.pedrocarrillo.redditclient;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class RedditClientApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
