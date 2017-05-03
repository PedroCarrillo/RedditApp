package com.pedrocarrillo.redditclient.domain;

import java.util.List;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class Preview {

    private List<RedditImage> images;
    private boolean enabled;

    public List<RedditImage> getImages() {
        return images;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
