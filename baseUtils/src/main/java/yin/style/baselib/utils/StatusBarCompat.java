package yin.style.baselib.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import yin.style.baselib.fragment.NormalFragment;

/**
 * Created by 陈银 on 2017/12/15 10:15
 * <p>
 * 状态栏背景沉浸式
 */
public class StatusBarCompat {
    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.parseColor("#20000000");

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor) {

        //当前手机版本为5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                activity.getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

        //当前手机版本为4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView();
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }

            StatusBarView statusBarView = null;

            for (int i = 0; i < contentView.getChildCount(); i++) {
                if (contentView.getChildAt(i) instanceof StatusBarView)
                    statusBarView = (StatusBarView) contentView.getChildAt(i);
            }
            if (statusBarView == null) {
                statusBarView = new StatusBarView(activity);

                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ScreenUtil.getStatusHeight(activity));
                contentView.addView(statusBarView, lp);
            }
            statusBarView.setBackgroundColor(color);

        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(NormalFragment fragment, int statusColor) {
//        //当前手机版本为5.0及以上
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (statusColor != INVALID_VAL) {
//                fragment.getActivity().getWindow().setStatusBarColor(statusColor);
//            }
//            return;
//        }
//
//        //当前手机版本为4.4
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            int color = COLOR_DEFAULT;
//            ViewGroup contentView = (ViewGroup) fragment.titleView;
//            if (statusColor != INVALID_VAL) {
//                color = statusColor;
//            }
//
//            StatusBarView statusBarView = null;
//
//            for (int i = 0; i < contentView.getChildCount(); i++) {
//                if (contentView.getChildAt(i) instanceof StatusBarView)
//                    statusBarView = (StatusBarView) contentView.getChildAt(i);
//            }
//            if (statusBarView == null) {
//                statusBarView = new StatusBarView(fragment.getContext());
//
//                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ScreenUtil.getStatusHeight(fragment.getContext()));
//                contentView.addView(statusBarView, lp);
//            }
//            statusBarView.setBackgroundColor(color);
//
//        }
    }

    public static void compat(Activity activity) {
        compat(activity, INVALID_VAL);
    }


    public static class StatusBarView extends View {

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
