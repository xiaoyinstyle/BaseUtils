package com.jskingen.baselib.activity.base;

import android.os.Bundle;

import com.jskingen.baselib.R;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.view.LoadSwipeRefreshLayout;

/**
 * Created by ChneY on 2017/4/27.
 */

public abstract class LoadRefreshActivity extends TitleActivity implements LoadSwipeRefreshLayout.OnLoadRefreshListener {
    protected LoadSwipeRefreshLayout loadSwipeRefreshLayout;

    @Override
    protected int getViewByXml() {
        return R.layout.base_activity_loadrefresh;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadSwipeRefreshLayout = (LoadSwipeRefreshLayout) findViewById(R.id.loadSwipeRefreshLayout);

        loadSwipeRefreshLayout.setGridNumb(setGridNub());//可以加载更多
        loadSwipeRefreshLayout.setCanLoadMore(true);//可以加载更多
        loadSwipeRefreshLayout.setAdapter(getAdapter());
        loadSwipeRefreshLayout.setOnLoadRefreshListener(this);
    }

    protected abstract BaseQuickAdapter getAdapter();


    @Override
    protected void initData() {
        onRefresh();
    }

    protected void setLoading(boolean loading) {
        loadSwipeRefreshLayout.setLoading(loading);
    }

    protected void setRefreshing(boolean refreshing) {
        loadSwipeRefreshLayout.setRefreshing(refreshing);
    }

    protected void setCanLoadMore(boolean canLoad) {
        loadSwipeRefreshLayout.setCanLoadMore(canLoad);
    }

    protected void setCanRefresh(boolean canRefresh) {
        loadSwipeRefreshLayout.setRefreshing(canRefresh);
    }

    protected int setGridNub() {
        return 1;
    }
}
