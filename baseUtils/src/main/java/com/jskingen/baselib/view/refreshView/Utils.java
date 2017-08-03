package com.jskingen.baselib.view.refreshView;

import android.content.Context;

/**
 * Created by ChneY on 2017/7/27.
 */
public class Utils {
    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
