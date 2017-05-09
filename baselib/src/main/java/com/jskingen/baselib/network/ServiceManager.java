package com.jskingen.baselib.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jskingen.baselib.BaseHelp;
import com.jskingen.baselib.Configuration;
import com.jskingen.baselib.R;
import com.jskingen.baselib.network.Interceptor.LoggingInterceptor;
import com.jskingen.baselib.network.adapter.DjCallAdapterFactory;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ChneY on 2017/4/6.
 */

public class ServiceManager {

    private ServiceManager() {
        //construct
    }

    public static <T> T create(Class<T> clazz) {
        return create(clazz, null, "");
    }

    public static <T> T create(Class<T> clazz, String baseUrl) {
        return create(clazz, null, baseUrl);
    }

    public static <T> T create(Class<T> clazz, OkHttpClient client) {
        return create(clazz, client, "");
    }

    /**
     * 创建Retrofit Service
     * 自定义OkHttpClient、接口地址url
     *
     * @param clazz
     * @param client
     * @param baseUrl
     * @return
     */

    public static <T> T create(Class<T> clazz, OkHttpClient client, String baseUrl) {
        if (client == null)
            client = BaseHelp.getInstance().getOkHttpClient();

        if (TextUtils.isEmpty(baseUrl))
            baseUrl = TextUtils.isEmpty(baseUrl) ? BaseHelp.getInstance().getConfiguration().baseUrl : baseUrl;

        OkHttpClient okHttpClient = client.newBuilder()
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                 自定义请求 通用打印日志
                .addInterceptor(new LoggingInterceptor( BaseHelp.getInstance().isDebug()))
                .build();

        Retrofit retrofit = getRetrofit(okHttpClient, baseUrl);

        return retrofit.create(clazz);
    }

    @NonNull
    private static Retrofit getRetrofit(OkHttpClient okHttpClient, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(DjCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 取消请求 TODO
     * 在onDestroy时调用
     */
    public static void cancelTag(Object tag) {
        OkHttpClient mOkHttpClient = BaseHelp.getInstance().getOkHttpClient();

        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
