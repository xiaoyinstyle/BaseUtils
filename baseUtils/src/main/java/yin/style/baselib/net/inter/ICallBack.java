package yin.style.baselib.net.inter;

import okhttp3.Call;

/**
 * Created by ChneY on 2017/6/22.
 * 普通的 Post / Get 请求的接口
 */

public interface ICallBack<T> {
    void onStart(Call call);

    void onSuccess(T result);

    void onError(String result);

    void onFinish();
}
