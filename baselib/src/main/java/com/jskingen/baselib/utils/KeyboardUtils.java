package com.jskingen.baselib.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by leslie on 2016/8/16.
 */
public class KeyboardUtils {

    /**
     * 打开键盘
     *
     * @param context
     * @param view
     */
    public static void open(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    /**
     * 关闭键盘
     *
     * @param context
     * @param view
     */
    public static void close(Context context, View view) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    public static int getHight(final View view) {
//        view.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
        Rect r = new Rect();
        view.getWindowVisibleDisplayFrame(r);

        int screenHeight = view.getRootView().getHeight();
        int heightDifference = screenHeight - (r.bottom - r.top);
        return heightDifference;
        //boolean visible = heightDiff > screenHeight / 3;
//    }
//                });
    }

    //未起作用
    public static boolean isOpen(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();

    }

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public static void controlyout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域的底部
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.setPadding(0, 0, 0, srollHeight);
                } else {
                    //键盘隐藏
                    root.setPadding(0, 0, 0, 0);
                }
            }
        });
    }
}
