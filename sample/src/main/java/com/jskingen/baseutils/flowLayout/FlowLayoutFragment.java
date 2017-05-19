package com.jskingen.baseutils.flowLayout;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jskingen.baseutils.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.flowlayoutmanager.FlowLayoutManager;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;

/**
 * Created by ChneY on 2017/5/19.
 */

public class FlowLayoutFragment extends Fragment {
    private RecyclerView rvcontent;
    private RecyclerView rvbase;
    private List<String> bastStr = Arrays.asList("接警接警接警", "出动出动", "到场", "侦", "布\n阵"
            , "接警接警接警\n接警接警接警", "出水", "控制", "救人", "排烟"
            , "控火", "供水", "结束", "撤离", "待命", "回单");
    private BaseQuickAdapter baseQuickAdapter;


    private TestAdapter adapter;
    private List<TestBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flowlayout, container, false);
        rvbase = (RecyclerView) view.findViewById(R.id.rv_base);
        rvcontent = (RecyclerView) view.findViewById(R.id.rv_content);

        FlowLayoutManager manager = new FlowLayoutManager();
        manager.setAutoMeasureEnabled(true);
        rvbase.setLayoutManager(manager);

        FlowLayoutManager manager2 = new FlowLayoutManager();
        manager2.setAutoMeasureEnabled(true);
        rvcontent.setLayoutManager(manager2);
        baseQuickAdapter = new BaseQuickAdapter<String>(R.layout.item_fragment_flowlayout, bastStr) {
            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
                baseViewHolder.setText(R.id.tv_item_test, s);
                baseViewHolder.setVisible(R.id.iv_item_arror, false);
                baseViewHolder.setVisible(R.id.tv_item_time, false);
            }
        };
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                String t = format.format(new Date(System.currentTimeMillis()));
                list.add(new TestBean(bastStr.get(position).toString(), t));
                adapter.notifyDataSetChanged();
            }
        });
        rvbase.setAdapter(baseQuickAdapter);

        adapter = new TestAdapter(R.layout.item_fragment_flowlayout, list);
        rvcontent.setAdapter(adapter);
        initBaseData();
        return view;
    }

    private void initBaseData() {

    }

    class TestBean {
        private String text;
        private String time;

        TestBean(String text, String time) {
            this.text = text;
            this.time = time;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    private class TestAdapter extends BaseQuickAdapter<TestBean> {

        public TestAdapter(@LayoutRes int layoutResId, List mData) {
            super(layoutResId, mData);
        }

        @Override
        protected void setViewHolder(BaseViewHolder baseViewHolder, TestBean testBean, int position) {
            baseViewHolder.setText(R.id.tv_item_test, testBean.getText());
            baseViewHolder.setText(R.id.tv_item_time, testBean.getTime());

            if (position == mData.size() - 1)
                baseViewHolder.getView(R.id.iv_item_arror).setVisibility(View.INVISIBLE);

        }
    }

}
