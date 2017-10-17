package com.hins.reader.network;

import com.hins.reader.model.GankResult;
import com.hins.reader.model.News;
import com.hins.reader.model.StoryDetail;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hins on 2017/10/11.
 */

public interface Api {

    @GET("api/4/news/latest")
    Observable<News> getHomeStory();

    @GET("api/4/news/before/{date}")
    Observable<News> getBeforeHomeStory(@Path("date") String date);

    @GET("api/4/news/{id}")
    Observable<StoryDetail> getStoryDetail(@Path("id") int id);

    @GET("api/data/{category}/{size}/{page}")
    Observable<GankResult> getGankItemByCategory(@Path("category") String category,
                                                 @Path("size") int size,
                                                 @Path("page") int page);
}
