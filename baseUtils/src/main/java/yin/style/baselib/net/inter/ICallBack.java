package yin.style.baselib.net.inter;

import okhttp3.Call;

/**
 * Created by ChneY on 2017/6/22.
 * 普通的 Post / Get 请求的接口
 */

public abstract class ICallBack<T> {
    public Object setTag() {
        return null;
    }

    /**
     * 请求网络开始前，UI线程
     */
    public void onStart(Call call) {

    }

    /**
     * 对返回数据进行操作的回调， UI线程
     */
    public void onResponse(T response) {
        onSuccess(response, null);
    }

    public abstract void onSuccess(T response, String p);


    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     */
    public void onError(int code, Throwable throwable) {
    }

    /**
     * 请求网络结束后，UI线程
     */
    public void onFinish() {
    }

    /**
     * 上传过程中的进度回调，get请求不回调，UI线程
     */
    public void uploadProgress(float progress, long currentSize, long allSize) {
    }

    /**
     * 下载过程中的进度回调，UI线程
     */
    public void downloadProgress(float progress, long currentSize, long allSize) {
    }
}
