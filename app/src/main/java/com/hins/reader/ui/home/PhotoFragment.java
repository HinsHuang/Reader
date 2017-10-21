package com.hins.reader.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.hins.reader.R;
import com.hins.reader.adapter.ErrorHandleAdapter;
import com.hins.reader.adapter.PhotoAdapter;
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
 * Created by Hins on 2017/10/10.
 */

public class PhotoFragment extends BasePagerFragment {

    private static final String TAG = "PhotoFragment";


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.photo_swipe_refresh)
    SwipeRefreshLayout mPhotoSwipeRefresh;

    private LinearLayoutManager mLinearLayoutManager;
    private PhotoAdapter mPhotoAdapter;

    private List<GankResultBean> mGankResultBeans;

    private String mCategory = "福利";
    private int mSize = 10;
    private int mPage = 1;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGankResultBeans = new ArrayList<>();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initView() {
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mPhotoSwipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent);
        mPhotoSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取最后一个完全显示的item position
                    int lastVisiableItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisiableItem == (totalItemCount - 1) ) {
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

        mPhotoSwipeRefresh.setRefreshing(true);

        GankHttpHelper.getInstance().getGankItemByCategory(mCategory, mSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GankResult gankResult) {

                        setRecyclerView(true, gankResult.getResults());
                        mPhotoSwipeRefresh.setRefreshing(false);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        mPhotoSwipeRefresh.setRefreshing(false);
                        if (mGankResultBeans.size() == 0) {
                            ErrorHandleAdapter adapter = new ErrorHandleAdapter();
                            mRecyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(mContext, "请再次检查网络", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void loadMore() {
        Log.d(TAG, "loadMore: ");

        GankHttpHelper.getInstance().getGankItemByCategory(mCategory, mSize)
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
                        Log.e(TAG, "onError: ", e);

                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }

    private void setRecyclerView(boolean isClear, List<GankResultBean> gankResultBeans) {

        if (isClear) {
            mGankResultBeans.clear();
        }
        mGankResultBeans.addAll(gankResultBeans);

        if (mPhotoAdapter == null) {
            mPhotoAdapter = new PhotoAdapter(mGankResultBeans);
            mRecyclerView.setAdapter(mPhotoAdapter);
        } else {
            mPhotoAdapter.notifyDataSetChanged();
        }

    }

    private void refresh() {
        mPage = 1;
        initData();
    }

}
