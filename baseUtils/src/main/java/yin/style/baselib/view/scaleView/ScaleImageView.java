package yin.style.baselib.view.scaleView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ChneY on 2017/5/7.
 * <p>
 * A {@link ImageView} that re-sizes
 *
 * @attr ref R.styleable.ScaleView_scale_weight_width
 * @attr ref R.styleable.ScaleView_scale_weight_height
 * @attr ref R.styleable.ScaleView_scale_references
 * @attr ref R.styleable.ScaleView_scale_max_width
 * @attr ref R.styleable.ScaleView_scale_max_height
 * @attr ref R.styleable.ScaleView_scale_min_width
 * @attr ref R.styleable.ScaleView_scale_min_height
 */
public class ScaleImageView extends ImageView {

    ScaleViewHelper scaleViewHelper;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
