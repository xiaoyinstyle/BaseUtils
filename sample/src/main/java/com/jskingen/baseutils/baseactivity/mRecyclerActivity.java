package com.jskingen.baseutils.baseactivity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jskingen.baselib.activity.base.RecyclerViewActivity;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;

public class mRecyclerActivity extends RecyclerViewActivity {
    private List<String> list = new ArrayList<>();
    private BaseQuickAdapter adapter;

    @Override
    protected void setTitle() {
        title.setText("RecyclerActivity");

        tv_right.setText("切换");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView(null);
            }
        });
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new BaseQuickAdapter<String>(R.layout.item_recyclerview, list) {
            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
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

    @Override
    public void onRefresh() {
//        recyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                for (int i = 0; i < 5; i++) {
//                    list.add("" + i);
//                }
//                adapter.notifyDataSetChanged();
//                setRefreshing(false);
//            }
//        }, 1000);
    }

    @Override
    protected boolean setCanRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        return true;
    }

    protected void initType() {
        if (GRIDLAYOUT != type)
            setType(GRIDLAYOUT, 3);
        else
            setType(LINEARLAYOUT, 0);
    }

    protected int setItemWidth() {
        return 0;
    }

}
