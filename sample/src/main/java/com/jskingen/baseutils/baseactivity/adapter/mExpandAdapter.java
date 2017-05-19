package com.jskingen.baseutils.baseactivity.adapter;

import android.content.Context;

import com.jskingen.baseutils.R;

import java.util.List;

import yin.style.recyclerlib.adapter.BaseExpandAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

/**
 * Created by ChneY on 2017/5/16.
 */

public class mExpandAdapter extends BaseExpandAdapter<Group> {

    public mExpandAdapter(Context context, List list) {
        super(context, list);
    }
    @Override
    protected List getChild(int position) {
        return list.get(position).getGroupItems();
    }

    @Override
    protected int getGroupLayout() {
        return R.layout.item_recyclerview;
    }

    @Override
    protected int getChildLayout() {
        return R.layout.item_recyclerview2;
    }

    @Override
    protected void setChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        holder.setText(R.id.text, "这是ChildView_" + groupPosition + "_" + childPosition);
    }

    @Override
    protected void setGroupViewHolder(BaseViewHolder holder, boolean isOpenGroup, int groupPosition) {
        holder.setText(R.id.text, "这是GroupView_" + groupPosition);
        holder.setBackgroundRes(R.id.text,R.color.grey);
    }
}
