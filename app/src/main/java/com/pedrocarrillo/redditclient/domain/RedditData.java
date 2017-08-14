package com.pedrocarrillo.redditclient.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pedrocarrillo on 4/30/17.
 */

public class RedditData {

    private String modhash;
    @SerializedName("children")
    private List<RedditContent> children;
    private String after;
    private String before;

    public String getModhash() {
        return modhash;
    }

    public List<RedditContent> getChildren() {
        return children;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public void setChildren(List<RedditContent> children) {
        this.children = children;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
