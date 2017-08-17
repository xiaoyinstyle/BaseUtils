package yin.style.notes.adapter;

import android.support.annotation.LayoutRes;

import java.util.List;

import yin.style.notes.R;
import yin.style.notes.model.DetailsBean;
import yin.style.notes.utils.DateUtil;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

/**
 * Created by Chne on 2017/8/17.
 */

public class DetailAdapter extends BaseQuickAdapter<DetailsBean> {
    public DetailAdapter(@LayoutRes int layoutResId, List mData) {
        super(layoutResId, mData);
    }

    @Override
    protected void setViewHolder(BaseViewHolder baseViewHolder, DetailsBean bean, int position) {
        baseViewHolder.setText(R.id.tv_item_details_content, bean.getContent());
        baseViewHolder.setText(R.id.tv_item_details_money, bean.getMoney() + "");
        baseViewHolder.setText(R.id.tv_item_details_remarks, "备注：" + bean.getRemarks() + "");
        baseViewHolder.setText(R.id.tv_item_details_time, DateUtil.date2Str(bean.getTime(), "yyyy-MM-dd"));

        String type = DetailsBean.getFlagText(bean.getFlag());
        if (bean.getFlag() == DetailsBean.DETAILS_FLAG_WAGE)
            type = type + "(" + bean.getWorker() + ")";
        baseViewHolder.setText(R.id.tv_item_details_type, type);
    }
}

