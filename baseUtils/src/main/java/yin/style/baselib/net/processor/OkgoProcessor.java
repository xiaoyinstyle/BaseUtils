package yin.style.baselib.net.processor;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.CallAdapter;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.BitmapConvert;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.BodyRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;
import com.lzy.okrx2.adapter.ObservableResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import yin.style.baselib.BuildConfig;
import yin.style.baselib.log.Logger;
import yin.style.baselib.net.adapter.IObserver;
import yin.style.baselib.net.inter.BInterceptor;
import yin.style.baselib.net.inter.ICallBack;
import yin.style.baselib.net.inter.IHttpProcessor;
import yin.style.baselib.net.inter.OnBitmapResult;
import yin.style.baselib.net.inter.OnFileResult;
import yin.style.baselib.net.utils.BHUtils;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/6/15.
 * <p>
 * https://github.com/jeasonlzy/okhttp-OkGo
 */
public class OkgoProcessor implements IHttpProcessor {
    private final String TAG = "OkgoProcessor";
    private Map<String, String> headerMaps = new HashMap<>();
    private Map<String, String> getMaps = new HashMap<>();
    private Map<String, String> postMaps = new HashMap<>();
    private Map<String, File> uploadMaps = new HashMap<>();
    private String url;

    public OkgoProcessor(String url) {
        this.url = url;
    }

    @Override
    public IHttpProcessor header(Map<String, String> params) {
        headerMaps.putAll(params);
        return this;
    }

    @Override
    public IHttpProcessor header(String key, String value) {
        headerMaps.put(key, value);
        return this;
    }

    @Override
    public IHttpProcessor post(Map<String, String> params) {
        postMaps.putAll(params);
        return this;
    }

    @Override
    public IHttpProcessor post(String key, String value) {
        postMaps.put(key, value);
        return this;
    }

    @Override
    public IHttpProcessor get(Map<String, String> params) {
        getMaps.putAll(params);
        return this;
    }

    @Override
    public IHttpProcessor get(String key, String value) {
        getMaps.put(key, value);
        return this;
    }

    @Override
    public IHttpProcessor upload(Map<String, Object> params) {
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            upload(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public IHttpProcessor upload(String key, Object value) {
        if (value == null) {
            postMaps.put(key, "");
        } else if (value instanceof File) {
            File file = (File) value;
            if (file.exists()) {
                uploadMaps.put(key, file);
            } else {
                Logger.e(key + "--> 文件不存在");
            }
        } else if (value instanceof String || value instanceof Number) {
            postMaps.put(key, "" + value);
        } else {
            Logger.e(key + "--> 未知类型");
        }
        return this;
    }

    @Override
    public void callBack(final ICallBack iCallBack) {
        BInterceptor bInterceptor = null;
        if (iCallBack != null && iCallBack instanceof BInterceptor) {
            bInterceptor = (BInterceptor) iCallBack;
        }
        final Request request = getRequest(bInterceptor);
        //设置Tag
        if (iCallBack != null && iCallBack.setTag() != null)
            request.tag(iCallBack.setTag());

        request.execute(new AbsCallback() {
            @Override
            public void onStart(Request request) {
                super.onStart(request);
                if (iCallBack != null)
                    iCallBack.onStart(request.getRawCall());
            }


            @Override
            public Object convertResponse(okhttp3.Response response) throws Throwable {
                ResponseBody body = response.body();
                if (body == null)
                    return null;

                if (iCallBack instanceof OnBitmapResult) {
                    BitmapConvert convert = new BitmapConvert(1000, 1000);
                    Bitmap bitmap = convert.convertResponse(response);
                    response.close();
                    return bitmap;
                } else if (iCallBack instanceof OnFileResult) {
                    OnFileResult fileResult = (OnFileResult) iCallBack;
                    File downloadFile = fileResult.getDownloadFile();
                    FileConvert convert = new FileConvert(downloadFile.getParentFile().getPath(), downloadFile.getName());
                    convert.setCallback(this);
                    File file = convert.convertResponse(response);
                    response.close();
                    return file;
                } else {
                    StringConvert convert = new StringConvert();
                    String s = convert.convertResponse(response);
                    response.close();
                    return s;
                }
//                return body.string();
            }

            @Override
            public void onSuccess(Response response) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "URL_POST: " + request.getUrl());
                    Log.d(TAG, "maps: " + request.getParams().toString());
                    Log.d(TAG, "result: " + response.body().toString());
                }
                if (iCallBack != null)
                    iCallBack.onSuccess(response.body());
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);

                if (iCallBack != null)
                    iCallBack.downloadProgress(progress.fraction);
            }

            @Override
            public void uploadProgress(Progress progress) {
                super.uploadProgress(progress);
                Log.e(TAG, "uploadProgress: " + progress.toString());
                if (iCallBack != null)
                    iCallBack.uploadProgress(progress.fraction, progress.currentSize, progress.totalSize);
            }

            @Override
            public void onError(Response response) {
                super.onError(response);
                if (iCallBack != null)
                    iCallBack.onError(response.getException());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (iCallBack != null)
                    iCallBack.onFinish();
            }
        });
    }

    @Override
    public <T> void subscribe(final IObserver<T> iObserver) {
        BInterceptor bInterceptor = null;
        if (iObserver != null && iObserver instanceof BInterceptor) {
            bInterceptor = (BInterceptor) iObserver;
        }

        final Request<T, ? extends Request> request = getRequest(bInterceptor);
        //设置Tag
        if (iObserver != null && iObserver.setTag() != null)
            request.tag(iObserver.setTag());

        CallAdapter<T, Observable<T>> callAdapter = new ObservableResponse();

        request.converter(new Converter<T>() {
            @Override
            public T convertResponse(okhttp3.Response response) throws Throwable {
                ResponseBody body = response.body();
                if (body == null) return null;

                return (T) body.string();
            }
        });
        request.adapt(callAdapter)
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        if (iObserver != null)
                            iObserver.onStart(request.getRawCall());
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(iObserver);
    }

    /**
     * 获取
     *
     * @param BInterceptor
     * @return
     */
    public Request getRequest(BInterceptor bInterceptor) {
        Map<String, File> tempUploadMaps = uploadMaps;
        Map tempPostMaps = postMaps;
        Map<String, String> tempHeaderMaps = headerMaps;
        Map tempGetMaps = getMaps;

//        if (bInterceptor != null && iCallBack instanceof BInterceptor) {
        if (bInterceptor != null) {
            //拦截
            tempUploadMaps = bInterceptor.upload(uploadMaps);
            tempGetMaps = bInterceptor.get(getMaps);
            tempPostMaps = bInterceptor.post(postMaps);
            tempHeaderMaps = bInterceptor.header(headerMaps);
        }

        Request request;
        //添加Get
        final String newUrl = BHUtils.appendParams(url, tempGetMaps);
        if ((tempUploadMaps != null && tempUploadMaps.size() > 0) || (tempPostMaps != null && tempPostMaps.size() > 0)) {
            request = OkGo.<String>post(newUrl);
            //添加Post
            request.params(tempPostMaps);
            //添加文件
            for (Map.Entry<String, File> entry : tempUploadMaps.entrySet()) {
                if (!TextUtils.isEmpty(entry.getKey()) && entry.getValue().exists())
                    ((BodyRequest) request).params(entry.getKey(), entry.getValue(), entry.getValue().getName());
            }
        } else {
            request = OkGo.<String>get(newUrl);
        }

        //添加Header
        for (Map.Entry<String, String> entry : tempHeaderMaps.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey()))
                request.headers(entry.getKey(), TextUtils.isEmpty(entry.getValue()) ? "" : entry.getValue());
        }


        return request;
    }


    @Override
    public void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }
}
