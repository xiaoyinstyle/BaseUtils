package com.jskingen.baselib.net.inter;

import okhttp3.Call;

/**
 * Created by ChneY on 2017/4/14.
 * 上传/下载接口
 */

public interface IFileCallBack {
    void onStart(Call call);

    void onProgress(double per, long current, long total);

    void onFinish(boolean success);

    void onError(String e);

    void cancel();
}
