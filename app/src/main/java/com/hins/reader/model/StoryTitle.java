package com.hins.reader.model;

/**
 * Created by Hins on 2017/10/10.
 */

public class StoryTitle {

    private String mTitle;
    private String mUrl;
    private int mId;

    public StoryTitle(){}

    public StoryTitle(String title, String url) {
        mTitle = title;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }
}
