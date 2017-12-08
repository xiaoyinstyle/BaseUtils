package yin.style.baselib.utils;

import android.util.Log;

import yin.style.baselib.BaseHelp;


/**
 * Created by ChneY on 2017/4/22.
 */

public class LogUtils {
    private static final String TAG = BaseHelp.getInstance().getLogTag();
    public static boolean Debug = true;

    private LogUtils() {
    }

    public static void i(String text) {
        if (Debug)
            Log.i(TAG, "" + text);
    }

    public static void i(String TAG, String text) {
        if (Debug)
            Log.i(TAG, "" + text);
    }

    public static void w(String text) {
        if (Debug)
            Log.w(TAG, "" + text);
    }

    public static void w(String TAG, String text) {
        if (Debug)
            Log.w(TAG, "" + text);
    }

    public static void d(String text) {
        if (Debug)
            Log.d(TAG, "" + text);
    }

    public static void d(String TAG, String text) {
        if (Debug)
            Log.d(TAG, "" + text);
    }

    public static void e(String text) {
        if (Debug)
            Log.e(TAG, "" + text);
    }

    public static void e(String TAG, String text) {
        if (Debug)
            Log.e(TAG, "" + text);
    }

}
