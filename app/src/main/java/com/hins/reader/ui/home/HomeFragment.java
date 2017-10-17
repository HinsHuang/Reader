package com.hins.reader.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hins.reader.R;
import com.hins.reader.adapter.HomeAdapter;
import com.hins.reader.adapter.TopStoryPagerAdapter;
import com.hins.reader.base.BasePagerFragment;
import com.hins.reader.model.News;
import com.hins.reader.model.Story;
import com.hins.reader.model.TopStory;
import com.hins.reader.network.ZhihuHttpHelper;
import com.hins.reader.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
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

public class HomeFragment extends BasePagerFragment {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.story_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private HomeAdapter mHomeAdapter;
    private LinearLayoutManager mLayoutManager;

    private ViewPager mHeaderViewPager;
    private LinearLayout mHeaderIndicator;
    private TextView mHeaderTitle;

    private News mNews;
    private TopStoryPagerAdapter mTopStoryAdapter;
    private List<TopStory> mTopStories;
    private List<Story> mStories;
    private List<ImageView> mImages = new ArrayList<>();

    private View mHeaderView;

    private boolean addHeader;

    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;

    private boolean isRunning;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // 一直给自己发消息
            mHandler.postDelayed(this, 3000);
            
            if (!isRunning) {
                return;
            }
            int currentPosition = mHeaderViewPager.getCurrentItem();

            if (currentPosition == mHeaderViewPager.getAdapter().getCount() - 1) {
                // 最后一页
                mHeaderViewPager.setCurrentItem(0);
            } else {
                mHeaderViewPager.setCurrentItem(currentPosition + 1);
            }
        }
    };

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStories = new ArrayList<>();
        mTopStories = new ArrayList<>();

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.header_images, mContainer, false);

        mHeaderViewPager = (ViewPager) mHeaderView.findViewById(R.id.header_view_pager);
        mHeaderIndicator = (LinearLayout) mHeaderView.findViewById(R.id.header_indicator);
        mHeaderTitle = (TextView) mHeaderView.findViewById(R.id.header_title);

        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorAccent);
//        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                //当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 用于判断当头部完全不可见时，图片轮播停止
                    int firstVisibleItem = manager.findFirstCompletelyVisibleItemPosition();
                    if (firstVisibleItem < 2) {
                        onVisible();
                    } else {
                        onInvisible();
                    }
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
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
        mSwipeRefresh.setRefreshing(true);

        ZhihuHttpHelper.getInstance().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull News news) {
                        Log.d(TAG, "onNext: " + news.getDate() + " " + news.getStories().size() + " " + news.getTopStories().size());
                        mNews = news;
                        initHeaderView();
                        setRecyclerView(true, mNews.getStories());
                        mSwipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        mSwipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

        Log.d(TAG, "initData: ");
    }

    private void initHeaderView() {

        if (addHeader) {
            return;
        }

        Log.d(TAG, "initHeaderView: ");
        
        mTopStories.clear();
        mTopStories = mNews.getTopStories();
        int index = 0;
        mHeaderTitle.setText(mTopStories.get(index).getTitle());

        for (TopStory s : mTopStories) {

            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext).load(s.getImage()).into(imageView);
            mImages.add(imageView);

            ImageView pointImage = new ImageView(mContext);
            pointImage.setImageResource(R.drawable.shape_point_selector);
            int pointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);
            if (index > 0) {
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.point_margin);
                pointImage.setSelected(false);
            } else {
                pointImage.setSelected(true);
            }

            pointImage.setLayoutParams(params);
            mHeaderIndicator.addView(pointImage);
            index++;
        }

        mTopStoryAdapter = new TopStoryPagerAdapter(mContext, mTopStories, mImages);
        mHeaderViewPager.setAdapter(mTopStoryAdapter);
        mHeaderViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mTopStories.size());

        mHandler.postDelayed(mRunnable, 3000);

        mHeaderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            int lastPosition;
            @Override
            public void onPageSelected(int position) {
                // 页面被选中
                // 修改position
                position = position % mImages.size();
                mHeaderTitle.setText(mTopStories.get(position).getTitle());
                // 设置当前页面选中
                mHeaderIndicator.getChildAt(position).setSelected(true);
                // 设置前一页不选中
                mHeaderIndicator.getChildAt(lastPosition).setSelected(false);
                // 替换位置
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setRecyclerView(boolean isClear, List<Story> stories) {
        if (isClear) {
            mStories.clear();
        }
        mStories.addAll(stories);

        if (mHomeAdapter == null) {
            mHomeAdapter = new HomeAdapter(mStories);
            //设置头部
            mHomeAdapter.setHeaderView(mHeaderView);
            addHeader = true;
            mRecyclerView.setAdapter(mHomeAdapter);
        } else {
            mHomeAdapter.notifyDataSetChanged();
        }
    }

    private void loadMore() {

        mCalendar.set(mYear, mMonth, mDay--);

        ZhihuHttpHelper.getInstance().getBeforeNews(DateUtil.mFormat.format(mCalendar.getTime()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull News news) {
                        setRecyclerView(false, news.getStories());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void refresh() {
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        isRunning = true;
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        isRunning = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mRunnable);
        Log.d(TAG, "onDestroyView: ");
    }
}
