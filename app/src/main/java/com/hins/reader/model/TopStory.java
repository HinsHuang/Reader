package com.hins.reader.model;

import java.util.List;

/**
 * Created by Hins on 2017/10/10.
 */

public class TopStory {

    private List<TopStoryBean> top_stories;

    public List<TopStoryBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoryBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class TopStoryBean {
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
