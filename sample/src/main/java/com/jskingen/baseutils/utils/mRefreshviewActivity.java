package com.jskingen.baseutils.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baselib.view.refreshView.RefreshLayout;
import com.jskingen.baseutils.R;
import com.jskingen.baseutils.utils.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

public class mRefreshviewActivity extends AppCompatActivity {
    RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_refreshview);

        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();
                        ToastUtils.show("6666");
                    }
                }, 3000);
            }
        });
        HeaderView headerView = new HeaderView(this);
        refreshLayout.setRefreshHeader(headerView);

//        addListView();
        addRecyclerView();
    }

    private void addRecyclerView() {
        RecyclerView recyclerView = new RecyclerView(this);
        refreshLayout.addView(recyclerView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("No." + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<String>(android.R.layout.simple_list_item_1, list) {
            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
                baseViewHolder.setText(android.R.id.text1, s);
            }
        });
    }


    private void addListView() {
        ListView listView = new ListView(this);
        refreshLayout.addView(listView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("No." + i);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
