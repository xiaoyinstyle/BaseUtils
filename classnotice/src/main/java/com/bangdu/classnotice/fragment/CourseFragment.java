package com.bangdu.classnotice.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangdu.classnotice.R;
import com.jskingen.baselib.fragment.NormalFragment;
import com.jskingen.baselib.view.AutofitTextView;

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

public class CourseFragment extends NormalFragment {
    @BindView(R.id.rv_today)
    RecyclerView rvToday;
    @BindView(R.id.rv_tomorrow)
    RecyclerView rvTomorrow;

    MyAdapter todayAdapter;
    MyAdapter tomorrowAdapter;

    List<String> todayList = new ArrayList<>();
    List<String> tomorrowList = new ArrayList<>();

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        todayAdapter = new MyAdapter(R.layout.item_course, todayList);
        rvToday.setLayoutManager(new GridLayoutManager(mContext, 7));
        rvToday.setAdapter(todayAdapter);

        tomorrowAdapter = new MyAdapter(R.layout.item_course, tomorrowList);
        rvTomorrow.setLayoutManager(new GridLayoutManager(mContext, 7));
        rvTomorrow.setAdapter(tomorrowAdapter);

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 7; i++) {
            todayList.add("" + i);
            tomorrowList.add("" + i);
        }
        todayAdapter.notifyChanged();
        tomorrowAdapter.notifyChanged();
    }

    class MyAdapter extends BaseQuickAdapter<String> {

        public MyAdapter(int layoutResId, List mData) {
            super(layoutResId, mData);
        }

        @Override
        protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
            AutofitTextView autofitTextView = baseViewHolder.getView(R.id.tv_item_1);
            autofitTextView.invalidate();
        }
    }
}
