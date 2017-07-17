package com.jskingen.baselib.picture.inter;

import com.jskingen.baselib.picture.model.MediaFile;

import java.util.List;

/**
 * Created by ChneY on 2017/5/8.
 */

public interface PictureSelectListener {
    void onChange(String fileName, List<MediaFile> selectList, List<MediaFile> allList);
}
