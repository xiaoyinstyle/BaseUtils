package yin.style.baselib.net;


import yin.style.baselib.net.inter.ICallBack;
import yin.style.baselib.net.inter.IFileCallBack;
import yin.style.baselib.net.inter.IHttpProcessor;
import yin.style.baselib.net.processor.OkHttpProcessor;

import java.util.Map;

/**
 * Created by ChneY on 2017/6/22.
 */

public class HttpHelper implements IHttpProcessor {
    private static IHttpProcessor mIHttpProcessor = null;
    private static HttpHelper _instance;

    private HttpHelper() {
        if (mIHttpProcessor == null) {
            init(new OkHttpProcessor());
        }
    }

    public static HttpHelper getInstance() {
        if (_instance == null) {
            _instance = new HttpHelper();
        }
        if (mIHttpProcessor == null) {
            init(new OkHttpProcessor());
        }
        return _instance;
    }

    public static void init(IHttpProcessor httpProcessor) {
        mIHttpProcessor = httpProcessor;
    }

    @Override
    public void post(String url, Map<String, String> params, ICallBack callBack) {
        mIHttpProcessor.post(url, params, callBack);
    }

    @Override
    public void get(String url, Map<String, String> params, ICallBack callBack) {
        mIHttpProcessor.get(url, params, callBack);
    }

    @Override
    public void upload(String url, Map<String, Object> params, ICallBack callBack) {
        mIHttpProcessor.upload(url, params, callBack);
    }

    @Override
    public void cancel(String url) {
        mIHttpProcessor.cancel(url);
    }

    @Override
    public void uploadFile(String url, Map<String, Object> params, IFileCallBack callBack) {
        mIHttpProcessor.uploadFile(url, params, callBack);
    }

    @Override
    public void downloadFile(String url, Map<String, String> params, String filePath, IFileCallBack callBack) {
        mIHttpProcessor.downloadFile(url, params, filePath, callBack);
    }
}
