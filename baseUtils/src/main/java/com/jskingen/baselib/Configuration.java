package com.jskingen.baselib;

import android.content.Context;
import android.text.TextUtils;

import okhttp3.OkHttpClient;

/**
 * Created by ChneY on 2017/5/3.
 */

public class Configuration {
    public String baseUrl = "";
    public boolean debug = false;
    public String fileName = "";
    public Context context;
    public long timeout = 20L;
    public OkHttpClient.Builder okhttpBuilder;

    private Configuration() {
    }

    public static class Builder {
        Configuration configuration;

        public Builder(Context context) {
            configuration = new Configuration();
            configuration.context = context;
        }

        public Builder baseUrl(String baseUrl) {
            configuration.baseUrl = baseUrl;
            return this;
        }

        public Builder fileName(String fileName) {
            configuration.fileName = fileName;
            return this;
        }

        public Builder debug(boolean debug) {
            configuration.debug = debug;
            return this;
        }

        public Builder timeout(long timeout) {
            configuration.timeout = timeout;
            return this;
        }

        public Builder okhttpBuilder(OkHttpClient.Builder okhttpBuilder) {
            configuration.okhttpBuilder = okhttpBuilder;
            return this;
        }

        public Configuration build() {
            //OkHttp的 BaseUrl
            if (TextUtils.isEmpty(configuration.baseUrl))
                throw new NullPointerException("baseUrl cannot be null, please set baseUrl in Configuration ");
            //缓存的文件夹 目录
            if (TextUtils.isEmpty(configuration.fileName))
                throw new NullPointerException("Cache fileName cannot be null, please set fileName in Configuration ");
            return configuration;
        }

    }
}
