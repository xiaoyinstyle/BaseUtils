package yin.style.recyclerlib.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.decoration.BaseDividerItem;

/**
 * Created by ChneY on 2017/1/15.
 */

public class LoadSwipeRefreshLayout extends SwipeRefreshLayout {
    private RecyclerView mRecyclerView;

    private GridLayoutManager layoutManager;

    private boolean linerAround = false;//是否显示 边框线
    private int gridNumb = 1;//Grid的数目
    private int linerWidth = 0;//间隔线的宽度
    private int linerColor = Color.LTGRAY;//间隔线的颜色 默认灰色

    private BaseQuickAdapter mAdapter;
    private boolean canLoadMore = true;//是否可以继续加载

    private boolean isLoading = false;//是否正在加载中
    private OnLoadRefreshListener mListener;//监听

    public LoadSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public LoadSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(Context context) {
        if (mAdapter == null)
            return;
        mRecyclerView = new RecyclerView(context);
        addView(mRecyclerView);
        BaseDividerItem mDividerItemDecoration = new BaseDividerItem(linerWidth, linerColor);
        mDividerItemDecoration.setHeaderCount(mAdapter.getHeaderCount());
        mDividerItemDecoration.setLinerAround(linerAround);

        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        layoutManager = new GridLayoutManager(context, gridNumb);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //加载
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.e("test", "onScrolled");
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
//                    Log.d("test", "loading executed");
                    boolean isRefreshing = LoadSwipeRefreshLayout.this.isRefreshing();
                    if (isRefreshing || !canLoadMore || mAdapter.getItemCount() < 2) {
                        return;
                    }
                    if (!isLoading) {
                        setLoadMore();
                    }
                }
            }
        });

        //刷新
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Log.d("test", "setOnRefreshListener");
                if (isLoading) {
                    setRefreshing(false);
                    return;
                }
                if (mListener != null) {
                    canLoadMore = true;
                    mListener.onRefresh();
                }
            }
        });

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.isFullView(position) ? layoutManager.getSpanCount() : 1;
            }
        });
    }

    /**
     * 加载更多
     */
    private void setLoadMore() {
        isLoading = true;
        mAdapter.showFooterView();
        if (mListener != null) {
            mListener.onLoad();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 设置是否 正在加载中
     *
     * @param refreshing
     */
    public void setLoading(boolean refreshing) {
        if (refreshing) {
            setLoadMore();
        } else {
            isLoading = false;
            mAdapter.removeFooterView();
        }
    }

    /**
     *
     */
    public void setAdapter(BaseQuickAdapter adapter) {
        this.mAdapter = adapter;
        initView(getContext());
    }

    /**
     * 是否可以加载更多
     *
     * @param b
     */
    public void setCanLoadMore(boolean b) {
        this.canLoadMore = b;
        if (!b) {
            isLoading = false;
            if (mAdapter != null)
                mAdapter.removeFooterView();
        }
    }

    /**
     * 显示 外部 边框线
     *
     * @param linerAround
     */
    public void setLinerAround(boolean linerAround) {
        this.linerAround = linerAround;
    }

    /**
     * 设置 GridView 的 间隔线 颜色
     *
     * @param linerColor
     */
    public void setLinerColor(int linerColor) {
        this.linerColor = linerColor;
    }

    /**
     * 设置 GridView 的 间隔线
     * 宽度
     *
     * @param linerWidth
     */
    public void setLinerWidth(int linerWidth) {
        this.linerWidth = linerWidth;
    }

    /**
     * 设置 Grid 的数目
     *
     * @param gridNumb
     */
    public void setGridNumb(int gridNumb) {
        this.gridNumb = gridNumb;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public GridLayoutManager getLayoutManager() {
        return layoutManager;
    }
    /**
     * 监听器实现
     *
     * @param listener
     */
    public void setOnLoadRefreshListener(OnLoadRefreshListener listener) {
        mListener = listener;
    }

    /**
     * 加载刷新接口
     */
    public interface OnLoadRefreshListener {
        void onRefresh();

        void onLoad();
    }

}
