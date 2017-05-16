package com.jskingen.baseutils.photo;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.jskingen.baselib.imageload.GlideUtil;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baseutils.R;

import java.util.List;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

/**
 * Created by ChneY on 2017/5/13.
 */

public class PhotoAdapter extends BaseQuickAdapter<MediaFile> {
    public PhotoAdapter(@LayoutRes int layoutResId, List mData) {
        super(layoutResId, mData);
    }

    @Override
    protected void setViewHolder(BaseViewHolder baseViewHolder, MediaFile mediaFile, int position) {
        baseViewHolder.setText(R.id.tv_item_takephoto, position);
        GlideUtil.getInstance().setView((ImageView) baseViewHolder.getView(R.id.iv_item_takephoto), mediaFile.getPath());
    }
}
