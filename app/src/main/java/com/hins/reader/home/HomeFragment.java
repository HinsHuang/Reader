package com.hins.reader.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hins.reader.R;
import com.hins.reader.adapter.HomeAdapter;
import com.hins.reader.adapter.TopStoryAdapter;
import com.hins.reader.model.News;
import com.hins.reader.model.Story;
import com.hins.reader.model.TopStory;
import com.hins.reader.network.HttpHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.hins.reader.R.id.recycler_view;

/**
 * Created by Hins on 2017/10/10.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.header_view_pager)
    ViewPager mHeaderViewPager;
    @BindView(R.id.header_indicator)
    LinearLayout mHeaderIndicator;
    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    Unbinder unbinder;


    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private News mNews;
    private TopStoryAdapter mTopStoryAdapter;
    private List<TopStory> mTopStories;
    private List<Story> mStories;
    private List<ImageView> mImages = new ArrayList<>();

    private View mHeaderView;
    private View mListView;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = mHeaderViewPager.getCurrentItem();

            if (currentPosition == mHeaderViewPager.getAdapter().getCount() - 1) {
                // 最后一页
                mHeaderViewPager.setCurrentItem(0);
            } else {
                mHeaderViewPager.setCurrentItem(currentPosition + 1);
            }

            // 一直给自己发消息
            mHandler.postDelayed(this, 3000);
        }
    };

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHeaderView = inflater.inflate(R.layout.header_images, container, false);
        mListView = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, mHeaderView);

        mRecyclerView = (RecyclerView) mListView.findViewById(recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        initData();

        return mListView;
    }

    private void initData() {
        HttpHelper.getInstance().getLatestNews()
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
                        mStories = mNews.getStories();
                        setHeaderView();
                        mAdapter = new HomeAdapter(mStories);
                        mAdapter.setHeaderView(mHeaderView);
                        mRecyclerView.setAdapter(mAdapter);
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

    private void setHeaderView() {

        mTopStories = mNews.getTopStories();
        int index = 0;
        mHeaderTitle.setText(mTopStories.get(index).getTitle());

        for (TopStory s : mTopStories) {

            ImageView imageView = new ImageView(getActivity());

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            Glide.with(this).load(s.getImage()).into(imageView);

            mImages.add(imageView);

            ImageView pointImage = new ImageView(getActivity());
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

        mHeaderViewPager.setAdapter(new TopStoryAdapter(mTopStories, mImages));
        mHeaderViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mTopStories.size());

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

        mHandler.postDelayed(mRunnable, 3000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mHandler.removeCallbacks(mRunnable);
    }
}
