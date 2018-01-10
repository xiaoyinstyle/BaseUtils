package yin.style.baselib.view.autoTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import yin.style.baselib.R;

import java.util.WeakHashMap;

/**
 * A {@link ViewGroup} that re-sizes the text of it's children to be no larger than the width of the
 * view.
 *
 * @attr ref R.styleable.AutofitTextView_sizeToFit
 * @attr ref R.styleable.AutofitTextView_minTextSize
 * @attr ref R.styleable.AutofitTextView_precision
 */
public class AutoFitLayout extends FrameLayout {

    private boolean mEnabled;
    private float mMinTextSize;
    private float mPrecision;
    private WeakHashMap<View, AutoFitHelper> mHelpers = new WeakHashMap<View, AutoFitHelper>();

    public AutoFitLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AutoFitLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public AutoFitLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        boolean sizeToFit = true;
        int minTextSize = -1;
        float precision = -1;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AutoFitTextView,
                    defStyle,
                    0);
            sizeToFit = ta.getBoolean(R.styleable.AutoFitTextView_sizeToFit, sizeToFit);
            minTextSize = ta.getDimensionPixelSize(R.styleable.AutoFitTextView_minTextSize,
                    minTextSize);
            precision = ta.getFloat(R.styleable.AutoFitTextView_precision, precision);
            ta.recycle();
        }

        mEnabled = sizeToFit;
        mMinTextSize = minTextSize;
        mPrecision = precision;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child instanceof TextView) {
            TextView textView = (TextView) child;
            AutoFitHelper helper = AutoFitHelper.create(textView)
                    .setEnabled(mEnabled);
            if (mPrecision > 0) {
                helper.setPrecision(mPrecision);
            }
            if (mMinTextSize > 0) {
                helper.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, mMinTextSize);
            }
            mHelpers.put(textView, helper);
        }
    }

    /**
     * Returns the {@link AutoFitHelper} for this child View.
     */
    public AutoFitHelper getAutofitHelper(TextView textView) {
        return mHelpers.get(textView);
    }

    /**
     * Returns the {@link AutoFitHelper} for this child View.
     */
    public AutoFitHelper getAutofitHelper(int index) {
        return mHelpers.get(getChildAt(index));
    }
}
