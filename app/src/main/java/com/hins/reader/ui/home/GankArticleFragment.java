package com.hins.reader.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hins.reader.R;
import com.hins.reader.adapter.GankArticleAdapter;
import com.hins.reader.base.BasePagerFragment;
import com.hins.reader.model.GankResult;
import com.hins.reader.model.GankResult.GankResultBean;
import com.hins.reader.network.GankHttpHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hins on 2017/10/11.
 */

public class GankArticleFragment extends BasePagerFragment {

    private static final String TAG = "GankArticleFragment";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.article_swipe_refresh)
    SwipeRefreshLayout mArticleSwipeRefresh;

    private LinearLayoutManager mLayoutManager;
    private GankArticleAdapter mGankArticleAdapter;

    private List<GankResultBean> mGankResultBeans;

    private String mCategory;
    private int mSize;
    private int mPage;


    public static GankArticleFragment newInstance() {
        return new GankArticleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGankResultBeans = new ArrayList<>();
        mCategory = "Android";
        mSize = 10;
        mPage = 1;

        Log.d(TAG, "onCreate: ");
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_gank_article;
    }

    @Override
    protected void initView() {

        mArticleSwipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent);
        mArticleSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItems = manager.getItemCount();
                    if (lastVisibleItem == totalItems -1 && isSlidingToLast) {
                        loadMore();
                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });


    }

    @Override
    protected void initData() {
        mArticleSwipeRefresh.setRefreshing(true);

        GankHttpHelper.getInstance()
                .getGankArticle(mCategory, mSize, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull GankResult gankResult) {

                        setRecyclerView(true, gankResult.getResults());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);

                    }

                    @Override
                    public void onComplete() {
                        mArticleSwipeRefresh.setRefreshing(false);
                    }
                });

    }

    private void loadMore() {
        GankHttpHelper.getInstance()
                .getGankArticle(mCategory, mSize, ++mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GankResult gankResult) {
                        setRecyclerView(false, gankResult.getResults());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void refresh() {
        mPage = 1;
        initData();
    }

    private void setRecyclerView(boolean isClear, List<GankResultBean> gankResultBeans) {

        if (isClear) {
            mGankResultBeans.clear();
        }
        mGankResultBeans.addAll(gankResultBeans);

        if (mGankArticleAdapter == null) {
            mGankArticleAdapter = new GankArticleAdapter(mGankResultBeans);
            mRecyclerView.setAdapter(mGankArticleAdapter);
        } else {
            mGankArticleAdapter.notifyDataSetChanged();
        }
    }
}
