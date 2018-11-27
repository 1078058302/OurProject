package com.bw.movie.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * author:AbnerMing
 * date:2018/10/17
 * 存储工具类
 */
public class SharedPreferencesUtils {
    private static String name = "ming";

    //存储字符串
    public static void putString(Context context, String name, String value) {
        create(context).edit().putString(name, value).commit();
    }

    //存储Boolean
    public static void putBoolean(Context context, String name, boolean value) {
        create(context).edit().putBoolean(name, value).commit();
    }

    //存储Int
    public static void putInt(Context context, String name, int value) {
        create(context).edit().putInt(name, value).commit();
    }


    //获取String
    public static String getString(Context context, String name) {
        return create(context).getString(name, "");
    }

    //获取int
    public static int getInt(Context context, String name) {
        return create(context).getInt(name, 0);
    }

    //获取Boolean
    public static Boolean getBoolean(Context context, String name) {
        return create(context).getBoolean(name, false);
    }

    private static SharedPreferences create(Context context) {
        SharedPreferences sp = context.getSharedPreferences(name, context.MODE_PRIVATE);
        return sp;
    }
}
