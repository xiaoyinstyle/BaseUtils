package yin.style.baselib.view;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
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
            try {
                return super.onInterceptTouchEvent(arg0);
            } catch (IllegalArgumentException e) {
                //uncomment if you really want to see these errors
                //e.printStackTrace();
                Log.e(this.toString(), "onInterceptTouchEvent: ", e);
                return false;
            }
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