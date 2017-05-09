package com.jskingen.baselib.picture.utils;

import android.app.Activity;
import android.content.Intent;

import com.jskingen.baselib.log.Logger;
import com.jskingen.baselib.picture.activity.PictureAlbumActivity;
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

public class PictureManage {
    private List<MediaFile> mediaFiles = new ArrayList<>();
    private List<MediaFolder> mediaFolders = new ArrayList<>();
    public int position = 0;
    private OnSelectListener listener;
    private boolean isCrop;

    public static PictureManage instance;

    public static PictureManage getInstance() {
        if (instance == null) {
            instance = new PictureManage();
        }
        return instance;
    }


    public void openAlbum(Activity context, OnSelectListener listener) {
        create();
        this.listener = listener;

        Intent intent = new Intent(context, PictureAlbumActivity.class);
        context.startActivity(intent);
    }

    private void create() {
        if (mediaFolders == null)
            mediaFolders = new ArrayList<>();
        if (mediaFiles == null)
            mediaFiles = new ArrayList<>();
        mediaFolders.clear();
        mediaFiles.clear();
    }

    public void clear() {
        mediaFiles.clear();
    }

    public void addFile(MediaFile file) {
        if (!file.isChecked()) {
            file.setChecked(true);
            mediaFiles.add(file);
            RxBus.getInstance().post(file);
        }
        Logger.e("" + mediaFiles.size());
    }

    public void removeFile(MediaFile file) {
        if (file.isChecked()) {
            file.setChecked(false);
            mediaFiles.remove(file);
            RxBus.getInstance().post(file);
        }
        Logger.e("" + mediaFiles.size());
    }


    public boolean isCrop() {
        return isCrop;
    }

    public void setCrop(boolean crop) {
        isCrop = crop;
    }

    public List<MediaFile> getSelectList() {
        return mediaFiles;
    }

    public void setSelectList(List<MediaFile> selectList) {
        this.mediaFiles = selectList;
    }

    public List<MediaFolder> getMediaFolders() {
        return mediaFolders;
    }

    public void setMediaFolders(List<MediaFolder> mediaFolders) {
        this.mediaFolders = mediaFolders;
        RxBus.getInstance().post(PICTURE_LOADER_FINISH);
    }

    public void setListener() {
        if (null != listener)
            listener.onComplete(mediaFiles);
    }
}
