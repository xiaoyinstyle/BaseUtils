package com.zhy.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoLayoutHelper;

import androidx.gridlayout.widget.GridLayout;

/**
 *
 */

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on 2018-08-01 10:13:01.
 * 
 */ 
public class AutoGridLayout extends GridLayout {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoGridLayout(Context context) {
        super(context);
    }

    public AutoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public AutoLayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static class AutoLayoutParams extends LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mAutoLayoutInfo;

        public AutoLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }

//
//        public LayoutParams(int width, int height) {
//            super(width, height);
//        }

        public AutoLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public AutoLayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
    }
}
