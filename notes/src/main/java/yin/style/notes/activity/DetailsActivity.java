package yin.style.notes.activity;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jskingen.baselib.activity.base.RecyclerViewActivity;

import java.util.ArrayList;
import java.util.List;

import yin.style.notes.R;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;
import yin.style.recyclerlib.inter.OnItemClickLongListener;

public class DetailsActivity extends RecyclerViewActivity {

    private DetailAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle() {
        title.setText("项目明细");
    }

    @Override
    protected void setItemDecoration() {
//        super.setItemDecoration();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        adapter = new DetailAdapter(R.layout.item_details, list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(mContext, AddDetailsActivity.class));
            }
        });
        adapter.setOnItemClickLongListener(new OnItemClickLongListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                startActivity(new Intent(mContext, AddDetailsActivity.class));
            }
        });
        return adapter;
    }

    class DetailAdapter extends BaseQuickAdapter<String> {
        public DetailAdapter(@LayoutRes int layoutResId, List mData) {
            super(layoutResId, mData);
        }

        @Override
        protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {

        }
    }
}
