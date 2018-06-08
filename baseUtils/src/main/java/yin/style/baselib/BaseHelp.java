package yin.style.baselib;

import android.content.Context;

import yin.style.baselib.utils.AppManager;

/**
 * Created by ChneY on 2017/4/22.
 */

public class BaseHelp {
    private final String fileName = "BaseHelp";
    private final String LOG_TAG = "LOG_TAG";
    private BaseListener baseListener;
    private static BaseHelp instance;

    public static BaseHelp getInstance() {
        if (instance == null)
            instance = new BaseHelp();
        return instance;
    }

    public void init(BaseListener baseListener) {
        this.baseListener = baseListener;
    }

    public Context getContext() {
        Context mContext = AppManager.getInstance().currentActivity();
        return mContext != null ? mContext.getApplicationContext() : null;
    }

    public boolean isDebug() {
        return baseListener == null ? false : baseListener.isDebug();
    }

    /**
     * 7.0 权限 文件名
     */
    public String getFileName() {
        return baseListener == null ? fileName : baseListener.getFileName();
    }

    /**
     * BaseLog
     */
    public String getLogTag() {
        return baseListener == null ? LOG_TAG : baseListener.getLogTag();
    }


    public interface BaseListener {
        boolean isDebug();

        String getFileName();

        String getLogTag();
    }

}
