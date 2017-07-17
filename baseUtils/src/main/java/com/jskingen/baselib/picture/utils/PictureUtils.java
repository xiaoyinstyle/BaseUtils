package com.jskingen.baselib.picture.utils;

import android.app.Activity;
import android.content.Intent;

import com.jskingen.baselib.log.Logger;
import com.jskingen.baselib.picture.activity.PictureAlbumActivity;
import com.jskingen.baselib.picture.activity.PictureSelectActivity;
import com.jskingen.baselib.picture.inter.OnSelectListener;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.model.MediaFolder;
import com.jskingen.baselib.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import static com.jskingen.baselib.picture.fragment.PictureAlbumFragment.PICTURE_LOADER_FINISH;

/**
 * Created by ChneY on 2017/5/8.
 */

public class PictureUtils {
    public static PictureUtils instance;

    public static PictureUtils getInstance() {
        if (instance == null) {
            instance = new PictureUtils();
        }
        return instance;
    }

    /**
     * 打开相册 单选
     *
     * @param context
     * @param isCrop
     * @param listener
     */
    public void openAlbum(Activity context, boolean showCamera,boolean isCrop, OnSelectListener listener) {
        PictureManage.getInstance().create();
        PictureManage.getInstance().setCrop(isCrop);
        PictureManage.getInstance().setListener(listener);
        PictureManage.getInstance().setShowCamera(showCamera);
        PictureManage.getInstance().setTakePhoto(false);
        PictureManage.getInstance().setSelectOne(true);

        Intent intent = new Intent(context, PictureAlbumActivity.class);
        context.startActivity(intent);
    }

    /**
     * 拍照
     *
     * @param context
     * @param isCrop
     * @param listener
     */
    public void openCamera(Activity context, boolean isCrop, OnSelectListener listener) {
        PictureManage.getInstance().create();
        PictureManage.getInstance().setCrop(isCrop);
        PictureManage.getInstance().setListener(listener);
        PictureManage.getInstance().setTakePhoto(true);
        PictureManage.getInstance().setSelectOne(true);

        Intent intent = new Intent(context, PictureSelectActivity.class);
        context.startActivity(intent);
    }

    /**
     * 打开相册 多选
     *
     * @param context
     * @param numb
     * @param listener
     */
    public void openAlbum(Activity context, int numb, boolean showCamera, OnSelectListener listener) {
        PictureManage.getInstance().create();
        PictureManage.getInstance().setCrop(false);
        PictureManage.getInstance().setListener(listener);
        PictureManage.getInstance().setSelectNumb(numb);
        PictureManage.getInstance().setShowCamera(showCamera);
        PictureManage.getInstance().setSelectOne(false);

        Intent intent = new Intent(context, PictureAlbumActivity.class);
        context.startActivity(intent);
    }

}
