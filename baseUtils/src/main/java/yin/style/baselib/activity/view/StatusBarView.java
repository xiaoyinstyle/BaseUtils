package yin.style.baselib.activity.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import yin.style.baselib.utils.ScreenUtil;
import yin.style.baselib.utils.StatusBarUtils;

public class StatusBarView extends LinearLayout {

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


    /**
     * 设置沉浸式
     */
    public boolean setStatusBarView(Activity activity, boolean isShowStatus, int statusBarColor, boolean barTextDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isShowStatus) {
                StatusBarUtils.compat(activity, statusBarColor);
                setPadding(0, ScreenUtil.getStatusHeight(activity), 0, 0);
                setBackgroundColor(statusBarColor);
            } else {
                setPadding(0, 0, 0, 0);
                StatusBarUtils.compat(activity, Color.TRANSPARENT);
            }
            StatusBarUtils.statusBarLightMode(activity, barTextDark);
            return true;
        } else {
            return false;
        }
    }

    public boolean setStatusBarView(Activity activity, boolean isShowStatus) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isShowStatus) {
                setPadding(0, ScreenUtil.getStatusHeight(activity), 0, 0);
            } else {
                setPadding(0, 0, 0, 0);
                StatusBarUtils.compat(activity, Color.TRANSPARENT);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置沉浸式 字体颜色
     */
    public boolean setStatusBarText(Activity activity, boolean barTextDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtils.statusBarLightMode(activity, barTextDark);
            return true;
        } else {
            return false;
        }
    }
}