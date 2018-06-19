package yin.style.baselib.net.inter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class OnBaseResult<T> extends ICallBack<T> implements BInterceptor {


    @Override
    public Map<String, String> header(Map<String, String> headerMap) {
        return headerMap;
    }

    @Override
    public Map<String, String> post(Map<String, String> postMap) {
        return postMap;
    }

    @Override
    public Map<String, String> get(Map<String, String> getMap) {
        return getMap;
    }

    @Override
    public Map<String, File> upload(Map<String, File> uploadMap) {
        return uploadMap;
    }
}
