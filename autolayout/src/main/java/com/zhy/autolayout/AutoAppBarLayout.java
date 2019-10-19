package com.zhy.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 *
 */
public class AutoAppBarLayout extends AppBarLayout {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoAppBarLayout(Context context) {
        super(context);
    }

    public AutoAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    public AutoAppBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static class LayoutParams extends AppBarLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }


        public LayoutParams(int width, int height) {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

    }
}
