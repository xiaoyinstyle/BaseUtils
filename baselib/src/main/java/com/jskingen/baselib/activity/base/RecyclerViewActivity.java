package com.jskingen.baselib.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jskingen.baselib.R;

import yin.style.recyclerlib.decoration.BaseDividerItem;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * RecyclerViewActivity,可进行 列表或者表格
 */

public abstract class RecyclerViewActivity extends TitleActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final int LINEARLAYOUT = 0;
    public static final int GRIDLAYOUT = 1;

    protected int type = LINEARLAYOUT;
    private int spanCount = 2;

    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    private boolean isCanRefresh = true;

    @Override
    protected int getViewByXml() {
        return R.layout.base_activity_recyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        initType();
        if (type == LINEARLAYOUT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(getItemDecoration());
        } else {
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, spanCount);
            recyclerView.setLayoutManager(mGridLayoutManager);
            recyclerView.addItemDecoration(getItemDecoration());
        }

        if (null == setAdapter())
            throw new NullPointerException("RecyclerView.Adapter is not null");
        else
            recyclerView.setAdapter(setAdapter());

        //刷新功能
        isCanRefresh = setCanRefresh(swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(isCanRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        if (isCanRefresh)
            swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    /**
     * 重写这个方法 来控制 是否可以 刷新数据
     */
    protected abstract boolean setCanRefresh(SwipeRefreshLayout swipeRefreshLayout);

    protected void setRefreshing(boolean refreshing) {
        if (null != swipeRefreshLayout)
            swipeRefreshLayout.setRefreshing(refreshing);
    }

    /**
     * 设置类型 LinearLayout还是GridLayout
     */
    protected void initType() {
        setType(LINEARLAYOUT, spanCount);
    }

    protected void setType(int type, int spanCount) {
        if (type != LINEARLAYOUT && type != GRIDLAYOUT) {
            throw new IllegalArgumentException("type must be one of LINEARLAYOUT and GRIDLAYOUT");
        }

        this.type = type;
        this.spanCount = spanCount;
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new BaseDividerItem(setItemWidth(), setItemColor());
    }

    protected abstract RecyclerView.Adapter setAdapter();

    protected int setItemWidth() {
        return 2;
    }

    protected int setItemColor() {
        return Color.parseColor("#DCDCDC");
    }
}
