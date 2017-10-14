package yin.style.recyclerlib.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.BaseRefreshHeader;

import android.view.View;

import yin.style.recyclerlib.view.EmptyView;
import yin.style.recyclerlib.view.FooterView;
import yin.style.recyclerlib.view.HeaderView;

/**
 * Created by chenY on 2017/1/16.
 * <p>
 * 基本够用的 RecyclerView 的 间隔线
 */

public class BaseDividerItem extends RecyclerView.ItemDecoration {
    private int space;
    private int color = Color.parseColor("#DCDCDC");
    private Drawable mDivider;
    private Paint mPaint;

    private boolean linerAround = false;//是否显示边框线

    public BaseDividerItem(int space) {
        init(space, color, linerAround);
    }

    public BaseDividerItem(int space, int color) {
        init(space, color, linerAround);
    }

    public BaseDividerItem(int space, int color, boolean linerAround) {
        init(space, color, linerAround);
    }

    public BaseDividerItem(int space, Drawable mDivider) {
        this.space = space;
        this.mDivider = mDivider;
    }

    private void init(int space, int color, boolean linerAround) {
        this.space = space;
        this.color = color;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(space * 2);
        this.linerAround = linerAround;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() != null) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager && !(parent.getLayoutManager() instanceof GridLayoutManager)) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    if (view instanceof BaseRefreshHeader || view instanceof HeaderView)
                        outRect.set(0, 0, 0, 0);
                    else
                        outRect.set(space, 0, space, 0);
                } else {
                    if (view instanceof BaseRefreshHeader || view instanceof HeaderView)
                        outRect.set(0, 0, 0, 0);
                    else
                        outRect.set(0, space, 0, space);
                }
            } else {
                if (view instanceof BaseRefreshHeader || view instanceof HeaderView)
                    outRect.set(0, 0, 0, 0);
                else
                    outRect.set(space, space, space, space);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getLayoutManager() != null) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager && !(parent.getLayoutManager() instanceof GridLayoutManager)) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    drawHorizontal(c, parent);
                } else {
                    drawVertical(c, parent);
                }
            } else {
                //绘制 空布局
                if (!isEmptyView(c, parent, state)) {
                    //不是空的
                    if (linerAround)
                        drawGridLinerAround(c, parent);
                    else
                        drawGridLiner(c, parent);
                }
            }
        }
    }

    private boolean isEmptyView(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildCount() != 0) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                View view = parent.getChildAt(i);
                if (view instanceof EmptyView) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    layoutParams.height = parent.getBottom() - space * 2;
                    layoutParams.width = parent.getRight() - space * 2;
                    view.setLayoutParams(layoutParams);
                    return true;
                }
            }
        }
        return false;
    }

    //绘制纵向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();

        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof BaseRefreshHeader || child instanceof HeaderView)
                continue;

            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + space;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //绘制横向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();

        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof BaseRefreshHeader || child instanceof HeaderView)
                continue;

            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + space;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

        }
    }

    /**
     * 绘制边框
     */
    private void drawGridLinerAround(Canvas canvas, RecyclerView parent) {
        GridLayoutManager linearLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int childSize = parent.getChildCount();

        if (childSize < 1)
            return;
        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            if (child.getWidth() > linearLayoutManager.getDecoratedLeft(child)
                    && parent.getWidth() - linearLayoutManager.getDecoratedRight(child) < child.getWidth()) {
//                if (child instanceof HeaderView) {
//                    //Header
//                    child.setBackgroundColor(Color.RED);
//                    drawChildTop(canvas, child);
//                    drawChildLeft(canvas, child);
//                    drawChildRight(canvas, child);
//                } else if (child instanceof FooterView) {
//                    //Footer
//                    child.setBackgroundColor(Color.BLUE);
//                    drawChildTop(canvas, child);
//                }
            } else {
                drawChildTop(canvas, child);
                drawChildLeft(canvas, child);
                drawChildBottom(canvas, child);
                drawChildRight(canvas, child);
            }
        }
//        for (int j = 0; j < childSize; j++) {
//            if (!(headerCount != 0 && linearLayoutManager.findFirstVisibleItemPosition() == 0) || j / linearLayoutManager.getSpanCount() != 0) {
//                int i;
//                View child;
//                if (headerCount != 0 && linearLayoutManager.findFirstVisibleItemPosition() == 0) {
//                    i = j - linearLayoutManager.getSpanCount();
//                    child = parent.getChildAt(i + headerCount);
//                } else {
//                    i = j;
//                    child = parent.getChildAt(i);
//                }
//
//                //跳过 Footer 的边框
//                if (i > 1 && child.getHeight() != parent.getChildAt(i - 1).getHeight()) {
//                    continue;
//                }
//                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
//                int top, bottom, left, right;
//
//                //child 顶部
//                if (i / linearLayoutManager.getSpanCount() == 0) {
//                    top = child.getTop() - layoutParams.topMargin - space;
//                    bottom = top + space;
//                    right = child.getRight() + layoutParams.leftMargin + space;
//                    left = child.getLeft() - layoutParams.rightMargin - space;
//                    if (mDivider != null) {
//                        mDivider.setBounds(left, top, right, bottom);
//                        mDivider.draw(canvas);
//                    }
//                    if (mPaint != null) {
//                        canvas.drawRect(left, top, right, bottom, mPaint);
//                    }
//                }
//                //child 底部
//                if (i / linearLayoutManager.getSpanCount() == childSize / linearLayoutManager.getSpanCount()) {
//                    top = child.getBottom() + layoutParams.bottomMargin;
//                    bottom = top + space;
//                    right = child.getRight() + layoutParams.leftMargin + space;
//                    left = child.getLeft() - layoutParams.rightMargin - space;
//                    if (mDivider != null) {
//                        mDivider.setBounds(left, top, right, bottom);
//                        mDivider.draw(canvas);
//                    }
//                    if (mPaint != null) {
//                        canvas.drawRect(left, top, right, bottom, mPaint);
//                    }
//                }
//                //child 左边
//                if (i % linearLayoutManager.getSpanCount() == 0) {
//                    top = child.getTop() - layoutParams.topMargin - layoutParams.bottomMargin - space;
//                    bottom = child.getBottom() + space;
//                    left = child.getLeft() - layoutParams.leftMargin - space;
//                    right = left + space;
//                    if (mDivider != null) {
//                        mDivider.setBounds(left, top, right, bottom);
//                        mDivider.draw(canvas);
//                    }
//                    if (mPaint != null) {
//                        canvas.drawRect(left, top, right, bottom, mPaint);
//                    }
//                }
//                //child 右边
//                if (i % linearLayoutManager.getSpanCount() == linearLayoutManager.getSpanCount() - 1) {
//                    top = child.getTop() - layoutParams.topMargin - layoutParams.bottomMargin - space;
//                    bottom = child.getBottom() + space;
//                    left = child.getRight() + layoutParams.rightMargin;
//                    right = left + space;
//                    if (mDivider != null) {
//                        mDivider.setBounds(left, top, right, bottom);
//                        mDivider.draw(canvas);
//                    }
//                    if (mPaint != null) {
//                        canvas.drawRect(left, top, right, bottom, mPaint);
//                    }
//                }
//            }
//        }
    }

    //绘制gridview item 分割线 不是填充满的
    private void drawGridLiner(Canvas canvas, RecyclerView parent) {
        GridLayoutManager linearLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int childSize = parent.getChildCount();

        if (childSize < 1)
            return;

        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            if (child.getWidth() > linearLayoutManager.getDecoratedLeft(child)
                    && parent.getWidth() - linearLayoutManager.getDecoratedRight(child) < child.getWidth()) {
                if (child instanceof BaseRefreshHeader || child instanceof HeaderView) {
                    //Header
                    drawChildBottom(canvas, child);
                } else {
                    //Footer
                    drawChildTop(canvas, child);
                }
            } else if (child.getWidth() > linearLayoutManager.getDecoratedLeft(child)
                    && linearLayoutManager.getDecoratedRight(child) < child.getWidth() * 1.5) {
                //每行的 第一个View
                drawChildBottom(canvas, child);
            } else {
                //每行的 非第一个View
                drawChildBottom(canvas, child);
                drawChildLeft(canvas, child);
            }
        }
    }

    private int top, bottom, left, right;

    // ItemView 的Top画线
    private void drawChildTop(Canvas canvas, View child) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        top = child.getTop() - layoutParams.topMargin - space;
        bottom = top + space;
        right = child.getRight() + layoutParams.leftMargin + space;
        left = child.getLeft() - layoutParams.rightMargin - space;
        if (mDivider != null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    // ItemView 的Right 画线
    private void drawChildRight(Canvas canvas, View child) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        top = child.getTop() - layoutParams.topMargin - space;
        bottom = child.getBottom() + space + layoutParams.bottomMargin;
        left = child.getRight() + layoutParams.rightMargin;
        right = left + space;

        if (mDivider != null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    // ItemView 的Bottom画线
    private void drawChildBottom(Canvas canvas, View child) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        top = child.getBottom() + layoutParams.bottomMargin;
        bottom = top + space;
        right = child.getRight() + layoutParams.leftMargin + space;
        left = child.getLeft() - layoutParams.rightMargin - space;
        if (mDivider != null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    // ItemView 的Left 画线
    private void drawChildLeft(Canvas canvas, View child) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        top = child.getTop() - layoutParams.topMargin - space;
        bottom = child.getBottom() + space + layoutParams.bottomMargin;
        left = child.getLeft() - layoutParams.leftMargin - space;
        right = left + space;
        if (mDivider != null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    public void setLinerAround(boolean linerAround) {
        this.linerAround = linerAround;
    }
}
