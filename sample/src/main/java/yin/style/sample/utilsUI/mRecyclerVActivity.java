package yin.style.sample.utilsUI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import yin.style.baselib.utils.ToastUtils;
import yin.style.sample.R;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.MyLayoutManager;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;
import yin.style.recyclerlib.inter.OnItemClickLongListener;

public class mRecyclerVActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BaseQuickAdapter adapter;
    List<String> list = new ArrayList<>();

    MyLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_recycler_v);

        for (int i = 0; i < 10; i++) {
            list.add("No." + i);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new MyLayoutManager(recyclerView, 4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BaseQuickAdapter<String>(this, list) {
            @Override
            protected int getLayoutResId() {
                return R.layout.item_m_rec;
            }

            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
                baseViewHolder.setText(R.id.tv_, "" + s);
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.show(list.get(position));
                layoutManager.setBigItem(position);


            }
        });
        adapter.setOnItemClickLongListener(new OnItemClickLongListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                ToastUtils.show("***" + list.get(position));
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.add("777");
                adapter.notifyDataSetChanged();
            }
        }, 10000);
    }
}
