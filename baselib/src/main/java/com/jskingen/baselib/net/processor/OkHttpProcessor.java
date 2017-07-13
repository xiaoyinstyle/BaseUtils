package com.jskingen.baselib.net.processor;

import android.os.Handler;
import android.util.Log;

import com.jskingen.baselib.log.Logger;
import com.jskingen.baselib.net.fileLoad.FileRequestBody;
import com.jskingen.baselib.net.inter.ICallBack;
import com.jskingen.baselib.net.inter.IFileCallBack;
import com.jskingen.baselib.net.inter.IHttpProcessor;
import com.jskingen.baselib.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
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

    private OkHttpClient okHttpClient = null;
    private Handler handler;
    private List<Call> calls = new ArrayList<>();

    public OkHttpProcessor() {
        okHttpClient = new OkHttpClient.Builder()
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
        handler = new Handler();
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

        start(request, callBack);
    }

    @Override
    public void get(String url, Map<String, String> params, final ICallBack callBack) {
        String finalUrl = appendParams(url, params);

        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .tag(url)
                .header("User-Agent", "a")
                .build();

        start(request, callBack);
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

        start(request, callBack);
    }

    @Override
    public void cancel(String url) {
        if (url.equals(null)) {
            for (int i = calls.size() - 1; i >= 0; i--) {
                calls.get(i).cancel();
                calls.remove(i);
            }
        } else {
            for (int i = calls.size() - 1; i >= 0; i--) {
                String tag = calls.get(i).request().tag().toString();
                if (tag.equals(url)) {
                    calls.get(i).cancel();
                    calls.remove(i);
                }
            }
        }
    }

    @Override
    public void uploadFile(String url, Map<String, Object> params, IFileCallBack callBack) {
        MultipartBody mBody = appendMulBody(params);
        FileRequestBody fileRequestBody = new FileRequestBody(mBody, callBack);

        Request request = new Request.Builder()
                .url(url)
                .post(mBody)
                .tag(url)
                .header("User-Agent", "a")
                .build();

        startUpload(request, callBack);
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

        startDownLoad(request, filePath, callBack);
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

    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            Log.e("参数转码异常", e.toString());
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
                        Logger.e(Tag, entry.getKey() + "--> 文件不存在");
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

    /**
     * 请求结束
     *
     * @param callBack
     */
    private void onFinish(Call call, ICallBack callBack) {
        callBack.onFinish();

        for (int i = 0; i < calls.size(); i++) {
            if (calls.get(i).request().tag().toString().equals(call.request().tag().toString())) {
                calls.remove(i);
                break;
            }
        }

//        Log.e("AAA", "onFinish_size:" + calls.size());

    }

    /**
     * 开始 网络 请求
     *
     * @param request
     * @param callBack
     */
    private void start(final Request request, final ICallBack callBack) {
        Call call = okHttpClient.newCall(request);
        calls.add(call);
        callBack.onStart(call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e(Tag, e.toString());
                        if (e instanceof FileNotFoundException) {
                            callBack.onError("文件操作权限未获取");
                        } else if (e instanceof ConnectException) {
                            callBack.onError("网络未连接");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onError("连接超时");
                        } else {
                            callBack.onError(e.getMessage());
                        }
                        onFinish(call, callBack);
                    }
                }, 200);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            try {
                                callBack.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            callBack.onError(response.message());
                        }
                        onFinish(call, callBack);
                    }
                }, 200);
            }
        });
    }

    private void startDownLoad(Request request, String filePath, final IFileCallBack callBack) {
        final File file = new File(filePath);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
//        if (file.exists()) {
//            callBack.onFinish(true);
//            return;
//        }
        Call call = okHttpClient.newCall(request);
        callBack.onStart(call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (e instanceof FileNotFoundException) {
                            callBack.onError("文件操作权限未获取");
                        } else if (e instanceof ConnectException) {
                            callBack.onError("网络未连接");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onError("连接超时");
                        } else
                            callBack.onError(e.getMessage());
                        callBack.onFinish(false);
                    }
                }, 200);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    final long total = response.body().contentLength();
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);

                        final long finalCurrent = current;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onProgress(((double) finalCurrent / (double) total), finalCurrent, total);
                            }
                        });
                    }
                    fos.flush();
                    callBack.onFinish(true);
                } catch (final IOException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(e.getMessage());
                            callBack.onFinish(false);
                        }
                    });
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        callBack.onError(e.getMessage());
                    }
                }
            }
        });
    }

    private void startUpload(Request request, final IFileCallBack callBack) {
        Call call = okHttpClient.newCall(request);
        callBack.onStart(call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (e instanceof FileNotFoundException) {
                            callBack.onError("文件操作权限未获取");
                        } else if (e instanceof ConnectException) {
                            callBack.onError("网络未连接");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onError("连接超时");
                        } else
                            callBack.onError(e.getMessage());
                        callBack.onFinish(false);
                    }
                }, 200);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            callBack.onFinish(true);
                        } else {
                            callBack.onError(response.message());
                        }
                    }
                }, 200);
            }
        });
    }
}