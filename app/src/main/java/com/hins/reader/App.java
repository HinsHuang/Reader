package com.hins.reader;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Hins on 2017/10/16.
 */

public class App extends Application {

    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        LeakCanary.install(this);
    }
}
