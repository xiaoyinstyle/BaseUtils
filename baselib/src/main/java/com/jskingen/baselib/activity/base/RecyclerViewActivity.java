package com.jskingen.baselib.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jskingen.baselib.R;

import yin.style.recyclerlib.decoration.BaseDividerItem;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * RecyclerViewActivity,可进行 列表或者表格
 */

public abstract class RecyclerViewActivity extends TitleActivity {

    protected XRecyclerView mRecyclerView;

    @Override
    protected int getViewByXml() {
        return R.layout.base_activity_recyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);

        setLayoutManager();
        setItemDecoration();
        setCanRefresh(false);
        setCanLoading(false);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        if (null == setAdapter())
            throw new NullPointerException("RecyclerView adapter is not null");
        else
            mRecyclerView.setAdapter(setAdapter());
    }

    protected void setLayoutManager() {
        if (setGridNumb() <= 1) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(this, setGridNumb());
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    /**
     * 设置刷新 或者加载的监听
     *
     * @param listener
     */
    protected void setListener(XRecyclerView.LoadingListener listener) {
        mRecyclerView.setLoadingListener(listener);
    }

    /**
     * 重写这个 可以对 RecyclerView 进行设置 setLayoutManager
     * <p>
     * 小于等于 1 ，为LinearLayoutManager，其他为 GridLayoutManager
     */
    protected int setGridNumb() {
        return 0;
    }

    /**
     * 重写这个 设置间隔线
     */
    protected void setItemDecoration() {
        RecyclerView.ItemDecoration itemDecoration = new BaseDividerItem(1, setItemColor());
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 重新这个 可以改变 间隔线的颜色
     */
    protected int setItemColor() {
        return Color.parseColor("#DCDCDC");
    }

    protected abstract RecyclerView.Adapter setAdapter();

    /**
     * 添加 HeadView  (也可以直接在 baseAdapter里加)
     *
     * @param view
     */
    protected void addHeadView(View view) {
        mRecyclerView.addHeaderView(view);
    }

    /**
     * 可以刷新
     *
     * @param canRefresh
     */
    public void setCanRefresh(boolean canRefresh) {
        mRecyclerView.setPullRefreshEnabled(canRefresh);
    }

    /**
     * 可以加载
     *
     * @param canLoading
     */
    public void setCanLoading(boolean canLoading) {
        mRecyclerView.setLoadingMoreEnabled(canLoading);
    }

    /**
     * 刷新完成
     */
    public void setRefreshComplete() {
        mRecyclerView.refreshComplete();
    }

    /**
     * 加载完成
     */
    public void setLoadMoreComplete() {
        mRecyclerView.loadMoreComplete();
    }

    /**
     * 开始刷新
     */
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.refresh();
            }
        }, 100);
    }
}
