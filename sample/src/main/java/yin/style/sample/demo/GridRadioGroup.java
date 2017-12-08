package yin.style.sample.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Created by Chne on 2017/12/1.
 */

public class GridRadioGroup extends RadioGroup {

    private int column = 2;

    private int maxSizeWidth[];
    private int maxSizeHeight[];

    public GridRadioGroup(Context context) {
        super(context);
    }

    public GridRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        maxSizeWidth = new int[column];
        int childCount = getChildCount();
        maxSizeHeight = new int[childCount / column == 0 ? childCount / column : childCount / column + 1];

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int layoutWidth = 0;
        int layoutHeight = 0;

        int cWidth = 0;

        int cHeight = 0;
        int maxHeight = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            //如果布局容器的宽度模式是确定的（具体的size或者match_parent），直接使用父窗体建议的宽度
            layoutWidth = sizeWidth;
        } else {
            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                maxSizeWidth[i % column] = maxSizeWidth[i % column] > child.getMeasuredWidth() ? maxSizeWidth[i % column] : child.getMeasuredWidth();

//                if (i % column == 0) {
//                    layoutWidth = cWidth > layoutWidth ? cWidth : layoutWidth;
//                    cWidth = child.getMeasuredWidth();
//                } else {
//                    cWidth = cWidth + child.getMeasuredWidth();
//                }
            }
            for (int i = 0; i < column; i++) {
                layoutWidth = layoutWidth + maxSizeWidth[i];
            }
            //获取子控件最大宽度
            layoutWidth = cWidth > layoutWidth ? cWidth : layoutWidth;
        }

        //高度很宽度处理思想一样
        if (heightMode == MeasureSpec.EXACTLY) {
            layoutHeight = sizeHeight;
        } else {
            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                maxSizeHeight[i / column] = maxSizeHeight[i / column] > child.getMeasuredWidth() ? maxSizeHeight[i / column] : child.getMeasuredWidth();

                if (i % column == 0) {
                    maxHeight = child.getMeasuredHeight();
                    cHeight = cHeight + maxHeight;
                    layoutHeight = layoutHeight > cHeight ? layoutHeight : cHeight;
                } else {
                    maxHeight = maxHeight > child.getMeasuredHeight() ? maxHeight : child.getMeasuredHeight();
                }
            }
            //获取子控件最大宽度
            layoutHeight = layoutHeight > cHeight ? layoutHeight : cHeight;
        }


        // 测量并保存layout的宽高
        setMeasuredDimension(layoutWidth, layoutHeight);

    }

    /**
     * 为所有的子控件摆放位置.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;    // 容器已经占据的宽度
        int layoutHeight = 0;   // 容器已经占据的宽度
        int maxChildHeight = 0; //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = maxSizeWidth[i % column];
            childMeasureHeight = child.getMeasuredHeight();
            if (i % column == 0) {
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            } else {
                //如果一行没有排满，继续往右排列
                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            }

            layoutWidth += childMeasureWidth;  //宽度累加
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight;
            }

            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }


    }

    public void setColumn(int column) {
        this.column = column;
        invalidate();
    }
}
