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
    public Context context;
    public long timeout = 20L;
    public OkHttpClient.Builder okhttpBuilder;

    public Configuration(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.debug = builder.debug;
        this.context = builder.context;
        this.okhttpBuilder = builder.okhttpBuilder;
    }

    public static class Builder {
        private String baseUrl;
        private Context context;
        private long timeout;
        private boolean debug;
        private OkHttpClient.Builder okhttpBuilder;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder timeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder okhttpBuilder(OkHttpClient.Builder okhttpBuilder) {
            this.okhttpBuilder = okhttpBuilder;
            return this;
        }

        public Configuration build() {
            if (TextUtils.isEmpty(baseUrl))
                throw new NullPointerException("baseUrl cannot be null, plase set baseUrl in Configuration ");
            return new Configuration(this);
        }

    }
}
