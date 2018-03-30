package yin.style.baselib.net.processor;


import yin.style.baselib.log.Logger;
import yin.style.baselib.net.processor.okhttp.FileRequestBody;
import yin.style.baselib.net.inter.ICallBack;
import yin.style.baselib.net.inter.IFileCallBack;
import yin.style.baselib.net.inter.IHttpProcessor;
import yin.style.baselib.net.processor.okhttp.RequestData;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ChneY on 2017/6/22.
 */

public class OkHttpProcessor implements IHttpProcessor {
    private final String Tag = "okhttp";

    private RequestData requestData;

    public OkHttpProcessor() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .build();

        requestData = new RequestData(okHttpClient);
    }

    @Override
    public void post(String url, Map<String, String> params, final ICallBack callBack) {
        RequestBody requestBody = appendBody(params);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .tag(url)
                .header("User-Agent", "a")
                .build();

        requestData.start(request, callBack);
    }

    @Override
    public void get(String url, Map<String, String> params, ICallBack callBack) {
        String finalUrl = appendParams(url, params);

        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .tag(url)
                .header("User-Agent", "a")
                .build();

        requestData.start(request, callBack);
    }

    @Override
    public void upload(String url, Map<String, Object> params, final ICallBack callBack) {
        MultipartBody mBody = appendMulBody(params);
        Request request = new Request.Builder()
                .url(url)
                .post(mBody)
                .tag(url)
                .header("User-Agent", "a")
                .build();

        requestData.start(request, callBack);
    }

    @Override
    public void cancel(String url) {
        requestData.cancel(url);
    }

    @Override
    public void uploadFile(String url, Map<String, Object> params, final IFileCallBack callBack) {
        MultipartBody mBody = appendMulBody(params);
        FileRequestBody fileRequestBody = new FileRequestBody(mBody, callBack);

        Request request = new Request.Builder()
                .url(url)
                .post(fileRequestBody)
                .tag(url)
                .header("User-Agent", "a")
                .build();

        requestData.start(request, callBack);
    }

    @Override
    public void downloadFile(String url, Map<String, String> params, String filePath, IFileCallBack callBack) {
        String finalUrl = (params == null || params.isEmpty()) ? url : appendParams(url, params);

        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .tag(url)
                .header("User-Agent", "a")
                .build();

        requestData.startDownLoad(request, filePath, callBack);
    }

    /**
     * get 请求的Url 拼接
     *
     * @param params
     * @return
     */
    private String appendParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty())
            return url;

        StringBuilder sb = new StringBuilder(url);
        if (sb.indexOf("?") <= 0) {
            sb.append("?");
        } else {
            if (!sb.toString().endsWith("&")) {
                sb.append("&");
            }
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=")
                    .append(encode(entry.getValue().toString()))
                    .append("&");
        }
        return sb.toString();
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            Logger.e("参数转码异常:"+ e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * 多文件 上传
     *
     * @param params
     * @return
     */
    private MultipartBody appendMulBody(Map<String, Object> params) {
        MultipartBody.Builder mBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null & !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    if (file.exists()) {
                        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                        mBuilder.addFormDataPart(entry.getKey(), file.getName(), fileBody);
                    } else
                        Logger.e(entry.getKey() + "--> 文件不存在");
                } else if (entry.getValue() instanceof String || entry.getValue() instanceof Integer) {
                    mBuilder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        return mBuilder.build();
    }

    /**
     * 普通 post 上传
     *
     * @param params
     * @return
     */
    private RequestBody appendBody(Map<String, String> params) {
        FormBody.Builder body = new FormBody.Builder();

        if (params != null & !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                body.add(entry.getKey(), entry.getValue().toString());
            }
        }
        return body.build();
    }
}