package yin.style.baselib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.reflect.Method;

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
//        SPCache.setClassName(baseListener.getClass().toString());
    }

    public Context getContext() {
        Context mContext = AppManager.getInstance().currentActivity();
        return mContext != null ? mContext.getApplicationContext() : null;
    }

    public boolean isDebug() {
//        if (baseListener == null) reset();

        return baseListener == null ? false : baseListener.isDebug();
    }

    /**
     * 7.0 权限 文件名
     */
    public String getFileName() {
//        if (baseListener == null) reset();

        return baseListener == null ? fileName : baseListener.getFileName();
    }

    /**
     * BaseLog
     */
    public String getLogTag() {
//        if (baseListener == null) reset();

        return baseListener == null ? LOG_TAG : baseListener.getLogTag();
    }

//    private boolean reset() {
//        try {
//            String className = SPCache.getClassName();
//            if (TextUtils.isEmpty(className))
//                return false;
//
//            Class clazz = Class.forName(className);
//            Method m1 = clazz.getDeclaredMethod("onCreate");
//            if (clazz.newInstance() instanceof Application) {
//                m1.invoke(clazz.newInstance());
//                return true;
//            } else if (clazz.newInstance() instanceof Activity) {
//                m1.invoke(clazz.newInstance(), new Bundle());
//                return true;
//            }
//            return false;
//        } catch (Exception e) {
////            e.printStackTrace();
//            return false;
//        }
//    }


    public interface BaseListener {
        boolean isDebug();

        String getFileName();

        String getLogTag();
    }

//    private static class SPCache {
//        private static final String CACHENAME = "BaseHelp_CACHE";
//
//        public static String getClassName() {
//            Context mContext = AppManager.getInstance().currentActivity().getApplicationContext();
//            SharedPreferences sharedPreferences = mContext.getSharedPreferences(CACHENAME, Activity.MODE_PRIVATE);
//            return sharedPreferences.getString("className", "");
//        }
//
//        public static void setClassName(String className) {
//            Context mContext = AppManager.getInstance().currentActivity().getApplicationContext();
//            SharedPreferences sharedPreferences = mContext.getSharedPreferences(CACHENAME, Activity.MODE_PRIVATE);
//
//            if (TextUtils.isEmpty(className))
//                return;
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("className", className);
//            editor.commit();
//        }
//    }

}
