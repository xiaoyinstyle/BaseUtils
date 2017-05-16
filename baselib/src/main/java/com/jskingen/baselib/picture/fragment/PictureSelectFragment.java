package com.jskingen.baselib.picture.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jskingen.baselib.R;
import com.jskingen.baselib.fragment.RecyclerViewFragment;
import com.jskingen.baselib.picture.activity.PicturePreviewActivity;
import com.jskingen.baselib.picture.inter.PictureSelectListener;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.utils.AnimationUtils;
import com.jskingen.baselib.picture.utils.ImageUtils;
import com.jskingen.baselib.picture.utils.PictureManage;
import com.jskingen.baselib.picture.utils.TakePhoto;
import com.jskingen.baselib.utils.FileUtils;
import com.jskingen.baselib.utils.RxBus;
import com.jskingen.baselib.utils.ToastUtils;

import java.io.File;
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
    private static final int REQUEST_TAKEPHOTE = 44;//拍照
    private static final int REQUEST_CROP = 66;//裁剪
    private BaseQuickAdapter adapter;
    private List<MediaFile> list = new ArrayList<>();

    private PictureSelectListener listener;
    private boolean showCamera = (PictureManage.getInstance().getShowCamera() && PictureManage.getInstance().position == 0) ? true : false;
    private File tempFile;

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new BaseQuickAdapter<MediaFile>(R.layout.base_item_picture_select, list) {

            @Override
            protected void setViewHolder(final BaseViewHolder baseViewHolder, MediaFile imageFile, final int position) {
                Object path = imageFile.getPath();
                if (showCamera && position == 0) {
                    path = R.drawable.ic_launcher;
                }

                DiskCacheStrategy result;
                if (false) {
                    result = DiskCacheStrategy.SOURCE;
                } else {
                    result = DiskCacheStrategy.RESULT;
                }
                Glide.with(getContext())
                        .load(path)
                        .placeholder(R.drawable.pic_ic_placeholder)
                        .diskCacheStrategy(result)
//                        .crossFade()
                        .centerCrop()
                        .override(150, 150)
                        .into((ImageView) baseViewHolder.getView(R.id.pic_iv_item));


                baseViewHolder.setVisible(R.id.pic_view_item, imageFile.isChecked());

                baseViewHolder.setOnCheckedChangeListener(R.id.pic_cb_item, null);

                if (PictureManage.getInstance().isSelectOne() || showCamera && position == 0) {
                    baseViewHolder.setVisible(R.id.pic_cb_item, false);
                } else {
                    baseViewHolder.setTag(R.id.pic_cb_item, position);
                    baseViewHolder.setChecked(R.id.pic_cb_item, imageFile.isChecked());
                    baseViewHolder.setTag(R.id.pic_cb_item, position);
                    baseViewHolder.setOnCheckedChangeListener(R.id.pic_cb_item, new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            //不可以添加 的情况
                            if (!PictureManage.getInstance().canAdd()) {
                                buttonView.setChecked(false);
                                return;
                            }
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
            }
        };

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (showCamera && position == 0) {
                    tempFile = FileUtils.getSystemImageFileTemp();
                    TakePhoto.takePhoto(getActivity(), tempFile, REQUEST_TAKEPHOTE);
                } else if (PictureManage.getInstance().isSelectOne()) {
                    if (PictureManage.getInstance().isCrop())
                        TakePhoto.cropPicture(getActivity(), new File(list.get(position).getPath()), REQUEST_CROP);
                    else {
                        PictureManage.getInstance().addFile(list.get(position));
                        RxBus.getInstance().post(PICTURE_TAG_COMPLETE);
                        getActivity().finish();
                    }
                } else {
                    PicturePreviewActivity.start(getContext(), showCamera ? position - 1 : position);
                }
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

                    if (showCamera)
                        list.add(0, new MediaFile());
                    adapter.notifyDataSetChanged();
                } else if (integer == PICTURE_TAG_COMPLETE) {
                    getActivity().finish();
                }
            }
        });

        RxBus.getInstance().addSubscription(this, subscription1);
    }

    @Override
    public void initData() {
        if (PictureManage.getInstance().isTakePhoto()) {
            tempFile = FileUtils.getSystemImageFileTemp();
            TakePhoto.takePhoto(getActivity(), tempFile, REQUEST_TAKEPHOTE);
            return;
        }
        int position = PictureManage.getInstance().position;

        if (null == PictureManage.getInstance().getMediaFolders() || PictureManage.getInstance().getMediaFolders().size() == 0)
            return;

        list.clear();
        if (showCamera)
            list.add(0, new MediaFile());

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

    public void activityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKEPHOTE) {
            if (resultCode == getActivity().RESULT_OK) {
//                ToastUtils.show("拍照");
                ImageUtils.addIamge2Album(getContext(), tempFile);
                if (PictureManage.getInstance().isTakePhoto() || PictureManage.getInstance().isSelectOne()) {
                    if (PictureManage.getInstance().isCrop())
                        TakePhoto.cropPicture(getActivity(), tempFile, REQUEST_CROP);
                    else {
                        PictureManage.getInstance().addCropFile(tempFile);
                        PictureManage.getInstance().setComplete();
                        getActivity().finish();
                    }
                }
            } else {
//                ToastUtils.show("取消拍照");
                if (PictureManage.getInstance().isTakePhoto()) {
                    setCancel();
                    getActivity().finish();
                }
            }
        } else if (requestCode == REQUEST_CROP) {
            if (data != null) {
//                ToastUtils.show("裁剪");
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap != null) {
                    TakePhoto.saveBitmap(bitmap, tempFile);
                    PictureManage.getInstance().addCropFile(tempFile);
                    getActivity().finish();
                }
            } else {
//                ToastUtils.show("取消裁剪");
                if (PictureManage.getInstance().isTakePhoto()) {
                    setCancel();
                    getActivity().finish();
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (PictureManage.getInstance().isTakePhoto())
            return;
        initData();
        if (null != listener)
            listener.onChange("", PictureManage.getInstance().getSelectList(), list);
    }

    public void setListener(PictureSelectListener listener) {
        this.listener = listener;
    }

    @Override
    protected int setItemColor() {
        return Color.TRANSPARENT;
    }

    @Override
    protected int setGridNumb() {
        return 4;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);
    }
}
