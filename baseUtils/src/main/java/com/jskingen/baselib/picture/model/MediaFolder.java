package com.jskingen.baselib.picture.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChneY on 2017/5/7.
 */

public class MediaFolder implements Serializable {
    private String name;//文件夹名
    private String path;//文件夹路径
    private int selectNum;//选择的张数
    private List<MediaFile> imageFiles = new ArrayList<>();//图片信息

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
    }

    public List<MediaFile> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<MediaFile> imageFiles) {
        this.imageFiles = imageFiles;
    }
}
