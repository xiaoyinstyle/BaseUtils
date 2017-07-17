package com.jskingen.baselib.utils;

import android.app.Activity;

/**
 * Created by ChneY on 2017/5/12.
 * <p>
 * 常用Activity的 进入/退出动画
 */

public class ActivityJumpAnimation {
    /**
     * 没有动画效果
     */
    private static void no(Activity activity) {
        activity.overridePendingTransition(0, 0);
    }

    /**
     * 左进右出 动画效果
     */
    private static void startLeft2Right(Activity activity) {

    }

    /**
     * 右出左进 动画效果
     */
    private static void finishRight2Left(Activity activity) {

    }

    /**
     * 下进上出 动画效果
     */
    private static void startBottom2Top(Activity activity) {

    }

    /**
     * 上进下出 动画效果
     */
    private static void finishTop2Bottom(Activity activity) {

    }

    /**
     * 淡入淡出 动画效果
     */
    private static void finishFade(Activity activity) {

    }

    /**
     * 淡入淡出 动画效果
     */
    private static void startFade(Activity activity) {

    }


    /**
     * 缩放 动画效果
     */
    private static void finishScale(Activity activity) {

    }

    /**
     * 缩放 动画效果
     */
    private static void startScale(Activity activity) {

    }
}
