package com.hins.reader.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.hins.reader.R;
import com.hins.reader.base.BasePagerFragment;

import butterknife.BindView;

/**
 * Created by Hins on 2017/10/11.
 */

public class GanhuoFragment extends BasePagerFragment {

    private static final String TAG = "GanhuoFragment";
    @BindView(R.id.text_view)
    TextView mTextView;


    public static GanhuoFragment newInstance() {
        return new GanhuoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
    }



    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_ganhuo;
    }

    @Override
    protected void initData() {

        Log.d(TAG, "initData: ");

    }

    @Override
    protected void initView() {

        Log.d(TAG, "initView: ");

    }


}
