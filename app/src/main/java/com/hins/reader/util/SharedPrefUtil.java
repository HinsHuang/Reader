package com.hins.reader.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hins on 2017/10/21.
 */

public class SharedPrefUtil {

    //以下两个方法用于获取和保存夜间模式状态
    public static boolean getNightMode(Context context) {
        SharedPreferences pref = context.getSharedPreferences("night_mode", Context.MODE_PRIVATE);
        return pref.getBoolean("NightMode", false);
    }

    public static void setNightMode(Context context, boolean isNightMode) {
        SharedPreferences pref = context.getSharedPreferences("night_mode", Context.MODE_PRIVATE);
        pref.edit().putBoolean("NightMode", isNightMode)
                .commit();
    }
}
