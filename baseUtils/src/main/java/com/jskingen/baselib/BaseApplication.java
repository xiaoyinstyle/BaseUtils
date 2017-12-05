package com.jskingen.baselib;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.jskingen.baselib.utils.AppManager;

/**
 * Created by ChneY on 2017/4/22.
 */

public abstract class BaseApplication extends MultiDexApplication {
    public String LOG_TAG = "LOG_TAG";
    protected static BaseApplication instance;
    private Context mContext;   //ApplicationContext

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    protected abstract void init();

    public abstract boolean isDebug();

    public Context getContext() {
        if (null == mContext)
            mContext = AppManager.getInstance().currentActivity().getApplicationContext();
        return mContext;
    }

    /**
     * 7.0 权限 文件名
     *
     * @return
     */
    public abstract String getFileName();

}
