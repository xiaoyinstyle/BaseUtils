package yin.style.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import yin.style.baselib.BaseHelp;
import yin.style.baselib.activity.dialog.IDialog;
import yin.style.baselib.activity.dialog.NormalDialogProxy;
import yin.style.sample.utils.DensityUtils;

import com.squareup.leakcanary.LeakCanary;
import com.zhy.autolayout.config.AutoLayoutConifg;

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
        DensityUtils.setDensity(this);
        AutoLayoutConifg.getInstance().useDeviceSize();

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

            @Override
            public boolean isCheckNetWork() {
                return true;
            }

            @Override
            public boolean setEventBus() {
                return true;
            }

            @Override
            public IDialog getIDialog(Activity mActivity) {
                return new NormalDialogProxy(mActivity);
            }
        });
        init();

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        OkGo.getInstance().init(this)
//                .setOkHttpClient(builder.build())
//        ;


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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

    }


}
