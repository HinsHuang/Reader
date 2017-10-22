package com.hins.reader.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hins.reader.util.CleanLeakUtils;

/**
 * Created by Hins on 2017/10/22.
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onDestroy() {

        CleanLeakUtils.fixInputMethodManagerLeak(this);

        super.onDestroy();

        Log.d(TAG, "onDestroy: ");
    }
}
