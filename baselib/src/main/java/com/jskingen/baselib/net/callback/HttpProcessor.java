package com.jskingen.baselib.net.callback;

import com.jskingen.baselib.net.inter.IFileCallBack;

import okhttp3.Call;

/**
 * Created by ChneY on 2017/6/22.
 * <p>
 */

public abstract class HttpProcessor implements IFileCallBack {


    @Override
    public void onStart(Call call) {

    }

//    @Override
//    public void onProgress(double per, long fileSizeDownloaded, long fileSize) {
//
//    }

//    @Override
//    public void onFinish(boolean success) {
//
//    }

    @Override
    public void onError(String e) {

    }

    @Override
    public void cancel() {

    }
}
