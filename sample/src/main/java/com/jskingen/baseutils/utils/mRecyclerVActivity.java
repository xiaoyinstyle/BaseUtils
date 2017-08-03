package com.jskingen.baseutils.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.MyLayoutManager;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;

public class mRecyclerVActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BaseQuickAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_recycler_v);

        for (int i = 0; i < 100; i++) {
            list.add("No." + i);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new MyLayoutManager(MyLayoutManager.TOP, 3));
        adapter = new BaseQuickAdapter<String>(R.layout.item_m_rec, list) {
            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
                baseViewHolder.setText(R.id.tv_, "" + s);
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.show(list.get(position));
            }
        });
    }
}
