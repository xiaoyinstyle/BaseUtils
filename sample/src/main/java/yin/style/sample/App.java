package yin.style.sample;

import android.support.multidex.MultiDexApplication;

import yin.style.baselib.BaseHelp;
import yin.style.baselib.net.HttpHelper;
import yin.style.baselib.net.processor.OkHttpUtilsProcessor;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ChneY on 2017/5/2.
 */

public class App extends MultiDexApplication implements BaseHelp.BaseListener {
    //    public static String downloadUrl = "http://appcdn.fanyi.baidu.com/app/v7.2.0/app-webbutton-release.apk";
    public static String downloadUrl = "http://xmp.down.sandai.net/kankan/XMPSetupLite-xl8.exe";

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BaseHelp.getInstance().init(this);
        init();
    }

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

    @Override
    public String getLogTag() {
        return "Log_Tag";
    }
}
