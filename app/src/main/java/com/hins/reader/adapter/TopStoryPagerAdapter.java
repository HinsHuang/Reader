package com.hins.reader.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hins.reader.model.TopStory;
import com.hins.reader.ui.detail.StoryDetailActivity;

import java.util.List;

/**
 * Created by Hins on 2017/10/10.
 */

public class TopStoryPagerAdapter extends PagerAdapter {


    private Context mContext;
    private List<TopStory> mTopStories;
    private List<ImageView> mImageViews;

    public TopStoryPagerAdapter(Context context, List<TopStory> topStories, List<ImageView> imageViews) {
        mContext = context;
        mTopStories = topStories;
        mImageViews = imageViews;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % mImageViews.size();
        final TopStory story = mTopStories.get(position);

        View v = mImageViews.get(position);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoryDetailActivity.start(mContext, story.getId());
            }
        });

        if (v.getParent() != null) {
            ((ViewGroup)v.getParent()).removeView(v);
        }

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
