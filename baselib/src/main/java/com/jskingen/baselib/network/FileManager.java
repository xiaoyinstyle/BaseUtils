package com.jskingen.baselib.network;

import com.jskingen.baselib.BaseHelp;
import com.jskingen.baselib.network.api.FileService;
import com.jskingen.baselib.network.callBack.OnDownLoadCallback;
import com.jskingen.baselib.network.callBack.OnUploadCallback;
import com.jskingen.baselib.network.utils.FileRequestBody;
import com.jskingen.baselib.network.utils.RequestMultipart;

import java.io.File;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by ChneY on 2017/4/14.
 * <p>
 * 上传或下载的工具类
 */

public class FileManager {
    private static FileService service;
    private static Call call;

    private static FileService service() {
        if (service == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BaseHelp.getInstance().getConfiguration().baseUrl)
                    .build();

            service = retrofit.create(FileService.class);
        }
        return service;
    }

    /**
     * @param url 下载的链接
     */
    public static void download(final String url, final OnDownLoadCallback downLoadCallback) {
        call = service().download(url);
        call.enqueue(downLoadCallback);
    }

    /**
     * 上传大文件
     */
    public static void upload(String url, File file, OnUploadCallback callback) {
        RequestBody body1 = new RequestMultipart()
                .add("file", file)
                .build();

        FileRequestBody body = new FileRequestBody(body1, callback);

        call = service().upload(url, body);
        call.enqueue(callback);
    }

    /**
     * 取消请求
     */
    public static void onCancel() {
        if (call != null)
            call.cancel();
    }
}
