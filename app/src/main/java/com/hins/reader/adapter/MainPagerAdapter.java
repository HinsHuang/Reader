package com.hins.reader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Hins on 2017/10/10.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments,
                            List<String> titles) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
     //   super.destroyItem(container, position, object);
    }
}
