package com.jskingen.baselib.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.jskingen.baselib.R;
import com.jskingen.baselib.view.LoadingDialog;

/**
 * Created by ChneY on 2017/5/27.
 * <p>
 * 一些简单的Dialog 显示框
 */

public class DialogUtils {

    /**
     * 显示白色的加载 Dialog
     *
     * @param context
     * @param message
     * @param backgroundRes
     */
    public static void showLoading(Context context, String message, @DrawableRes int backgroundRes, int textColor, @DrawableRes int icon, int dismissTime) {
        final LoadingDialog progress = new LoadingDialog(context);
        if (!TextUtils.isEmpty(message))
            progress.setMessage(message);
        if (backgroundRes != 0)
            progress.setBackground(backgroundRes);
        if (textColor != 0)
            progress.setTextColor(textColor);

        if (icon != 0)
            progress.setIcon(icon);
        progress.show();

        if (dismissTime != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.dismiss();
                }
            }, dismissTime);
        }
    }

    public static void showLoading(Context context, String message, @DrawableRes int backgroundRes, int textColor, @DrawableRes int icon) {
        showLoading(context, message, backgroundRes, textColor, icon, 0);
    }

    public static void showLoading(Context context, String message, @DrawableRes int backgroundRes) {
        showLoading(context, message, backgroundRes, 0, 0);
    }

    /**
     * 默认 白色背景的 Dialog
     */
    public static void showLoading(Context context, String message) {
        showLoading(context, message, 0);
    }

    public static void showLoading(Context context) {
        showLoading(context, "");
    }


    /**
     * @param context 黑色背景的 dialog
     * @param message 文字信息
     * @param icon    自定义图片
     */
    public static void showBlackLoading(Context context, String message, @DrawableRes int icon) {
        showLoading(context, message, R.drawable.progress_custom_bg_black, Color.WHITE, icon, 0);
    }

    //
    public static void showBlackLoading(Context context, String message, @AVLoadingIndicatorView.Indicator int indicatorId, int colorRes) {
        final LoadingDialog progress = new LoadingDialog(context);
        if (!TextUtils.isEmpty(message))
            progress.setMessage(message);
        progress.setBackground(R.drawable.progress_custom_bg_black);
        progress.setIndicator(indicatorId, colorRes);
        progress.setTextColor(Color.WHITE);

        progress.show();
    }

    public static void showBlackLoading(Context context, String message) {
        showLoading(context, message, R.drawable.progress_custom_bg_black, Color.WHITE, 0);
    }

    public static void showBlackLoading(Context context) {
        showBlackLoading(context, "");
    }

    /**
     * 自动消失的黑色Dialog
     *
     * @param context
     * @param message     文字内容
     * @param icon        图标
     * @param dismissTime 自动小时的时间
     */
    public static void showAutoCancelDialog(Context context, String message, int icon, int dismissTime) {
        showLoading(context, message, R.drawable.progress_custom_bg_black, Color.WHITE, icon, dismissTime);
    }

    public static void showAutoCancelDialog(Context context, String message, int icon) {
        showLoading(context, message, R.drawable.progress_custom_bg_black, Color.WHITE, icon, (int) (1000 * 1.5f));
    }

    /**
     * 会自动消失的 完成dialog
     */
    public static void showCompleDialog(Context context, String message) {
        showLoading(context, message, R.drawable.progress_custom_bg_black, Color.WHITE, R.mipmap.loading_success, (int) (1000 * 1.5f));
    }

    /**
     * 会自动消失的 警告dialog
     */
    public static void showWaringDialog(Context context, String message) {
        showLoading(context, message, R.drawable.progress_custom_bg_black, Color.WHITE, R.mipmap.loading_waring, (int) (1000 * 1.5f));
    }

    /**
     * 会自动消失的 错误dialog
     */
    public static void showErrorDialog(Context context, String message) {
        showLoading(context, message, R.drawable.progress_custom_bg_black, Color.WHITE, R.mipmap.loading_error, (int) (1000 * 1.5f));
    }
}
