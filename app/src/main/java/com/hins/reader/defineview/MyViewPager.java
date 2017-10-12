package com.hins.reader.defineview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Hins on 2017/10/12.
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        super.onAttachedToWindow();
        try {
            Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, false);
            getAdapter().notifyDataSetChanged();
            setCurrentItem(getCurrentItem());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("MyViewPager", "onAttachedToWindow: ");


    }
}
