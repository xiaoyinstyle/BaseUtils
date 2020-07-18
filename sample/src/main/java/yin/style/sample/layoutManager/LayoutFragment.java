package yin.style.sample.layoutManager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import yin.style.baselib.fragment.NormalFragment;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.sample.R;
import yin.style.sample.layoutManager.layout.LooperLayoutManager;
import yin.style.sample.layoutManager.layout.StackLayout2Manager;
import yin.style.sample.layoutManager.layout.StackLayoutManager;

public class LayoutFragment extends NormalFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    LayoutAdapter adapter;
    List<String> list = new ArrayList<>();
    int flag;


    @Override
    protected int getViewByXml() {
        return R.layout.fragment_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        adapter = new LayoutAdapter(mContext, list);
        recyclerView.setAdapter(adapter);

        if (flag == 0) {
            recyclerView.setLayoutManager(new StackLayout2Manager(mContext));
        } else if (flag == 1) {
            recyclerView.setLayoutManager(new StackLayoutManager(mContext));
        } else {
            recyclerView.setLayoutManager(new LooperLayoutManager(mContext));
        }

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 50; i++) {
            list.add("");
        }

        adapter.notifyDataSetChanged();
    }

    public LayoutFragment setFlag(int flag) {
        this.flag = flag;
        return this;
    }


    class LayoutAdapter extends BaseQuickAdapter<String> {

        public LayoutAdapter(Context mContext, List mData) {
            super(mContext, mData);
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.item_layout_1;
        }

        @Override
        protected void setViewHolder(BaseViewHolder baseViewHolder, String bean, int position) {
            baseViewHolder.setText(R.id.tv_item_1, position + "");
        }
    }
}
