package com.jskingen.baselib.net.inter;

/**
 * Created by ChneY on 2017/4/14.
 * 上传/下载接口
 */

public interface IFileCallBack extends ICallBack{

    void onProgress(double per, long current, long total);

    void onFinish(boolean success);

    void cancel();
}
