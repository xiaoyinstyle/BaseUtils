package yin.style.baselib.net.adapter;

import com.lzy.okgo.model.Response;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

public abstract class IObserver<T> implements Observer<Response<T>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Response<T> t) {
        onSuccess(t.body());
    }

    @Override
    public void onComplete() {
        onFinish();
    }

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
    public abstract void onSuccess(T response);


    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     */
    public void onError(Throwable throwable) {
    }

    /**
     * 请求网络结束后，UI线程
     */
    public void onFinish() {
    }

//    /**
//     * 上传过程中的进度回调，get请求不回调，UI线程
//     */
//    public void uploadProgress(float progress, long currentSize, long allSize) {
//    }
//
//    /**
//     * 下载过程中的进度回调，UI线程
//     */
//    public void downloadProgress(float progress) {
//    }
}
