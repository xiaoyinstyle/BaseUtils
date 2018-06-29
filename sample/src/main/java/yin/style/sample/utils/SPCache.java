package yin.style.sample.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import yin.style.sample.App;

/**
 * Author by ChneYin, Email 976370887@qq.com, Date on  2018/6/28.
 *
 */
public class SPCache {
    private final String TAG = "SPCache";
    private final static String CACHENAME = "APP_CACHE";
    private static SPCache cache;
    private static Context mContext;

    private static SharedPreferences sharedPreferences;

    public static SPCache getInstance() {
        if (mContext == null) {
            mContext = App.getInstance().getApplicationContext();
        }

        if (cache == null) {
            cache = new SPCache();
        }

        if (sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(CACHENAME, Activity.MODE_PRIVATE);
        }
        return cache;
    }

    public void clearData() {
        sharedPreferences.edit().clear().commit();
    }

    public void put(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.commit();
    }

    public Object get(String key, Object defaultValue) {
        if (defaultValue instanceof String) {
            return sharedPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        }
        return null;
    }


}
