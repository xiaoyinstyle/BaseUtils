package yin.style.baselib.utils.net;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 跟网络相关的工具类
 */
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
        }

//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (null != connectivity) {
//            NetworkInfo info = connectivity.getActiveNetworkInfo();
//            if (null != info && info.isConnected()) {
//                if (info.getState() == NetworkInfo.State.CONNECTED) {
//                    return true;
//                }
//            }
//        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        if (isConnected(context)) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable();
                }
            }
        }
        return false;

    }

    /**
     * 判断当前何种网络
     *
     * @param context
     */
    public static int getNetWorkInfo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        State mobile = connectivity.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).getState();
        if (wifi == State.CONNECTED) {
            return ConnectivityManager.TYPE_WIFI;
        }
        if (mobile == State.CONNECTED) {
            return ConnectivityManager.TYPE_MOBILE;
        }
        return -1;
    }

    //3G网络
    public static boolean isMobileConnected(Context context) {
        if (isConnected(context)) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Context context) {
        /*
         * Intent intent = new Intent("/"); ComponentName cm = new
         * ComponentName("com.android.settings",
         * "com.android.settings.WirelessSettings"); intent.setComponent(cm);
         * intent.setAction("android.intent.action.VIEW");
         * activity.startActivityForResult(intent, 0); Intent intent=new
         * Intent(); intent.setClassName("com.android.settings",
         * "com.android.settings.Settings"); activity.startActivity(intent);
         */
        context.startActivity(new Intent(
                android.provider.Settings.ACTION_WIRELESS_SETTINGS));

    }

}
