package yin.style.sample.baseActivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import yin.style.baselib.activity.base.RecyclerViewActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.ToastUtils;
import yin.style.recyclerlib.inter.OnExpandItemClickLongListener;
import yin.style.sample.baseActivity.adapter.Group;
import yin.style.sample.baseActivity.adapter.mExpandAdapter;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.inter.OnExpandItemClickListener;

public class mExpandViewActivity extends RecyclerViewActivity {
    private mExpandAdapter adapter;

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("ExpandView");

        titleLayout.setTextRight("添加", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Group> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    List<Group.GroupItem> l = new ArrayList<>();
                    for (int j = 0; j < 5; j++) {
                        Group.GroupItem item = new Group.GroupItem("测试" + j);
                        l.add(item);
                    }
                    Group g = new Group("抬头", l);
                    list.add(g);
                }

                adapter.setData(list, true);
            }
        });
    }

    @Override
    protected void initData() {
        List<Group> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Group.GroupItem> l = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Group.GroupItem item = new Group.GroupItem("测试" + j);
                l.add(item);
            }
            Group g = new Group("抬头", l);
            list.add(g);
        }

        adapter.setData(list, false);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new mExpandAdapter(this);
//        adapter.setOnItemClickListener(false, new OnExpandItemClickListener() {
//            @Override
//            public void onItemClick(View view, int groupPosition, int childPosition) {
//                ToastUtils.show(groupPosition + "__" + childPosition);
//            }
//        });
        adapter.setDefaultExpand(true);
        adapter.setNotClose();
        adapter.setOnlyOpenOne(true);
        adapter.setOnItemClickLongListener(new OnExpandItemClickLongListener() {
            @Override
            public boolean onItemLongClick(View view, int groupPosition, int childPosition) {

                ToastUtils.show("onItemLongClick:" + groupPosition + "__" + childPosition);
                return true;
            }
        });
        return adapter;
    }

}
