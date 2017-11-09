package com.bangdu.classnotice.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bangdu.classnotice.R;
import com.jskingen.baselib.fragment.NormalFragment;
import com.jskingen.baselib.imageload.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

/**
 * Created by BangDu on 2017/11/7.
 */

public class DutyFragment extends NormalFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    MyAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_duty;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        adapter = new MyAdapter(R.layout.item_duty, list);

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 6; i++) {
            list.add("" + i);
        }
        adapter.notifyChanged();
    }

    class MyAdapter extends BaseQuickAdapter<String> {

        public MyAdapter(int layoutResId, List mData) {
            super(layoutResId, mData);
        }

        @Override
        protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
            baseViewHolder.setText(R.id.tv_item_1, "卫生" + ":\n" + "楚楚");

            GlideUtil.getInstance().setCircleView(((ImageView) baseViewHolder.getView(R.id.iv_item_1)), R.mipmap.ic_temp_avatar);
        }
    }
}
