package com.hins.reader.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.hins.reader.R;
import com.hins.reader.base.BasePagerFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Hins on 2017/10/10.
 */

public class PhotoFragment extends BasePagerFragment {

    private static final String TAG = "PhotoFragment";
    @BindView(R.id.text_photo)
    TextView mTextPhoto;
    Unbinder unbinder;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }



    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_photo;
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
