//package com.jskingen.baselib;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import okhttp3.OkHttpClient;
//
///**
// * Created by ChneY on 2017/5/3.
// */
//
//public class Configuration {
//    public boolean debug = false;
//    public String fileName = "";
//    public Context context;
//
//    private Configuration() {
//    }
//
//    public static class Builder {
//        Configuration configuration;
//
//        public Builder(Context context) {
//            configuration = new Configuration();
//            configuration.context = context;
//        }
//
//        public Builder fileName(String fileName) {
//            configuration.fileName = fileName;
//            return this;
//        }
//
//        public Builder debug(boolean debug) {
//            configuration.debug = debug;
//            return this;
//        }
//
//        public Configuration build() {
//            //缓存的文件夹 目录
//            if (TextUtils.isEmpty(configuration.fileName))
//                configuration.fileName = configuration.context.getString(R.string.app_name);
//            return configuration;
//        }
//
//    }
//}
