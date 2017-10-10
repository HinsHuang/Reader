package com.hins.reader.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hins.reader.model.TopStory;

import java.util.List;

/**
 * Created by Hins on 2017/10/10.
 */

public class TopStoryAdapter extends PagerAdapter {


    private TopStory mTopStory;
    private List<ImageView> mImageViews;

    public TopStoryAdapter(TopStory topStory, List<ImageView> imageViews) {
        mTopStory = topStory;
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

        container.addView(mImageViews.get(position));

        return mImageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
