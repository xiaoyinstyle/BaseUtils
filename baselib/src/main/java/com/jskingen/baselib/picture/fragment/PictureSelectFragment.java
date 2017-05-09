package com.jskingen.baselib.picture.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jskingen.baselib.R;
import com.jskingen.baselib.fragment.RecyclerViewFragment;
import com.jskingen.baselib.log.Logger;
import com.jskingen.baselib.picture.activity.PicturePreviewActivity;
import com.jskingen.baselib.picture.inter.PictureSelectListener;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.utils.AnimationUtils;
import com.jskingen.baselib.picture.utils.PictureManage;
import com.jskingen.baselib.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;

import static com.jskingen.baselib.picture.fragment.PictureAlbumFragment.PICTURE_LOADER_FINISH;
import static com.jskingen.baselib.picture.fragment.PictureAlbumFragment.PICTURE_TAG_COMPLETE;
import static com.jskingen.baselib.picture.fragment.PictureAlbumFragment.PICTURE_TAG_CENCEL;

/**
 * Created by ChneY on 2017/5/6.
 */

public class PictureSelectFragment extends RecyclerViewFragment {
    private BaseQuickAdapter adapter;
    private List<MediaFile> list = new ArrayList<>();

    private PictureSelectListener listener;

    @Override
    protected boolean setCanRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        return false;
    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new BaseQuickAdapter<MediaFile>(R.layout.base_item_picture_select, list) {

            @Override
            protected void setViewHolder(final BaseViewHolder baseViewHolder, MediaFile imageFile, final int position) {
                DiskCacheStrategy result;
                if (false) {
                    result = DiskCacheStrategy.SOURCE;
                } else {
                    result = DiskCacheStrategy.RESULT;
                }
                Glide.with(getContext())
                        .load(imageFile.getPath())
                        .placeholder(R.drawable.pic_ic_placeholder)
                        .diskCacheStrategy(result)
//                        .crossFade()
                        .centerCrop()
                        .override(150, 150)
                        .into((ImageView) baseViewHolder.getView(R.id.pic_iv_item));

                baseViewHolder.setVisible(R.id.pic_view_item, imageFile.isChecked());

                baseViewHolder.setOnCheckedChangeListener(R.id.pic_cb_item, null);

                baseViewHolder.setTag(R.id.pic_cb_item, position);
                baseViewHolder.setChecked(R.id.pic_cb_item, imageFile.isChecked());
                baseViewHolder.setTag(R.id.pic_cb_item, position);
                baseViewHolder.setOnCheckedChangeListener(R.id.pic_cb_item, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        baseViewHolder.setVisible(R.id.pic_view_item, isChecked);

                        if (isChecked) {
                            AnimationUtils.start(buttonView);
                            PictureManage.getInstance().addFile(list.get(position));
                        } else
                            PictureManage.getInstance().removeFile(list.get(position));

                        if (null != listener)
                            listener.onChange("", PictureManage.getInstance().getSelectList(), list);
                    }
                });
            }
        };

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PicturePreviewActivity.start(getContext(), position);
            }
        });
        return adapter;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        Subscription subscription1 = RxBus.getInstance().doSubscribe(Integer.class, new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                if (integer == PICTURE_LOADER_FINISH) {
                    list.addAll(PictureManage.getInstance().getMediaFolders().get(0).getImageFiles());
                    adapter.notifyDataSetChanged();
                } else if (integer == PICTURE_TAG_COMPLETE) {
                    getActivity().finish();
                }
            }
        });

        RxBus.getInstance().addSubscription(this, subscription1);
    }

    @Override
    public void onRefresh() {
        int position = PictureManage.getInstance().position;

        if (null == PictureManage.getInstance().getMediaFolders() || PictureManage.getInstance().getMediaFolders().size() == 0)
            return;

        list.clear();
        list.addAll(PictureManage.getInstance().getMediaFolders().get(position).getImageFiles());
        adapter.notifyDataSetChanged();
        listener.onChange(PictureManage.getInstance().getMediaFolders().get(position).getName(), new ArrayList<MediaFile>(), list);
    }

    /**
     * 取消选择
     */
    public void setCancel() {
        RxBus.getInstance().post(PICTURE_TAG_CENCEL);
        getActivity().finish();
    }

    /**
     * 完成选择
     */
    public void setComplete() {
        RxBus.getInstance().post(PICTURE_TAG_COMPLETE);
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
        if (null != listener)
            listener.onChange("", PictureManage.getInstance().getSelectList(), list);
    }

    public void setListener(PictureSelectListener listener) {
        this.listener = listener;
    }

    @Override
    protected void setType(int type, int spanCount) {
        super.setType(GRIDLAYOUT, 4);
    }

    @Override
    protected int setItemColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);
    }
}
