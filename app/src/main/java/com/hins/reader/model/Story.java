package com.hins.reader.model;

import java.util.List;

/**
 * Created by Hins on 2017/10/10.
 */

public class Story {

    /**
     * images : ["https://pic3.zhimg.com/v2-12849caa559f7b7691d0a3f4895cd3ea.jpg"]
     * type : 0
     * id : 9565082
     * ga_prefix : 101009
     * title : 总算知道，为什么有些简历能从几千份简历里脱颖而出了
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
