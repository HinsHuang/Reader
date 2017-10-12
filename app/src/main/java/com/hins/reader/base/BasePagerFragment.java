package com.hins.reader.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * Created by Hins on 2017/10/11.
 */

public abstract class BasePagerFragment extends Fragment {
    protected View mRootView;
    public Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        initData();
        isFirst = false;
    }

    protected void onVisible() {}

    protected void onInvisible() {}

    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BasePagerFragment", "onDestroy: ");
    }
}
