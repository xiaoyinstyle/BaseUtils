package com.jskingen.baselib.picture.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jskingen.baselib.R;
import com.jskingen.baselib.fragment.RecyclerViewFragment;
import com.jskingen.baselib.picture.utils.MediaLoader;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.model.MediaFolder;
import com.jskingen.baselib.picture.utils.PictureManage;
import com.jskingen.baselib.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;
import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;
import yin.style.recyclerlib.inter.OnItemClickListener;

/**
 * Created by ChneY on 2017/5/6.
 */

public class PictureAlbumFragment extends RecyclerViewFragment {
    public static final int PICTURE_LOADER_FINISH = 77;
    public static final int PICTURE_TAG_CENCEL = 88;
    public static final int PICTURE_TAG_COMPLETE = 99;

    private Class jumpActivity;

    private BaseQuickAdapter adapter;
    private List<MediaFolder> list = new ArrayList<>();

    private int selectNumb;


    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new BaseQuickAdapter<MediaFolder>(R.layout.base_item_picture_list, list) {
            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, MediaFolder imageFolder, int position) {
                List<MediaFile> imageFiles = imageFolder.getImageFiles();

                baseViewHolder.setText(R.id.pic_image_num, "(" + imageFiles.size() + ")");
                baseViewHolder.setText(R.id.pic_tv_img_num, "" + imageFolder.getSelectNum());
                baseViewHolder.setText(R.id.pic_tv_folder_name, "" + imageFolder.getName());

                int selectNum = imageFolder.getSelectNum();
                if (selectNum == 0)
                    baseViewHolder.setVisible(R.id.pic_tv_img_num, false);
                else
                    baseViewHolder.setVisible(R.id.pic_tv_img_num, true);

                Glide.with(getContext())
                        .load(imageFiles.get(0).getPath())
                        .placeholder(R.drawable.pic_default_place)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .crossFade()
                        .override(180, 180)
                        .into((ImageView) baseViewHolder.getView(R.id.pic_first_image));
            }
        };

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PictureManage.getInstance().position = position;
                if (null != jumpActivity)
                    startActivity(new Intent(getActivity(), jumpActivity));
            }
        });
        return adapter;
    }

    @Override
    protected void initData() {
        if (null == jumpActivity)
            throw new NullPointerException("JumpActivity must be set");
        PictureManage.getInstance().position = 0;
        startActivity(new Intent(getActivity(), jumpActivity));
        new MediaLoader(getActivity(), false).loadAllImage(null);

        Subscription subscription = RxBus.getInstance().doSubscribe(MediaFile.class, new Action1<MediaFile>() {
            @Override
            public void call(MediaFile imageFile) {
                //单选
                if (PictureManage.getInstance().isSelectOne()||PictureManage.getInstance().isTakePhoto()) {
                    RxBus.getInstance().post(PICTURE_TAG_COMPLETE);
                } else {
                    //多选
                    for (int i = 0; i < list.size(); i++) {
                        if (i == 0 || TextUtils.equals(imageFile.getParentPath(), list.get(i).getPath())) {
                            for (MediaFile file : list.get(i).getImageFiles()) {
                                if (TextUtils.equals(file.getPath(), imageFile.getPath())) {
                                    file.setChecked(imageFile.isChecked());
                                    if (imageFile.isChecked()) {
                                        list.get(i).setSelectNum(list.get(i).getSelectNum() + 1);
                                    } else {
                                        list.get(i).setSelectNum(list.get(i).getSelectNum() - 1);
                                    }
                                }
                            }

                            if (i != 0)
                                break;
                        }
                    }
                }
            }
        });

        Subscription subscription1 = RxBus.getInstance().doSubscribe(Integer.class, new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                if (integer == PICTURE_TAG_COMPLETE) {
                    PictureManage.getInstance().setComplete();
                    getActivity().finish();
                } else if (integer == PICTURE_TAG_CENCEL) {
                    getActivity().finish();
                } else if (integer == PICTURE_LOADER_FINISH) {
                    list.addAll(PictureManage.getInstance().getMediaFolders());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        RxBus.getInstance().addSubscription(this, subscription);
        RxBus.getInstance().addSubscription(this, subscription1);

        selectNumb = PictureManage.getInstance().getSelectNumb();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (null != adapter)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);
    }

    /**
     * 设置 跳转的Activity 的 选择图片
     *
     * @param selectActivity
     */
    public void setSelectActivity(Class selectActivity) {
        this.jumpActivity = selectActivity;
    }

}
