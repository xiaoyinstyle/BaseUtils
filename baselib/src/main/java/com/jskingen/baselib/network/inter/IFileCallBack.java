package com.jskingen.baselib.network.inter;

/**
 * Created by ChneY on 2017/4/14.
 * 上传/下载接口
 */

public interface IFileCallBack {
    void onStart();

    void onLoading(int per, long fileSizeDownloaded, long fileSize);

    void onFinish(boolean success);

    void onError(Exception e);

    void cancel();
}
