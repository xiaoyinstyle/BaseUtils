package com.zhy.autolayout;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;

import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 *
 */
public class AutoScrollView extends NestedScrollView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoScrollView(Context context) {
        super(context);
    }

    public AutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public AutoFrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoFrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
