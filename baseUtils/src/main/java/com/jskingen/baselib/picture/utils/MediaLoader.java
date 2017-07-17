package com.jskingen.baselib.picture.utils;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.jskingen.baselib.R;
import com.jskingen.baselib.log.Logger;
import com.jskingen.baselib.picture.model.MediaFile;
import com.jskingen.baselib.picture.model.MediaFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChneY on 2017/5/7.
 */

public class MediaLoader {
    private int TypeId = 0;

    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID
    };


    private FragmentActivity activity;
    private boolean isGif;

    public MediaLoader(FragmentActivity activity, boolean isGif) {
        this.activity = activity;
        this.isGif = isGif;
    }

    public void loadAllImage(final LocalMediaLoadListener imageLoadListener) {
        activity.getSupportLoaderManager().initLoader(TypeId, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                String select[] = null;
                String condition = "";
                if (isGif) {
                    select = new String[]{"image/jpeg", "image/png", "image/gif"};
                    condition = MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?" + " or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?";
                } else {
                    select = new String[]{"image/jpeg", "image/png", "image/webp"};
                    condition = MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?" + " or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?";
                }

                CursorLoader cursorLoader = new CursorLoader(
                        activity,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        condition,
                        select,
                        IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                try {
                    ArrayList<MediaFolder> imageFolder = new ArrayList<>();
                    ArrayList<MediaFile> imageFile = new ArrayList<>();//最近照片

                    if (data != null) {
                        int count = data.getCount();
                        Logger.e(count + "");
                        data.moveToFirst();
                        do {
                            String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                            // 如原图路径不存在或者路径存在但文件不存在,就结束当前循环
                            if (TextUtils.isEmpty(path) || !new File(path).exists()) {
                                continue;
                            }
                            //保存图片
                            savaImageFile(data, imageFolder, imageFile);
                        } while (data.moveToNext());

                        //查询结束
                        MediaFolder localMediaFolder = new MediaFolder();
                        localMediaFolder.setName(activity.getString(R.string.pic_recent));
                        localMediaFolder.setImageFiles(imageFile);
                        imageFolder.add(0, localMediaFolder);

                        PictureManage.getInstance().setMediaFolders(imageFolder);
                        if (null != imageLoadListener)
                            imageLoadListener.loadComplete(imageFolder);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
    }

    //保存图片
    private void savaImageFile(Cursor data, ArrayList<MediaFolder> imageFolder, ArrayList<MediaFile> imageFiles) {
        MediaFile tempImageFile = new MediaFile();
        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

        String parentPath = new File(path).getParent();
        tempImageFile.setPath(path);
        tempImageFile.setParentPath(parentPath);
        tempImageFile.setSaveTime(dateTime);

        boolean isNewFolder = true;
        for (MediaFolder mediaFolder : imageFolder) {
            if (TextUtils.equals(mediaFolder.getPath(), parentPath)) {
                mediaFolder.getImageFiles().add(tempImageFile);
                isNewFolder = false;
                break;
            }
        }
        if (isNewFolder) {
            MediaFolder mediaFolder = new MediaFolder();
            mediaFolder.setPath(parentPath);
            mediaFolder.getImageFiles().add(tempImageFile);
            mediaFolder.setName(new File(parentPath).getName());
            imageFolder.add(mediaFolder);
        }

        //添加最近照片 最多99张
        if (imageFiles.size() < 99)
            imageFiles.add(tempImageFile);
    }

    public interface LocalMediaLoadListener {
        void loadComplete(List<MediaFolder> folders);
    }
}
