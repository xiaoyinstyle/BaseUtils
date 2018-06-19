package yin.style.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import okhttp3.OkHttpClient;
import yin.style.baselib.BaseHelp;

import com.lzy.okgo.OkGo;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ChneY on 2017/5/2.
 */

public class App extends MultiDexApplication {
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
        BaseHelp.getInstance().init(new BaseHelp.BaseListener() {
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
        });
        init();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
        ;


//        CrashHandler.getInstance().init(getApplicationContext());
//        CrashHandlerImpl.getInstance().init(getApplicationContext(),false);
//        CrashHandlerImpl.getInstance().init(getApplicationContext(),false,false,0,MainActivity.class);
//    addCrash();
    }

    private void addCrash() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                CrashHandler.getInstance().init(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    protected void init() {
//        HttpHelper.init(new OkHttpUtilsProcessor());

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }

    }


}
