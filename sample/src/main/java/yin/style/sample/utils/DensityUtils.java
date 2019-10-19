package yin.style.sample.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/10/16.
 * 今日头条 屏幕适配方案 升级版
 */
public class DensityUtils {
    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;
    private static int barHeight;
    public final static String WIDTH = "width";
    public final static String HEIGHT = "height";

    //    public final static float DEFAULT_WIDTH = 1080f;
    public final static float DEFAULT_WIDTH = 750f;
    public final static float DEFAULT_HEIGHT = 1280f;

    /**
     * 在Application里初始化一下
     *
     * @param application
     */
    public static void setDensity(@NonNull final Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        //获取状态栏高度
        barHeight = getStatusBarHeight(application);

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
    }

    /**
     * 此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好)
     * 在setContentView()之前设置
     *
     * @param activity
     */
    public static void setDefault(Activity activity) {
        setAppOrientation(activity, WIDTH);
    }

    /**
     * 此方法用于在某一个Activity里面更改适配的方向
     * 在setContentView()之前设置
     *
     * @param activity
     * @param orientation
     */
    public static void setOrientation(Activity activity, String orientation) {
        setAppOrientation(activity, orientation);
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * orientation:方向值,传入width或height
     */
    private static void setAppOrientation(@Nullable Activity activity, String orientation) {

        float targetDensity;

        if (orientation.equals(HEIGHT)) {
            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / DEFAULT_HEIGHT;//设计图的高度 单位:dp
        } else {
            targetDensity = appDisplayMetrics.widthPixels / DEFAULT_WIDTH;//设计图的宽度 单位:dp
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         * 只修改Activity的density值
         */
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}