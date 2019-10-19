package yin.style.baselib.view.scaleView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by ChneY on 2017/5/7.
 *
 *  ref R.styleable.ScaleView_scale_weight_width
 *  ref R.styleable.ScaleView_scale_weight_height
 *  ref R.styleable.ScaleView_scale_references
 *  ref R.styleable.ScaleView_scale_max_width
 *  ref R.styleable.ScaleView_scale_max_height
 *  ref R.styleable.ScaleView_scale_min_width
 *  ref R.styleable.ScaleView_scale_min_height
 */
public class ScaleLinearLayout extends LinearLayout {

    ScaleViewHelper scaleViewHelper;

    public ScaleLinearLayout(Context context) {
        this(context, null);
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scaleViewHelper = new ScaleViewHelper(this, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        scaleViewHelper.onMeasure();
        setMeasuredDimension(scaleViewHelper.getWidth(), scaleViewHelper.getHeight());
    }

    public ScaleViewHelper getHelper() {
        return scaleViewHelper;
    }
}
