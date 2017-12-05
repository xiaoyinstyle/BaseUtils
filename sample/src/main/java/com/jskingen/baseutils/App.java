package com.jskingen.baseutils;

import com.jskingen.baselib.BaseApplication;
import com.jskingen.baselib.net.HttpHelper;
import com.jskingen.baselib.net.processor.OkHttpUtilsProcessor;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ChneY on 2017/5/2.
 */

public class App extends BaseApplication {
    //    public static String downloadUrl = "http://appcdn.fanyi.baidu.com/app/v7.2.0/app-webbutton-release.apk";
    public static String downloadUrl = "http://xmp.down.sandai.net/kankan/XMPSetupLite-xl8.exe";

    public static App getInstance() {
        return (App) instance;
    }

    @Override
    protected void init() {
        HttpHelper.init(new OkHttpUtilsProcessor());

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }

    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public String getFileName() {
        return getString(R.string.app_name);
    }
}
