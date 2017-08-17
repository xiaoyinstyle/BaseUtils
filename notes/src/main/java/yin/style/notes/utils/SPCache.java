package yin.style.notes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import yin.style.notes.MyApp;

/**
 * Created by ChneY on 2017/4/7.
 */

public class SPCache {

    private final static String CACHENAME = "APP_CACHE";
    private static SPCache cache;
    private static Context mContext;

    private static SharedPreferences sharedPreferences;

    public static SPCache getInstance() {
        if (mContext == null)
            mContext = MyApp.getInstance().getApplicationContext();

        if (cache == null)
            cache = new SPCache();

        if (sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(CACHENAME, Activity.MODE_PRIVATE);
        }
        return cache;
    }

    public void clearData() {
        sharedPreferences.edit().clear().commit();
    }

    /**
     * 设置九宫格密码
     */
    public String getLock() {
        return sharedPreferences.getString("password", "");
    }

    public void setLock(String password) {
        if (TextUtils.isEmpty(password))
            return;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.commit();
    }


}
