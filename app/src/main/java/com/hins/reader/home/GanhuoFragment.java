package com.hins.reader.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hins.reader.R;
import com.hins.reader.base.BasePagerFragment;

/**
 * Created by Hins on 2017/10/11.
 */

public class GanhuoFragment extends BasePagerFragment {

    private static final String TAG = "GanhuoFragment";

    public static GanhuoFragment newInstance() {
        return new GanhuoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ganhuo, container, false);
        return view;
    }

    @Override
    protected void initData() {

        Log.d(TAG, "initData: ");

    }
}
