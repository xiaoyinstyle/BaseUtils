package com.jskingen.baselib.picture.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChneY on 2017/5/7.
 */

public class MediaFile implements Serializable {

    private String path;//路径
    private long saveTime;//保存时间 时间戳 秒
    private boolean isChecked;//是否选中
    private String parentPath;//文件夹路径


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
}
