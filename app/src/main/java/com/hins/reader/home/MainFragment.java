package com.hins.reader.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hins.reader.R;
import com.hins.reader.adapter.MainPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hins on 2017/10/9.
 */

public class MainFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    Unbinder unbinder;

    private MainPagerAdapter mMainPagerAdapter;
    private List<String> mTitles;
    private List<Fragment> mFragments;

    public static MainFragment newInstance() {

        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();

        mTitles.add("知乎日报");
        mTitles.add("干货美图");

        mFragments.add(HomeFragment.newInstance());
        mFragments.add(PhotoFragment.newInstance());

        mMainPagerAdapter = new MainPagerAdapter(getChildFragmentManager(), mFragments, mTitles);

        mViewPager.setAdapter(mMainPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
