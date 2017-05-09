package yin.style.recyclerlib.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import yin.style.recyclerlib.decoration.BaseTimeLineDecoration;

/**
 * Created by chenY on 2017/1/20.
 */

public abstract class BaseTimeLineAdapter<T> extends BaseQuickAdapter<T> {
    protected BaseTimeLineDecoration mItemDecoration = null;

    public BaseTimeLineAdapter(RecyclerView recyclerView, @LayoutRes int layoutResId, List mData) {
        super(layoutResId, mData);

        mContext = recyclerView.getContext();
        mItemDecoration = new BaseTimeLineDecoration<T>(mData, getColor()) {
            @Override
            protected void setViewItem(View ChildView, int position) {

            }

            @Override
            protected int getToplineHeight(View firstChildView) {
                return BaseTimeLineAdapter.this.getToplineHeight(firstChildView);
            }

            @Override
            protected int getCircleColor(int position) {
                return BaseTimeLineAdapter.this.getCircleColor(position);
            }
        };

        View headerView = setHeaderView();
        addHeaderView(headerView);
        mItemDecoration.setHasHeader(headerView == null ? false : true);

        View footerView = setFooterView();
        addFooterView(footerView);
        mItemDecoration.setFooterCount(footerView == null ? false : true);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(mItemDecoration);
    }

    protected abstract int getColor();

    protected abstract View setFooterView();

    protected abstract View setHeaderView();

    protected abstract int getCircleColor(int position);

    protected abstract int getToplineHeight(View firstChildView);


}