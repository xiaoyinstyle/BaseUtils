package yin.style.baselib.net.processor.okhttp;

import android.os.Handler;

import yin.style.baselib.log.Logger;
import yin.style.baselib.net.inter.ICallBack;
import yin.style.baselib.net.inter.IFileCallBack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ChneY on 2017/7/14.
 */

public class RequestData {
    private final String Tag = "okhttp";

    private Handler handler;
    private OkHttpClient okHttpClient;
    private List<Call> calls = new ArrayList<>();

    private RequestData() {
    }

    public RequestData(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        handler = new Handler();
    }

    /**
     * 开始 网络 请求
     *
     * @param request
     * @param callBack
     */
    public void start(final Request request, final ICallBack callBack) {
        Call call = okHttpClient.newCall(request);
        calls.add(call);
        callBack.onStart(call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e(e.toString());
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

    public void startDownLoad(Request request, String filePath, final IFileCallBack callBack) {
        final File file = new File(filePath);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
//        if (file.exists()) {
//            callBack.onFinish(true);
//            return;
//        }
        Call call = okHttpClient.newCall(request);
        calls.add(call);
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
                        cancel(call.request().tag().toString());
                    }
                }, 200);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFinish(false);
                            callBack.onError(response.message());
                        }
                    });
                } else {
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
                                    callBack.onProgress(((float) finalCurrent / (float) total), finalCurrent, total);
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
                            cancel(call.request().tag().toString());
                            if (is != null) is.close();
                            if (fos != null) fos.close();
                        } catch (final IOException e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onError(e.getMessage());
                                }
                            });
                        }
                    }
                }
            }
        });
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
    }

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

}
