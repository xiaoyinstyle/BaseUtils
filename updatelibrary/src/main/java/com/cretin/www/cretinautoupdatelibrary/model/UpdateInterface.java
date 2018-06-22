package com.cretin.www.cretinautoupdatelibrary.model;

/**
 * Created by cretin on 2017/4/20.
 */

public interface UpdateInterface {
    //获取版本号
    int getVersionCodes();

    //是否强制更新
    boolean getIsForceUpdates();

    //版本号 描述作用
    String getVersionNames();

    //新安装包的下载地址
    String getDownUrls();

    //更新日志
    String getUpdateLogs();

    //安装包大小 单位字节
    long getApkSizes();
}
