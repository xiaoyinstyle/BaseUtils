package yin.style.recyclerlib.decoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

/**
 * Created by chenY on 2017/1/19.
 */

public abstract class BaseTimeLineDecoration<T> extends RecyclerView.ItemDecoration {
    private int linteWidth = 10; //线条的宽度
    private int color = Color.GRAY; //默认颜色
    private Paint paintHorizontal;//水平的线
    private Paint paintVertical;//竖着的线
    private Paint paintCircle;//节点 空心圆

    private Bitmap nodeBitmap;//自定义 节点 Bitmap

    private int headerCount = 0;//HeaderView 数目
    private int footerCount = 0;// footerView 数目

    protected List<T> mData;
    protected Context mContext;

    private int ToplineHeight = 50;//第一段线的高度

    private int itemStep = 0;//Item上下空距
    private int step;//空距

    private int nodeWidth;//节点宽度

    public BaseTimeLineDecoration(List<T> mData) {
        init(linteWidth, mData, color);
    }

    public BaseTimeLineDecoration(List<T> mData, int color) {
        init(linteWidth, mData, color);
    }

    private void init(int linteWidth, List<T> mData, int color) {
        this.linteWidth = linteWidth;
        this.mData = mData;
        this.color = color;

        paintVertical = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintVertical.setColor(color);
        paintVertical.setStyle(Paint.Style.FILL);
        paintVertical.setStrokeWidth(linteWidth);

        paintHorizontal = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHorizontal.setColor(color);
        paintHorizontal.setStyle(Paint.Style.FILL);
        paintHorizontal.setStrokeWidth(linteWidth);

        if (nodeBitmap == null) {
            paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintCircle.setColor(color);
            paintCircle.setStrokeWidth(linteWidth / 2);
            paintCircle.setAntiAlias(true); //去锯齿
            paintCircle.setStyle(Paint.Style.STROKE);//空心
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        mContext = view.getContext();
        int posit = parent.getChildAdapterPosition(view);
        //HeaderView和FooterView 占满一行
        if ((headerCount != 0 && posit == 0) || (posit - headerCount - footerCount == mData.size())) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            p.setFullSpan(true);
            outRect.set(0, 0, 0, 0);
            return;
        }
//        Log.e("test", "" + posit);
        if ((posit - headerCount) % 2 == 0)
            outRect.set(0, itemStep, parent.getWidth() / 15, itemStep);
        else if (posit == 1 + headerCount) {
            int height = getToplineHeight(parent.getChildAt(headerCount));
            ToplineHeight = (height == 0 ? ToplineHeight : height);
            outRect.set(parent.getWidth() / 15, (parent.getChildAt(headerCount).getHeight() + itemStep + nodeWidth + step * 2) / 2 + itemStep, 0, itemStep);
        } else
            outRect.set(parent.getWidth() / 15, itemStep, 0, itemStep);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        if (parent.getLayoutManager() != null) {
            if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                if (((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount() == 2) {
                    drawTimeline(canvas, parent);
                }
            }
        }


    }

    private void drawTimeline(Canvas canvas, RecyclerView parent) {
        step = linteWidth / 2;
        nodeWidth = (nodeWidth == 0 ? linteWidth : nodeWidth);

        int childSize = parent.getChildCount();

        int centerLeft = (parent.getWidth() - linteWidth) / 2;
        int centerRight = centerLeft + linteWidth;

        if (headerCount != 0 && childSize < headerCount + footerCount)
            return;

        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            if ((parent.getChildAdapterPosition(child) == 0 && i < headerCount) || parent.getChildAdapterPosition(child) - headerCount - footerCount == mData.size()) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                params.width = parent.getWidth();
                child.setLayoutParams(params);
                continue;
            }
            int centerTop = child.getTop();
            if (parent.getChildAdapterPosition(child) == headerCount)
                centerTop = child.getTop() - itemStep;

            int centerBottom = child.getTop() + ToplineHeight;

            // 节点的中心 XY位置
            int NodeX = centerLeft + linteWidth / 2;
            int NodeY = centerBottom + nodeWidth / 2 + step;

            int CircleColor = getCircleColor(parent.getChildAdapterPosition(child) - headerCount);

            //绘制 第一根线 竖线
            canvas.drawRect(centerLeft, centerTop, centerRight, centerBottom, paintVertical);
//            Log.e("test", "    " + parent.getChildAdapterPosition(child)-headerCount);

            //绘制 节点
            if (nodeBitmap == null) {
                paintCircle.setColor(CircleColor == 0 ? color : CircleColor);
                canvas.drawCircle(NodeX, NodeY, nodeWidth / 2, paintCircle);
            } else {
//                canvas.drawBitmap(nodeBitmap);
            }

            //绘制  的第二根线竖线
            if (i + 1 < childSize) {
                View NextChild = parent.getChildAt(i + 1);
                canvas.drawRect(centerLeft, NodeY + nodeWidth / 2 + step, centerRight, NextChild.getTop(), paintVertical);
            } else {//最后一个
                canvas.drawRect(centerLeft, NodeY + nodeWidth / 2 + step, centerRight, child.getBottom() + itemStep, paintVertical);
            }

            paintHorizontal.setColor(CircleColor == 0 ? color : CircleColor);
            // 绘制  横线
            if (child.getRight() < parent.getWidth() / 2) {//左边
                canvas.drawRect(child.getRight(), NodeY - linteWidth / 2, NodeX - nodeWidth / 2 - step, NodeY + linteWidth / 2, paintHorizontal);
            } else {//右边
                canvas.drawRect(NodeX + nodeWidth / 2 + step, NodeY - linteWidth / 2, child.getLeft(), NodeY + linteWidth / 2, paintHorizontal);
            }

            setViewItem(child, parent.getChildAdapterPosition(child) - headerCount);
        }
    }

    /**
     * 设置 每个 iten的
     *
     * @param ChildView 每个Item的 View
     * @param position  位置
     */
    protected abstract void setViewItem(View ChildView, int position);

    /**
     * 设置 第一条线的高度
     *
     * @param FirstChildView
     * @return
     */
    protected abstract int getToplineHeight(View FirstChildView);

    /**
     * 设置 节点 圆的颜色
     *
     * @param position
     * @return
     */
    protected abstract int getCircleColor(int position);

    /**
     * 自定义 节点 Bitmap
     *
     * @param nodeBitmap
     */
    public void setNodeViwe(Bitmap nodeBitmap) {
        this.nodeBitmap = nodeBitmap;
    }

    /**
     * 上下间距
     *
     * @param itemStep
     */
    public void setItemStep(int itemStep) {
        this.itemStep = itemStep / 2;
    }

    /**
     * 节点 的 宽度
     *
     * @param nodeWidth
     */
    public void setNodeWidth(int nodeWidth) {
        this.nodeWidth = nodeWidth;
    }

    /**
     * 是否含有 Header
     */
    public void setHasHeader(boolean ishasHeader) {
        this.headerCount = ishasHeader ? 1 : 0;
    }

    public void setHasHeader(int ishasHeader) {
        this.headerCount = ishasHeader != 0 ? 1 : 0;
    }

    /**
     * 是否含有 Header
     */
    public void setFooterCount(boolean footerCount) {
        this.footerCount = footerCount ? 1 : 0;
    }

    public void setFooterCount(int footerCount) {
        this.footerCount = footerCount != 0 ? 1 : 0;
    }

    /**
     * 线宽
     */
    public void setLinteWidth(int linteWidth) {
        this.linteWidth = linteWidth;
    }

}