package com.hins.reader.network;

import com.hins.reader.config.Urls;
import com.hins.reader.model.News;
import com.hins.reader.model.StoryDetail;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hins on 2017/10/11.
 */

public class ZhihuHttpHelper {

    private static ZhihuHttpHelper INSTANCE;
    private static Retrofit mRetrofit;

    private ZhihuHttpHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Urls.ZHIHU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ZhihuHttpHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (ZhihuHttpHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ZhihuHttpHelper();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取最新的消息
     * @return
     */
    public Observable<News> getLatestNews() {
        return mRetrofit.create(Api.class)
                .getHomeStory();
    }


    /**
     * 获取以往的消息
     * @param date 具体某一天
     * @return
     */
    public Observable<News> getBeforeNews(String date) {
        return mRetrofit.create(Api.class)
                .getBeforeHomeStory(date);

    }

    /**
     * 获取具体的消息内容
     * @param id
     * @return
     */
    public Observable<StoryDetail> getStoryDetail(int id) {
        return mRetrofit.create(Api.class)
                .getStoryDetail(id);
    }




}
