package com.jskingen.baseutils.baseactivity;

import android.view.View;

import com.jskingen.baselib.activity.base.LoadRefreshActivity;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;

public class mLoadRefreshActivity extends LoadRefreshActivity {
    private List<String> list = new ArrayList<>();
    private BaseQuickAdapter adapter;

    @Override
    protected void setTitle() {
        title.setText("上拉刷新下拉加载");
    }

    @Override
    public void onRefresh() {
        loadSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 0; i < 5; i++) {
                    list.add("" + i);
                }
                adapter.notifyDataSetChanged();
                setRefreshing(false);
            }
        }, 1500);
    }

    @Override
    public void onLoad() {
        loadSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 5; i++) {
                    list.add("" + i);
                }
                adapter.notifyDataSetChanged();
                setLoading(false);

                //超过50个将不再加载更多
                if (list.size() > 39)
                    setCanLoadMore(false);
            }
        }, 1500);
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        adapter = new BaseQuickAdapter(R.layout.item_recyclerview, list) {
            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, Object o, int position) {
                baseViewHolder.setText(R.id.text, "测试——" + position);
            }
        };

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.show(position + "");
            }
        });
        return adapter;
    }

    //GridView的Nub 重写
    @Override
    protected int setGridNub() {
        return 2;
    }
}
