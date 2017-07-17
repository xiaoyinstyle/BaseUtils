package com.jskingen.baselib.picture.utils;

import com.jskingen.baselib.log.Logger;
import com.jskingen.baselib.picture.inter.OnSelectListener;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.model.MediaFolder;
import com.jskingen.baselib.utils.RxBus;
import com.jskingen.baselib.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.jskingen.baselib.picture.fragment.PictureAlbumFragment.PICTURE_LOADER_FINISH;
import static com.jskingen.baselib.picture.fragment.PictureAlbumFragment.PICTURE_TAG_COMPLETE;

/**
 * Created by ChneY on 2017/5/8.
 */

public class PictureManage {
    private List<MediaFile> mediaFiles = new ArrayList<>();
    private List<MediaFolder> mediaFolders = new ArrayList<>();
    public int position = 0;
    private OnSelectListener listener;
    private boolean isCrop;
    private int selectNumb;
    private boolean selectOne;
    private boolean showCamera;

    public static PictureManage instance;
    private boolean takePhoto;

    public static PictureManage getInstance() {
        if (instance == null) {
            instance = new PictureManage();
        }
        return instance;
    }

    public void create() {
        if (mediaFolders == null)
            mediaFolders = new ArrayList<>();
        if (mediaFiles == null)
            mediaFiles = new ArrayList<>();
        mediaFolders.clear();
        mediaFiles.clear();
    }

    public void destroy() {
        mediaFiles.clear();
        mediaFiles = null;
        mediaFolders.clear();
        mediaFolders = null;
        instance = null;
    }

    /**
     * 添加 裁剪文件
     * @param file
     */
    public void addCropFile(File file) {
        if (file.exists()) {
            MediaFile mediaFile = new MediaFile();
            mediaFile.setPath(file.getPath());
            mediaFile.setChecked(true);
            mediaFile.setParentPath(file.getParent());
            mediaFiles.add(mediaFile);
            RxBus.getInstance().post(mediaFile);
        }
    }

    /**
     * 添加记录
     * @param file
     */
    public void addFile(MediaFile file) {
        if (!file.isChecked()) {
            file.setChecked(true);
            mediaFiles.add(file);
            RxBus.getInstance().post(file);
        }
    }

    /**
     * 移除记录
     * @param file
     */
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

    public void setListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public int getSelectNumb() {
        return selectNumb;
    }

    public void setSelectNumb(int selectNumb) {
        this.selectNumb = selectNumb;
    }

    public boolean isSelectOne() {
        return selectOne;
    }

    public void setSelectOne(boolean selectOne) {
        this.selectOne = selectOne;
    }

    /**
     * 完成 选择
     */
    public void setComplete() {
        if (null != listener)
            listener.onComplete(mediaFiles);
        destroy();
    }

    /**
     * 判断是否可以继续添加数据
     */
    public boolean canAdd() {
        if (mediaFiles.size() < selectNumb) {
            return true;
        }
        ToastUtils.show("最多选择" + selectNumb + "张");
        return false;
    }

    /**
     * 是否显示Camera选项
     */
    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public boolean getShowCamera() {
        return showCamera;
    }

    /**
     * 仅拍照
     */
    public boolean isTakePhoto() {
        return takePhoto;
    }

    public void setTakePhoto(boolean takePhoto) {
        this.takePhoto = takePhoto;
    }
}
