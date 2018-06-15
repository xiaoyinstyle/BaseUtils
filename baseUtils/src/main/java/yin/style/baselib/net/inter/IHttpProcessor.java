package yin.style.baselib.net.inter;

import java.util.Map;

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

    //下载 大文件（带进度条）
    IHttpProcessor downloadFile(String filePath);

    IHttpProcessor addInterceptor(BInterceptor interceptor);

    void callBack();
}
