package com.bangdu.classnotice.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangdu.classnotice.R;
import com.jskingen.baselib.fragment.NormalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

/**
 * Created by BangDu on 2017/11/7.
 */

public class ActionFragment extends NormalFragment {
    @BindView(R.id.iv_f4_image)
    ImageView ivF4Image;
    @BindView(R.id.tv_f4_title)
    TextView tvF4Title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    MyAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_action;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        adapter = new MyAdapter(R.layout.item_action, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        adapter.notifyChanged();
    }

    class MyAdapter extends BaseQuickAdapter<String> {

        public MyAdapter(int layoutResId, List mData) {
            super(layoutResId, mData);
        }

        @Override
        protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
            baseViewHolder.setText(R.id.tv_item_1, (position + 1) + "》国庆节放假通知！");
        }
    }
}
