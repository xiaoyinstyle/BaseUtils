package com.jskingen.baselib.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenY on 2016/12/9.
 * <p>
 * 支持手动控制是否左右滑动的 ViewPage
 */

public class CommonViewPage extends ViewPager {
    private boolean isCanScroll = true;

    public CommonViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODOAuto-generated constructor stub
    }

    public CommonViewPage(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScroll) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODOAuto-generated method stub
        if (isCanScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }

//    @Override
//    public void setCurrentItem(int item, boolean smoothScroll) {
//        // TODOAuto-generated method stub
//        super.setCurrentItem(item, smoothScroll);
//    }
//
//    @Override
//    public void setCurrentItem(int item) {
//        // TODOAuto-generated method stub
//        super.setCurrentItem(item, false);
//    }

}