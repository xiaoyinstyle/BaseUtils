//package com.jskingen.baselib;
//
//import android.app.Application;
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.jskingen.baselib.log.LogLevel;
//import com.jskingen.baselib.log.Logger;
//import com.jskingen.baselib.utils.AppManager;
//import com.jskingen.baselib.utils.LogUtils;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.log.LoggerInterceptor;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//
///**
// * Created by ChneY on 2017/4/22.
// */
//
//public class BaseHelp {
//    public static final String LOG_TAG = "LOG_JSDJ";
//
//    private static BaseHelp instance;
//
//    private Configuration mConfiguration;
//    private Context mContext;   //ApplicationContext
//
//    public static BaseHelp getInstance() {
//        if (null == instance)
//            instance = new BaseHelp();
//        return instance;
//    }
//
//    public boolean isDebug() {
//        return mConfiguration.debug;
//    }
//
//    public Context getContext() {
//        if (null == mContext)
//            mContext = AppManager.getInstance().currentActivity().getApplicationContext();
//        return mContext;
//    }
//
//    public void init(Configuration configuration) {
//        mConfiguration = configuration;
//    }
//
//    public String getFileName() {
//        if (mConfiguration == null || TextUtils.isEmpty(mConfiguration.fileName))
//            return getContext().getString(R.string.app_name);
//        return mConfiguration.fileName;
//    }
//}
