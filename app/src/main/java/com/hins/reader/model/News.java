package com.hins.reader.model;

import java.util.List;

/**
 * Created by Hins on 2017/10/10.
 */

public class News {

    /**
     * date : 20171010
     * stories : [{"images":["https://pic3.zhimg.com/v2-12849caa559f7b7691d0a3f4895cd3ea.jpg"],"type":0,"id":9565082,"ga_prefix":"101009","title":"总算知道，为什么有些简历能从几千份简历里脱颖而出了"},{"images":["https://pic2.zhimg.com/v2-9c1b94c74b74348e430a3e07a98863e1.jpg"],"type":0,"id":9647010,"ga_prefix":"101008","title":"看看马再看看自己，感觉人类的进化好失败啊"},{"images":["https://pic3.zhimg.com/v2-e94b83f2ef873cd66d1bf0d66d7ba65e.jpg"],"type":0,"id":9647004,"ga_prefix":"101007","title":"大家好，给大家介绍一下，上次中科院走失的那个「气球」"},{"images":["https://pic4.zhimg.com/v2-f1367d2708dba4e4a79b3b423b128ba3.jpg"],"type":0,"id":9647039,"ga_prefix":"101007","title":"「2030 年德国要禁售燃油车」，这个新闻是假的"},{"images":["https://pic1.zhimg.com/v2-12c3cc9856e6abec47c82b532f41ef9c.jpg"],"type":0,"id":9634341,"ga_prefix":"101007","title":"为什么北京很多房子上，总要加个这样的「大屋顶」？"},{"images":["https://pic2.zhimg.com/v2-c9d3391c5145308432c4616151aac2b5.jpg"],"type":0,"id":9646096,"ga_prefix":"101006","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic2.zhimg.com/v2-7c82c271dfb95525cc9d1d4dba53b6d9.jpg","type":0,"id":9565082,"ga_prefix":"101009","title":"总算知道，为什么有些简历能从几千份简历里脱颖而出了"},{"image":"https://pic2.zhimg.com/v2-55e13258dfe354fc1390e797db260509.jpg","type":0,"id":9647039,"ga_prefix":"101007","title":"「2030 年德国要禁售燃油车」，这个新闻是假的"},{"image":"https://pic1.zhimg.com/v2-d2921dcbb8b4db28525fa2e25aa1648c.jpg","type":0,"id":9646910,"ga_prefix":"100915","title":"解开一件古董首饰的身世之谜，就像夏洛克探案一样严谨有趣"},{"image":"https://pic1.zhimg.com/v2-caf6c8e235dbdfd4e6cb471bd7f8379c.jpg","type":0,"id":9616039,"ga_prefix":"100916","title":"有两样看似平常的东西，影响力却一直被我们低估"},{"image":"https://pic1.zhimg.com/v2-54dfdff28df864bd9e717731cca3ca1c.jpg","type":0,"id":9593150,"ga_prefix":"100918","title":"恋爱中，你有过不想背的「人设」吗？"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
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

    public static class TopStoriesBean {
        /**
         * image : https://pic2.zhimg.com/v2-7c82c271dfb95525cc9d1d4dba53b6d9.jpg
         * type : 0
         * id : 9565082
         * ga_prefix : 101009
         * title : 总算知道，为什么有些简历能从几千份简历里脱颖而出了
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

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
    }
}
