package com.hins.reader.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hins.reader.model.TopStory;
import com.hins.reader.model.TopStory.TopStoryBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hins.reader.R.id.recycler_view;

/**
 * Created by Hins on 2017/10/10.
 */

public class HomeFragment extends Fragment {

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


    private TopStoryAdapter mTopStoryAdapter;
    private TopStory mTopStory;
    private List<TopStoryBean> mTopStoryBeans = new ArrayList<>();
    private List<ImageView> mImages = new ArrayList<>();

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

        mTopStory = new TopStory();


        mTopStoryBeans.add(
                new TopStoryBean("https://pic3.zhimg.com/v2-ebad6c9dd59183ddcce8960115598b2e.jpg", "中国人第一次使用自己的射电望远镜找到新的脉冲星，此刻我想致敬两个人"));

        mTopStoryBeans.add(
                new TopStoryBean("https://pic1.zhimg.com/v2-68006720e6456dd0b6af85ea14eefc8c.jpg", "国庆档票房又破了新纪录，可惜 26 亿至少「注水」了 5 个亿")
        );
        mTopStoryBeans.add(
                new TopStoryBean("https://pic1.zhimg.com/v2-1298a03d52221be4e9410ff171c54b30.jpg", "收了学生手机并当众砸烂，他们还是那句——「为了你好」")
        );

        mTopStoryBeans.add(
                new TopStoryBean("https://pic3.zhimg.com/v2-1542e7e6acfd6b08697ba65cf1d9c20a.jpg", "演过《大空头》的 Thaler 拿了诺奖，他是行为经济学真正的奠基人")
        );
        mTopStoryBeans.add(
                new TopStoryBean("https://pic4.zhimg.com/v2-7619627a9b8afd320a1a9ce1a70b7283.jpg", "看看马再看看自己，感觉人类的进化好失败啊")
        );

        mTopStory.setTop_stories(mTopStoryBeans);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View childView = inflater.inflate(R.layout.header_images, container, false);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HomeAdapter(mTopStoryBeans);

        mAdapter.setHeaderView(childView);

        mRecyclerView.setAdapter(mAdapter);


        unbinder = ButterKnife.bind(this, childView);

        initView();
        return rootView;
    }

    private void initView() {



        int index = 0;
        mHeaderTitle.setText(mTopStory.getTop_stories().get(index).getTitle());

        for (TopStoryBean s : mTopStory.getTop_stories()) {

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

        mHeaderViewPager.setAdapter(new TopStoryAdapter(mTopStory, mImages));

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

                mHeaderTitle.setText(mTopStory.getTop_stories().get(position).getTitle());

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
