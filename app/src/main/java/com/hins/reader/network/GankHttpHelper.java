package com.hins.reader.network;

import com.hins.reader.config.Urls;
import com.hins.reader.model.GankResult;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hins on 2017/10/17.
 */

public class GankHttpHelper {

    private static GankHttpHelper INSTANCE;
    private static Retrofit mRetrofit;

    private GankHttpHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Urls.GANK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static GankHttpHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (ZhihuHttpHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GankHttpHelper();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 随机数据：http://gank.io/api/random/data/分类/个数
     * @return
     */
    public Observable<GankResult> getGankItemByCategory(String category, int size) {
        return mRetrofit.create(Api.class)
                .getGankRandomPhoto(category, size);
    }

    public Observable<GankResult> getGankArticle(String category, int size, int page) {
        return mRetrofit.create(Api.class)
                .getGankAndroidArticle(category, size, page);
    }

}
