package com.hins.reader;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.hins.reader.util.SharedPrefUtil;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Hins on 2017/10/16.
 */

public class App extends Application {

    public static Application application;

    private static boolean isNightMode;

    public static void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }

    public static boolean isNightMode() {
        return isNightMode;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        LeakCanary.install(this);

        isNightMode = SharedPrefUtil.getNightMode(this);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
