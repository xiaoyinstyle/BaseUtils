package yin.style.notes.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jskingen.baselib.fragment.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

import yin.style.notes.R;
import yin.style.notes.activity.AddMainActivity;
import yin.style.notes.activity.DetailsActivity;
import yin.style.notes.view.LabelTextView;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;
import yin.style.recyclerlib.inter.OnItemClickLongListener;

/**
 * Created by Chne on 2017/8/12.
 */

public class ProjectsFragment extends RecyclerViewFragment {
    private ProjectsAdapter adapter;
    private List<String> list = new ArrayList<>();


    @Override
    protected void setTitle() {
        title.setText("Notes");
        hiddenBackButton();
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("新增");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AddMainActivity.class));
            }
        });
    }

    @Override
    protected void setItemDecoration() {
//        super.setItemDecoration();
    }

    @Override
    protected void initData() {

    }


    @Override
    protected RecyclerView.Adapter setAdapter() {
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        adapter = new ProjectsAdapter(R.layout.item_main, list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(mContext, DetailsActivity.class));
            }
        });
        adapter.setOnItemClickLongListener(new OnItemClickLongListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Intent intent = new Intent(mContext, AddMainActivity.class);
                intent.putExtra("mainId", 1);
                startActivity(intent);
            }
        });
        return adapter;
    }

    int labelColor[] = {Color.parseColor("#01BF9D"), Color.parseColor("#EA4B35"), Color.parseColor("#95A5A5")};

    class ProjectsAdapter extends BaseQuickAdapter<String> {

        public ProjectsAdapter(@LayoutRes int layoutResId, List mData) {
            super(layoutResId, mData);
        }

        @Override
        protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
            LabelTextView labelTextView = baseViewHolder.getView(R.id.ltv_item_main_statue);
            if (position % 3 == 0) {
                labelTextView.setLabelBackgroundColor(labelColor[0]);
                labelTextView.setLabelText("预定");
            } else if (position % 3 == 1) {
                labelTextView.setLabelBackgroundColor(labelColor[1]);
                labelTextView.setLabelText("进行中");
            } else {
                labelTextView.setLabelBackgroundColor(labelColor[2]);
                labelTextView.setLabelText("结束");
            }
        }
    }
}
