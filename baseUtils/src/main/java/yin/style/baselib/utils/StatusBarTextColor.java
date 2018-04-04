package yin.style.baselib.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by 陈银 on 2017/12/15 10:16
 * <p>
 * 状态栏字体 颜色
 */

public class StatusBarTextColor {

    public static int StatusBarLightMode(Activity activity, boolean dark) {
        int result = 0;
        //这个方法只支持4.0以上系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity.getWindow(), dark)) {//判断是不是小米系统
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), dark)) {//判断是不是魅族系统
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前是不是6.0以上的系统
                if (dark) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
                result = 3;
            } else {
                //如果以上都不符合就只能加个半透明的背景色了
                result = 4;
                if (dark) {
                    setTranslucentForCoordinatorLayout(activity, 70);
                } else {
                    setTranslucentForCoordinatorLayout(activity, 0);
                }
            }
        }
        return result;
    }

    //带有透明颜色的状态栏
    private static void setTranslucentForCoordinatorLayout(Activity activity, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);//先将状态栏设置为完全透明
        addTranslucentView(activity, statusBarAlpha);//添加一个自定义透明度的矩形状态栏
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 添加半透明矩形条
     *
     * @param activity       需要设置的 activity
     * @param statusBarAlpha 透明值
     */
    private static void addTranslucentView(Activity activity, int statusBarAlpha) {
        ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView();
        StatusBarView statusBarView = null;

        for (int i = 0; i < contentView.getChildCount(); i++) {
            if (contentView.getChildAt(i) instanceof StatusBarView) {
                statusBarView = (StatusBarView) contentView.getChildAt(i);
                break;
            }
        }
        if (statusBarView == null) {
            statusBarView = new StatusBarView(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getStatusHeight(activity));
            statusBarView.setLayoutParams(params);
            contentView.addView(statusBarView);
        }
        statusBarView.setBackgroundColor(Color.BLACK);
        statusBarView.getBackground().setAlpha(statusBarAlpha);
    }

    /**
     * 修改小米手机系统的
     *
     * @param window
     * @param dark
     * @return
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 魅族手机修改该字体颜色
     *
     * @param window
     * @param dark
     * @return
     */
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    private static class StatusBarView extends View {

        public StatusBarView(Context context) {
            super(context);
        }

        public StatusBarView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }
    }
}