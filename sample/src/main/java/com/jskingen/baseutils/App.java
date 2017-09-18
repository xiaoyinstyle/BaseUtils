package com.jskingen.baseutils;

import android.support.multidex.MultiDexApplication;

import com.jskingen.baselib.BaseHelp;
import com.jskingen.baselib.Configuration;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ChneY on 2017/5/2.
 */

public class App extends MultiDexApplication {
    public static String downloadUrl = "http://appcdn.fanyi.baidu.com/app/v7.2.0/app-webbutton-release.apk";


    @Override
    public void onCreate() {
        super.onCreate();
        init();

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    private void init() {

        Configuration configuration = new Configuration.Builder(this)
                .fileName("Demo")
                .debug(BuildConfig.DEBUG)
                .build();
        BaseHelp.getInstance().init(configuration);
    }
}
