package com.hins.reader.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hins on 2017/10/10.
 */

public class News {

    private String date;
    private List<Story> stories;

    @SerializedName("top_stories")
    private List<TopStory> topStories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<TopStory> getTopStories() {
        return topStories;
    }

    public void setTopStories(List<TopStory> topStories) {
        this.topStories = topStories;
    }
}
