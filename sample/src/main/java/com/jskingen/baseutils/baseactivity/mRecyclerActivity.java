package com.jskingen.baseutils.baseactivity;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
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
    protected void initData() {
        setCanRefresh(true);
        refresh();

        setListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        list.clear();
                        for (int i = 0; i < 100; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();
                        setRefreshComplete();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }
}
