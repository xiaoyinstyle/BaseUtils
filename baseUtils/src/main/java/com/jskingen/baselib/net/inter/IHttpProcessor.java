package com.jskingen.baselib.net.inter;

import java.io.File;
import java.util.Map;

/**
 * Created by ChneY on 2017/6/22.
 * <p>
 * 网络请求 代理接口
 */

public interface IHttpProcessor {

    //post请求
    void post(String url, Map<String, String> params, ICallBack callBack);

    //get 请求
    void get(String url, Map<String, String> params, ICallBack callBack);

    //上传 文字和文件
    void upload(String url, Map<String, Object> params, ICallBack callBack);

    //取消 请求
    void cancel(String url);

    //上传 大文件（带进度条）
    void uploadFile(String url, Map<String, Object> params, IFileCallBack callBack);

    //下载 大文件（带进度条）
    void downloadFile(String url, Map<String, String> params, String filePath, IFileCallBack callBack);
}
