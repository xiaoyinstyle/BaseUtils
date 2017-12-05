package com.jskingen.baselib.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.jskingen.baselib.BaseApplication;

/**
 * Created by chenY on 2017/1/18.
 * <p>
 * Toast工具
 */

public class ToastUtils {
    private static Toast toast = null;
    public static boolean isShow = true;

    private ToastUtils() {
            /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            showMessage(context, message, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, int message) {
        if (isShow)
            showMessage(context, message + "", Toast.LENGTH_LONG);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void show(Context context, CharSequence message) {
        if (isShow)
            showMessage(context, message, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int message) {
        if (isShow)
            showMessage(context, message + "", Toast.LENGTH_SHORT);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            showMessage(context, message + "", duration);
    }

    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            showMessage(context, message + "", duration);
    }

    private static void showMessage(final Context context, final CharSequence message, final int time) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), message, time);
        } else {
            toast.setText(message);
        }
        toast.show();

    }

    public static void show(CharSequence message) {
        if (isShow)
            showMessage(BaseApplication.getInstance().getContext(), message, Toast.LENGTH_SHORT);
    }

    public static void show(@StringRes int message) {
        if (isShow)
            showMessage(BaseApplication.getInstance().getContext(), BaseApplication.getInstance().getContext().getString(message), Toast.LENGTH_SHORT);
    }

    /**
     * 关闭当前Toast
     */
    public static void cancelCurrentToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
