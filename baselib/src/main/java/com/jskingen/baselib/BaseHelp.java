package com.jskingen.baselib;

import android.app.Application;
import android.content.Context;

import com.jskingen.baselib.log.LogLevel;
import com.jskingen.baselib.log.Logger;
import com.jskingen.baselib.utils.AppManager;
import com.jskingen.baselib.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ChneY on 2017/4/22.
 */

public class BaseHelp extends Application {
    public static final String LOG_TAG = "LOG_JSDJ";

    private static BaseHelp instance;

    private Configuration mConfiguration;
    private Context mContext;   //ApplicationContext
    private OkHttpClient mOkHttpClient;

    public static BaseHelp getInstance() {
        if (null == instance)
            instance = new BaseHelp();
        return instance;
    }

    public boolean isDebug() {
        return mConfiguration.debug;
    }

    public Context getContext() {
        if (null == mContext)
            mContext = AppManager.getInstance().currentActivity().getApplicationContext();
        return mContext;
    }

    public void init(Configuration configuration) {
        this.mConfiguration = configuration;

        if (configuration.okhttpBuilder == null) {
            configuration.okhttpBuilder = new OkHttpClient.Builder();

            configuration.okhttpBuilder
                    .connectTimeout(configuration.timeout, TimeUnit.SECONDS)
                    .readTimeout(configuration.timeout, TimeUnit.SECONDS);
        }

        this.mOkHttpClient = configuration.okhttpBuilder.build();

        //Log工具
        LogUtils.Debug = isDebug();
        Logger.init(LOG_TAG)               // default PRETTYLOGGER or use just init()
                .setMethodCount(0)            // default 2
                .hideThreadInfo()             // default shown
                .setLogLevel(isDebug() ? LogLevel.FULL : LogLevel.NONE); // default LogLevel.FULL

        //OkHttpUtils
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(configuration.timeout, TimeUnit.MILLISECONDS)
                .readTimeout(configuration.timeout, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Configuration getConfiguration() {
        return mConfiguration;
    }
}
