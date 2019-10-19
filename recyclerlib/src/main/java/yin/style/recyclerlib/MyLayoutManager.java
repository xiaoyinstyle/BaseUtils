package yin.style.recyclerlib;

import android.graphics.Rect;

import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 陈银 on 2017/8/3. 12:28
 * <p>
 * 描述： 自定义点击放大的 一个布局
 */

public class MyLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "AAA";
    //保存所有item的偏移信息
    private SparseArrayCompat<Rect> itemFrames = new SparseArrayCompat<>();
    //总的高度和宽度
    private int mTotalHeight;
    private final int SCREEN_VERTICAL = 0;// 屏幕 竖屏
    private final int SCREEN_HORIZONTAL = 1;//屏幕 横屏
    private int screenDirection;// 屏幕 方向
    private int verticalOffset;//竖直方向的偏移

    private int mainViewHeight = 0;
    private int mainViewWidth = 0;
    private int childViewWidth = 0;
    private int childViewHeight = 0;
    private int bigItem = 0;
    private float perWidth = 0.3f;

    private RecyclerView mRecyclerView;
    private int gridNumb = 1;
    private boolean canScroll;//是否可刷新
    private boolean isLoadComplete = true;

    public MyLayoutManager(RecyclerView recyclerView) {
        this(recyclerView, 1);
    }

    public MyLayoutManager(RecyclerView recyclerView, int gridNumb) {
        this(recyclerView, gridNumb, 0.3f);
    }

    public MyLayoutManager(RecyclerView recyclerView, int gridNumb, float perWidth) {
        mRecyclerView = recyclerView;
        this.gridNumb = gridNumb;

        perWidth = perWidth < 0.1 ? 0.1f : perWidth;
        perWidth = perWidth > 0.9 ? 0.9f : perWidth;

        this.perWidth = perWidth;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        super.onItemsChanged(recyclerView);
        removeAllViews();
        setBigItem(bigItem, false);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout() /*|| !isLoadComplete*/) {
            return;
        }
        screenDirection = getHeight() > getWidth() ? SCREEN_VERTICAL : SCREEN_HORIZONTAL;

        detachAndScrapAttachedViews(recycler);
        Log.e(TAG, screenDirection + "__onLayoutChildren__" + getItemCount());

        calculationChildViewSize(recycler);

        fill(recycler, state);

        isLoadComplete = false;

        for (int i = 0; i < itemFrames.size(); i++) {
            Log.e("BBB_" + i, itemFrames.get(i).width() + "___" + itemFrames.get(i).height());
        }
    }

    private void calculationChildViewSize(RecyclerView.Recycler recycler) {
        int totalHeight = 0;
        int offsetX;
        int offsetY = 0;

        if (screenDirection == SCREEN_VERTICAL) {
            mainViewHeight = (int) (getHeight() * perWidth);
            mainViewWidth = 0;

            totalHeight = mainViewHeight;
            offsetY = mainViewHeight;
        } else {
            mainViewHeight = 0;
            mainViewWidth = (int) (getWidth() * perWidth);
        }
        offsetX = mainViewWidth;
        childViewWidth = (getWidth() - mainViewWidth) / gridNumb;

        //计算每个item的位置信息,存储在itemFrames里面
        for (int i = 0; i < getItemCount(); i++) {
            //从缓存中取出
            View view = recycler.getViewForPosition(i);
            //添加到RecyclerView中
            addView(view);
            //测量
            measureChildWithMargins(view, 0, 0);
            //获取测量后的宽高
            int height = i == bigItem ? mainViewHeight : getDecoratedMeasuredHeight(view);
            //边界信息保存到Rect里面
            Rect rect = itemFrames.get(i);
            if (rect == null) {
                rect = new Rect();
            }
            if (i == bigItem) {
                rect.set(0, 0, getWidth(), mainViewHeight);
            } else if (i < bigItem) {
                rect.set(offsetX, offsetY - mainViewHeight, offsetX + childViewWidth, offsetY + height - mainViewHeight);
            } else {
                rect.set(offsetX, offsetY, offsetX + childViewWidth, offsetY + height);
            }

            itemFrames.put(i, rect);
            //横竖方向的偏移
            if (i > bigItem) {
                if ((i - 1) % gridNumb + 1 == gridNumb) {
                    offsetX = mainViewWidth;
                    offsetY += height;
                } else {
                    offsetX += childViewWidth;
                    if ((i - 1) % gridNumb == 0) {
                        //把每一个子View的宽高加起来获得总的
                        totalHeight += height;
                    }
                }
            } else if (i < bigItem) {
                if (i % gridNumb + 1 == gridNumb) {
                    offsetX = mainViewWidth;
                    offsetY += height;
                } else {
                    offsetX += childViewWidth;
                    //把每一个子View的宽高加起来获得总的
                    if (i % gridNumb == 0) {
                        totalHeight += height;
                    }
                }
            }
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
            if (i != bigItem) {
                lp.width = childViewWidth;
                lp.height = height;
            } else if (screenDirection == SCREEN_VERTICAL) {
                lp.width = getWidth();
                lp.height = mainViewHeight;
            } else {
                lp.width = mainViewWidth;
                lp.height = getHeight();
            }
            view.setLayoutParams(lp);
        }
        mTotalHeight = Math.max(totalHeight, getVerticalSpace());
        Log.e("AAA_hight", mTotalHeight + "__" + getHeight());
        canScroll = mTotalHeight > getHeight();
    }

    //回收不必要的view（超出屏幕的），取出需要的显示出来
    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.e(TAG, "fill " + verticalOffset);
        //获得屏幕的边界信息
        Rect displayFrame1 = new Rect(0, verticalOffset, getHorizontalSpace(),
                verticalOffset + getVerticalSpace());
        Rect displayFrame2 = new Rect(0, verticalOffset + mainViewHeight, getHorizontalSpace(),
                verticalOffset + getVerticalSpace());
        Rect displayFrame;
        //滑出屏幕回收到缓存中
        Rect childFrame = new Rect();
        Log.e(TAG, "getChildCount " + getChildCount());

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            childFrame.left = getDecoratedLeft(view);
            childFrame.top = getDecoratedTop(view);
            childFrame.right = getDecoratedRight(view);
            childFrame.bottom = getDecoratedBottom(view);
            if (getPosition(view) == bigItem) {
                Log.e(TAG, "view " + childFrame.left + "\n" +
                        childFrame.top + "\n" +
                        childFrame.right + "\n" +
                        childFrame.bottom);
            }
            //判断是否在显示区域里面
            if (!Rect.intersects(displayFrame1, childFrame) && getPosition(view) != bigItem) {
                removeAndRecycleView(view, recycler);
            }
        }

        //在屏幕上显示出
        for (int i = 0; i < getItemCount(); i++) {
            displayFrame = i < bigItem ? displayFrame1 : displayFrame2;
            if (Rect.intersects(displayFrame, itemFrames.get(i))) {//判断是否在屏幕中
                View view = recycler.getViewForPosition(i);
                Log.e(TAG, "intersects " + getPosition(view) + "__" + true);
                if (getPosition(view) > bigItem) {
                    addView(view);
                    measureChildWithMargins(view, 0, 0);
                    Rect rect = itemFrames.get(i);
                    layoutDecorated(view, rect.left, rect.top - verticalOffset,
                            rect.right, rect.bottom - verticalOffset);
                } else if (getPosition(view) < bigItem) {
                    addView(view);
                    measureChildWithMargins(view, 0, 0);
                    Rect rect = itemFrames.get(i);
                    layoutDecorated(view, rect.left, mainViewHeight + rect.top - verticalOffset,
                            rect.right, mainViewHeight + rect.bottom - verticalOffset);
                }
            }
        }

        setBigItemView(recycler);
    }

    @Override
    public boolean canScrollVertically() {
        return canScroll;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (verticalOffset + dy < 0) {//滑动到最顶部
            dy = -verticalOffset;
        } else if (verticalOffset + dy > mTotalHeight - getVerticalSpace()) {//滑动到底部
            dy = mTotalHeight - getVerticalSpace() - verticalOffset;
        }

        offsetChildrenVertical(-dy);
        fill(recycler, state);
        verticalOffset += dy;
        return dy;
    }

    //获取控件的竖直高度
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    //获取控件的水平宽度
    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public void setBigItem(int bigItem) {
        setBigItem(bigItem, true);
    }

    public void setBigItem(int bigItem, boolean isRefresh) {
        this.bigItem = bigItem;
        isLoadComplete = true;

        if (mRecyclerView.getAdapter() == null)
            return;
        if (isRefresh)
            mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollBy(0, 1);
            }
        }, 150);
    }

    private void setBigItemView(RecyclerView.Recycler recycler) {
        View v = recycler.getViewForPosition(bigItem);
        addView(v);
        measureChildWithMargins(v, 0, 0);
        Log.e(TAG, "andRecycleView " + getPosition(v));
        if (screenDirection == SCREEN_VERTICAL) {
            layoutDecorated(v, 0, 0, getWidth(), mainViewHeight);
        } else {
            layoutDecorated(v, 0, 0, mainViewWidth, getHeight());
        }
    }
}