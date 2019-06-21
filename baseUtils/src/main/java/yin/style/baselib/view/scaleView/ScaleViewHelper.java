package yin.style.baselib.view.scaleView;

import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import yin.style.baselib.R;

/**
 * Created by ChneY on 2017/5/7.
 * <p>
 * 自定义按比例设置宽高的View
 */

/**
 * Created by ChneY on 2017/5/7.
 * <p>
 * ref R.styleable.ScaleView_scale_weight_width
 * ref R.styleable.ScaleView_scale_weight_height
 * ref R.styleable.ScaleView_scale_references
 * ref R.styleable.ScaleView_scale_max_width
 * ref R.styleable.ScaleView_scale_max_height
 * ref R.styleable.ScaleView_scale_min_width
 * ref R.styleable.ScaleView_scale_min_height
 */
public class ScaleViewHelper {
    @IntDef({WIDTH, HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ReferencesMode {
    }

    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;

    private View view;

    private int width = 100;
    private int height = 100;

    private int maxWidth = -1;
    private int maxHeight = -1;
    private int minWidth = -1;
    private int minHeight = -1;

    private float widthWeight = 1f;
    private float heightWeight = 1f;
    private int referencesMode = WIDTH;

    public ScaleViewHelper(View view) {
        this.view = view;
//        onMeasure();
    }

    public ScaleViewHelper(View view, AttributeSet attrs, int defStyleAttr) {
        this.view = view;

        //load styled attributes.
        if (attrs != null) {
            TypedArray attributes = view.getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ScaleView,
                    defStyleAttr, 0);

            widthWeight = attributes.getFloat(R.styleable.ScaleView_scale_weight_width, widthWeight);
            heightWeight = attributes.getFloat(R.styleable.ScaleView_scale_weight_height, heightWeight);
            referencesMode = attributes.getInt(R.styleable.ScaleView_scale_references, referencesMode);

            maxWidth = attributes.getDimensionPixelSize(R.styleable.ScaleView_scale_max_width, maxWidth);
            maxHeight = attributes.getDimensionPixelSize(R.styleable.ScaleView_scale_max_height, maxHeight);

            minWidth = attributes.getDimensionPixelSize(R.styleable.ScaleView_scale_min_width, minWidth);
            minHeight = attributes.getDimensionPixelSize(R.styleable.ScaleView_scale_min_height, minHeight);

            attributes.recycle();
        }
    }

    public void onMeasure() {
        if (referencesMode == WIDTH) {
            width = CalculationWidth();
            height = (int) (heightWeight / widthWeight * width);

//            LogUtils.e("ScaleViewHelper", "width" + width);
//            LogUtils.e("ScaleViewHelper", "height" + height);
        } else {
            height = CalculationHeight();
            width = (int) (widthWeight / heightWeight * height);

//            LogUtils.e("ScaleViewHelper", "width" + width);
//            LogUtils.e("ScaleViewHelper", "height" + height);
        }
    }

    /**
     * 计算宽的长度
     */
    private int CalculationWidth() {
        width = view.getMeasuredWidth();

        if (minWidth < 0 && maxWidth < 0)
            return width;
        else if (minWidth < 0 && maxWidth > 0) {
            return Math.min(width, maxWidth);
        } else if (minWidth > 0 && maxWidth < 0) {
            return Math.max(minWidth, width);
        } else {
            int temp = Math.min(width, maxWidth);
            return Math.max(minWidth, temp);
        }
    }

    /**
     * 计算长的长度
     */
    private int CalculationHeight() {
        height = view.getMeasuredHeight();

        if (minHeight < 0 && maxHeight < 0)
            return height;
        else if (minHeight < 0 && maxHeight > 0) {
            return Math.min(height, maxHeight);
        } else if (minHeight > 0 && maxHeight < 0) {
            return Math.max(minHeight, height);
        } else {
            int temp = Math.min(height, maxHeight);
            return Math.max(minHeight, temp);
        }
    }

//
//    /**
//     * 计算长的长度
//     *
//     * @param widthMeasureSpec
//     */
//    private void CalculationHeight(int measureSpec) {
//        int specMode = View.MeasureSpec.getMode(measureSpec);
//        int specSize = View.MeasureSpec.getSize(measureSpec);
//
//        if (specMode == View.MeasureSpec.EXACTLY) {
//            result = specSize;
//        } else if (specMode == View.MeasureSpec.AT_MOST) {
//            result = Math.min(result, specSize);
//        } else {
//            //未定义
//            result = result;
//        }
//        width = view.getMeasuredWidth();
//    }

    /**
     * 获取计算之后的 宽度
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * 获取计算之后的 高度
     *
     * @return
     */
    public int getHeight() {
        return height;
    }


    public void setReferencesMode(@ReferencesMode int referencesMode) {
        this.referencesMode = referencesMode;
    }


    public void setWeight(float widthWeight, float heightWeight) {
        this.heightWeight = heightWeight;
        this.widthWeight = widthWeight;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;

    }

    /**
     * 手动触发
     */
    public void measure() {
        onMeasure();

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = getHeight();
        params.height = getWidth();
        view.setLayoutParams(params);
    }
}
