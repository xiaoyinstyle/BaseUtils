package com.jskingen.baselib.net.processor;

import com.jskingen.baselib.log.Logger;
import com.jskingen.baselib.net.inter.ICallBack;
import com.jskingen.baselib.net.inter.IFileCallBack;
import com.jskingen.baselib.net.inter.IHttpProcessor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Chne on 2017/11/14.
 */

public class OkHttpUtilsProcessor implements IHttpProcessor {


    private final String Tag = "okHttpUtils";
    List<RequestCall> calls = new ArrayList<>();

    private int timeOut = 20000;//超时时间

    public OkHttpUtilsProcessor() {

    }

    public OkHttpUtilsProcessor(int timeOut) {
        this.timeOut = timeOut;
    }


    @Override
    public void post(final String url, Map<String, String> params, final ICallBack callBack) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        builder.addHeader("User-Agent", "android");
        if (params != null) {
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                builder.addParams(entry.getKey(), entry.getValue());
            }
        }
        RequestCall requestCall = builder.tag(url).build();
        calls.add(requestCall);


        requestCall.connTimeOut(timeOut)
                .readTimeOut(timeOut)
                .writeTimeOut(timeOut);

        callBack.onStart(requestCall.getCall());
        requestCall.execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                callBack.onFinish();
                clearTag(url);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
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
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    callBack.onSuccess(response);
                } catch (Exception e) {
                    callBack.onError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void get(final String url, Map<String, String> params, final ICallBack callBack) {
        GetBuilder builder = OkHttpUtils.get().url(url);
        builder.addHeader("User-Agent", "android");
        if (params != null) {
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                builder.addParams(entry.getKey(), entry.getValue());
            }
        }
        RequestCall requestCall = builder.tag(url).build();
        calls.add(requestCall);

        requestCall.connTimeOut(timeOut)
                .readTimeOut(timeOut)
                .writeTimeOut(timeOut);

        callBack.onStart(requestCall.getCall());
        requestCall.execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                callBack.onFinish();
                clearTag(url);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
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
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    callBack.onSuccess(response);
                } catch (Exception e) {
                    callBack.onError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void upload(final String url, Map<String, Object> params, final ICallBack callBack) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        builder.addHeader("User-Agent", "android");
        if (params != null) {
            Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    if (file.exists()) {
                        builder.addFile(entry.getKey(), file.getName(), file);
                    } else {
                        Logger.e(Tag, entry.getKey() + "--> 文件不存在");
                    }
                } else if (entry.getValue() instanceof String || entry.getValue() instanceof Integer) {
                    builder.addParams(entry.getKey(), "" + entry.getValue());
                }
            }
        }

        RequestCall requestCall = builder.tag(url).build();
        calls.add(requestCall);

        requestCall.connTimeOut(timeOut)
                .readTimeOut(timeOut)
                .writeTimeOut(timeOut);

        callBack.onStart(requestCall.getCall());
        requestCall.execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                callBack.onFinish();
                clearTag(url);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
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
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    callBack.onSuccess(response);
                } catch (Exception e) {
                    callBack.onError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void cancel(String url) {
        cancelTag(url);
    }

    @Override
    public void uploadFile(final String url, Map<String, Object> params, final IFileCallBack callBack) {
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        builder.addHeader("User-Agent", "android");
        if (params != null) {
            Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    if (file.exists()) {
                        builder.addFile(entry.getKey(), file.getName(), file);
                    } else {
                        Logger.e(Tag, entry.getKey() + "--> 文件不存在");
                    }
                } else if (entry.getValue() instanceof String || entry.getValue() instanceof Integer) {
                    builder.addParams(entry.getKey(), "" + entry.getValue());
                }
            }
        }

        RequestCall requestCall = builder.tag(url).build();
        calls.add(requestCall);

        requestCall.connTimeOut(timeOut)
                .readTimeOut(timeOut)
                .writeTimeOut(timeOut);


        callBack.onStart(requestCall.getCall());
        requestCall.execute(new StringCallback() {
            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                callBack.onProgress(progress, (long) (total * progress), total);
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                callBack.onFinish();
                clearTag(url);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
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
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    callBack.onSuccess(response);
                } catch (Exception e) {
                    callBack.onError(e.getMessage());
                }

            }
        });
    }

    @Override
    public void downloadFile(final String url, Map<String, String> params, String filePath, final IFileCallBack callBack) {
        GetBuilder builder = OkHttpUtils.get().url(url);
        builder.addHeader("User-Agent", "android");
        if (params != null) {
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                builder.addParams(entry.getKey(), "" + entry.getValue());
            }
        }

        RequestCall requestCall = builder.tag(url).build();
        calls.add(requestCall);

        requestCall.connTimeOut(timeOut)
                .readTimeOut(timeOut)
                .writeTimeOut(timeOut);


        callBack.onStart(requestCall.getCall());
        File file = new File(filePath);
        requestCall.execute(new FileCallBack(file.getParent(), file.getName()) {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                callBack.onProgress(progress, (long) (total * progress), total);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
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
                callBack.onFinish(false);
                clearTag(url);
            }

            @Override
            public void onResponse(File response, int id) {
                callBack.onFinish(true);
                clearTag(url);
//                try {
//                    callBack.on(response);
//                } catch (Exception e) {
//                    callBack.onError(e.getMessage());
//                }
            }
        });
    }

    /**
     * 取消一个请求
     *
     * @param tag
     */
    private void cancelTag(Object tag) {
        for (int i = calls.size() - 1; i > -1; i--) {
            if (calls.get(i).getRequest().tag().equals(tag)) {
                calls.get(i).cancel();
                calls.remove(i);
            }
        }
    }

    /**
     * 取消一个请求
     *
     * @param tag
     */
    private void clearTag(Object tag) {
        for (int i = calls.size() - 1; i > -1; i--) {
            if (calls.get(i).getRequest().tag().equals(tag)) {
//                calls.get(i).cancel();
                calls.remove(i);
            }
        }
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        for (int i = 0; i < calls.size(); i++) {
            calls.get(i).cancel();
        }
        calls.clear();

    }
}
