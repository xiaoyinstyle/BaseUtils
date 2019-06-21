package yin.style.baselib;

import android.app.Activity;
import android.content.Context;

import yin.style.baselib.activity.dialog.IDialog;
import yin.style.baselib.activity.dialog.NormalDialogProxy;
import yin.style.baselib.utils.AppManager;

/**
 * Created by ChneY on 2017/4/22.
 */

public class BaseHelp {
    private final String fileName = "BaseHelp";
    private final String LOG_TAG = "LOG_TAG";
    private BaseListener baseListener;
    private static BaseHelp instance;
    private IDialog normalDialog;

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

    /**
     * 网络状态View
     */
    public boolean isCheckNetWork() {
        return baseListener == null ? false : baseListener.isCheckNetWork();
    }

    /**
     * setEventBus
     */
    public boolean setEventBus() {
        return baseListener == null ? false : baseListener.setEventBus();
    }

    public IDialog getIDialog(Activity mActivity) {
        IDialog iDialog = baseListener == null ? null : baseListener.getIDialog(mActivity);
        return iDialog == null ? new NormalDialogProxy(mActivity) : iDialog;
    }

    public boolean getBarTextDark() {
        return baseListener == null ? false : baseListener.getBarTextDark();
    }

    public abstract static class BaseListener {
        public abstract boolean isDebug();

        public abstract String getFileName();

        public abstract String getLogTag();

        public boolean isCheckNetWork() {
            return false;
        }

        public boolean setEventBus() {
            return false;
        }

        public abstract IDialog getIDialog(Activity mActivity);

        public boolean getBarTextDark() {
            return false;
        }
    }
}
