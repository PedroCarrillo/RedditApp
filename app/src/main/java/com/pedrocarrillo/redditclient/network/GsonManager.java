package com.pedrocarrillo.redditclient.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pedrocarrillo.redditclient.adapter.RedditContentDataParser;
import com.pedrocarrillo.redditclient.domain.RedditContentData;

/**
 * @author pedrocarrillo.
 */

public class GsonManager {
    private static final GsonManager ourInstance = new GsonManager();

    private final Gson gson;

    public static GsonManager getInstance() {
        return ourInstance;
    }

    private GsonManager() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RedditContentData.class, new RedditContentDataParser());
//        gsonBuilder.registerTypeAdapter(RedditContentData.class, new RedditContentDataTypeAdapter());
//        gsonBuilder.registerTypeAdapter(RedditResponse.class, new RedditResponseTypeAdapter());
        gson = gsonBuilder.create();
    }

    public Gson getGson() {
        return gson;
    }

}
