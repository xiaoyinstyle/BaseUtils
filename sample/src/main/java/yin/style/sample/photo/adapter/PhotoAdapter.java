package yin.style.sample.photo.adapter;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

import yin.style.baselib.imageload.GlideUtil;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.sample.R;

/**
 * Created by BangDu on 2017/12/13.
 */

public class PhotoAdapter extends BaseQuickAdapter<Object> {
    public PhotoAdapter(Context mContext, List mData) {
        super(mContext, mData);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_photo;
    }

    @Override
    protected void setViewHolder(BaseViewHolder baseViewHolder, Object o, int position) {
        ImageView imageView = baseViewHolder.getView(R.id.imageView);
        GlideUtil.getInstance().setView(imageView, o);
    }
}
