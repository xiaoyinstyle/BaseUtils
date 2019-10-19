package yin.style.sample.baseActivity;

import android.os.Bundle;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import yin.style.baselib.activity.base.RecyclerViewActivity;

import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.ToastUtils;
import yin.style.recyclerlib.inter.OnItemTouchListener;
import yin.style.sample.R;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

public class mRecyclerActivity extends RecyclerViewActivity {
    private List<String> list = new ArrayList<>();
    private BaseQuickAdapter adapter;

    private boolean liner = true;//是否线性布局

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("RecyclerActivity");
        title.setTextRight("切换", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liner = !liner;

                setLayoutManager();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.addOnItemTouchListener(new OnItemTouchListener(this) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                ToastUtils.show("onItemClick__" + viewHolder.getLayoutPosition());
//                startActivity(new Intent(mContext, mTabActivity.class));
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                ToastUtils.show("onItemLongClick");
            }
        });
    }

    @Override
    protected void initData() {
        setCanRefresh(true);
        setCanLoading(true);
//        refresh();

        setListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        list.clear();
                        for (int i = 0; i < 20; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();
                        mRecyclerView.reset();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            list.add("" + i);
                        }
                        adapter.notifyDataSetChanged();
                        mRecyclerView.reset();
                        mRecyclerView.setNoMore(list.size() > 50);
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new BaseQuickAdapter<String>(mContext, list) {
            @Override
            protected int getLayoutResId() {
                return R.layout.item_recyclerview;
            }

            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, String o, int position) {
                baseViewHolder.setText(R.id.text, "测试—\n—" + position);
            }
        };
        return adapter;
    }

    @Override
    protected int setGridNumb() {
        if (liner)
            return 0;
        else
            return 4;
    }

}
