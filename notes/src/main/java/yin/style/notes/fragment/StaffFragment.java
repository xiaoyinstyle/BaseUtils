package yin.style.notes.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jskingen.baselib.fragment.RecyclerViewFragment;
import com.jskingen.baselib.fragment.TitleFragment;

import java.util.ArrayList;
import java.util.List;

import yin.style.notes.R;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

/**
 * Created by Chne on 2017/8/12.
 * 员工 工资列表
 */

public class StaffFragment extends RecyclerViewFragment {

    List<String > list =new ArrayList<>();
    @Override
    protected void setTitle() {
        title.setText("设置");
        hiddenBackButton();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        return new BaseQuickAdapter<String>(R.layout.item_details,list) {
            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {

            }
        };
    }

    @Override
    protected void initData() {

    }


}
