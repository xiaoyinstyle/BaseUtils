package yin.style.sample.baseActivity.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;


import java.util.List;

import yin.style.baselib.imageload.GlideUtil;
import yin.style.recyclerlib.adapter.BaseExpandAdapter;
import yin.style.recyclerlib.adapter.BaseExpandProAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.sample.R;

/**
 * Created by ChneY on 2017/5/16.
 */

public class mExpandAdapter extends BaseExpandProAdapter<Group> {
    public mExpandAdapter(Context context, List list) {
        super(context, list);
    }

//    public mExpandAdapter(Context context) {
//        super(context);
////        setData(list, true);
//    }

//    @Override
//    protected int getLayoutType(int position) {
//        return LAYOUT_FLOW;
//    }

    @Override
    protected List getChild(int position) {
        return list.get(position).getGroupItems();
    }

    @Override
    protected int getGroupLayout() {
        return R.layout.item_recyclerview;
    }

    @Override
    protected int getChildLayout(int p) {
        return R.layout.item_recyclerview2;
    }

    @Override
    protected void setGroupViewHolder(BaseViewHolder holder, boolean isOpenGroup, int groupPosition) {
        holder.setText(R.id.text, "这是GroupView_" + groupPosition);
        holder.setBackgroundRes(R.id.text, R.color.text_grey);

    }

    @Override
    protected void setChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        Group.GroupItem groupItem = list.get(groupPosition).getGroupItems().get(childPosition);

        holder.setText(R.id.text, "这是ChildView_" + groupPosition + "_" + childPosition);
        holder.setText(R.id.text, groupItem.getTitle());

        ImageView imageView = holder.getView(R.id.image);
        GlideUtil.getInstance().setView(imageView, groupItem.getImage());

    }

    public void notifyDataSetChanged2() {
        notifyDataSetChanged();
        Log.e("AAA", "notifyDataSetChanged2: ");
    }
}
