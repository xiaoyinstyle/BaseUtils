package yin.style.baselib.view.scaleView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import yin.style.baselib.R;

/**
 * Created by ChneY on 2017/5/7.
 *
 * @attr ref R.styleable.ScaleView_scale_weight_width
 * @attr ref R.styleable.ScaleView_scale_weight_height
 * @attr ref R.styleable.ScaleView_scale_references
 * @attr ref R.styleable.ScaleView_scale_max_width
 * @attr ref R.styleable.ScaleView_scale_max_height
 * @attr ref R.styleable.ScaleView_scale_min_width
 * @attr ref R.styleable.ScaleView_scale_min_height
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
