package com.jskingen.baseutils.baseactivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jskingen.baselib.activity.base.RecyclerViewActivity;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.baseactivity.adapter.Group;
import com.jskingen.baseutils.baseactivity.adapter.mExpandAdapter;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.inter.OnExplandItemClickListener;

public class mExpandViewActivity extends RecyclerViewActivity {
    private List<Group> list = new ArrayList<>();
    private mExpandAdapter adapter;

    @Override
    protected void setTitle() {
        title.setText("ExpandView");
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 5; i++) {
            List<Group.GroupItem> l = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Group.GroupItem item = new Group.GroupItem("测试");
                l.add(item);
            }
            Group g = new Group("抬头", l);
            list.add(g);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new mExpandAdapter(this, list);
        adapter.setOnItemClickListener(true, new OnExplandItemClickListener() {
            @Override
            public void onItemClick(View view, int groupPosition, int childPosition) {
                ToastUtils.show(groupPosition + "__" + childPosition);
            }
        });
        return adapter;
    }
}
