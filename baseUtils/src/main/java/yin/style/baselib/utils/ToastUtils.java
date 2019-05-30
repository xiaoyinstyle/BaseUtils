package yin.style.baselib.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import yin.style.baselib.BaseHelp;
import yin.style.baselib.BuildConfig;

/**
 * Created by chenY on 2017/1/18.
 * <p>
 * Toast工具
 */

public class ToastUtils {
    private static Toast toast = null;
    public static boolean isShow = true;
    // 主线程的Handler对象
    private static Handler mHandler = new Handler(Looper.getMainLooper());

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
//        Log.e("AAAAA", (toast == null) + "__" + message + "___show: " + System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 28) {
            Toast.makeText(context.getApplicationContext(), message, time).show();
        } else if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), message, time);
            toast.show();   // 会发现延迟之后就显示出来了
        } else {
//            toast.cancel();
//            toast = null;
//            toast = Toast.makeText(context.getApplicationContext(), message, time);
            toast.setText(message);
            toast.show();   // 会发现延迟之后就显示出来了
        }


    }

    public static void show(CharSequence message) {
        Context mContext = BaseHelp.getInstance().getContext();
        if (isShow && mContext != null)
            showMessage(mContext, message, Toast.LENGTH_SHORT);
    }

    public static void show(@StringRes int message) {
        Context mContext = BaseHelp.getInstance().getContext();
        if (isShow && mContext != null)
            showMessage(mContext, BaseHelp.getInstance().getContext().getString(message), Toast.LENGTH_SHORT);
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
