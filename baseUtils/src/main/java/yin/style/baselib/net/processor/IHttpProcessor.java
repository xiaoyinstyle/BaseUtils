package yin.style.baselib.net.processor;

import java.util.Map;

import yin.style.baselib.net.adapter.IObserver;
import yin.style.baselib.net.inter.ICallBack;

/**
 * Created by ChneY on 2017/6/22.
 * <p>
 * 网络请求 代理接口
 */

public interface IHttpProcessor {

//    String getUrl( );

    //header
    IHttpProcessor header(Map<String, String> params);

    IHttpProcessor header(String key, String value);

    //post
    IHttpProcessor post(Map<String, String> params);

    IHttpProcessor post(String key, String value);

    //get 请求
    IHttpProcessor get(Map<String, String> params);

    IHttpProcessor get(String key, String value);

    //上传 文字和文件
    IHttpProcessor upload(Map<String, Object> params);

    IHttpProcessor upload(String key, Object value);

    //取消 请求
    void cancel(Object tag);

    <T>void callBack(ICallBack<T> iCallBack);

    <T>void  subscribe(IObserver<T> observer);
}
