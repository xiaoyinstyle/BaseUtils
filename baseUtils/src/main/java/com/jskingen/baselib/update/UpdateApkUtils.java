package com.jskingen.baselib.update;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.jskingen.baselib.update.dailog.ConfirmDialog;
import com.jskingen.baselib.update.inter.DialogListener;
import com.jskingen.baselib.update.inter.OnUpdateListener;

/**
 * Created by Chne on 2017/8/31.
 * <p>
 * 封装的 软件更新
 */

public class UpdateAppUtils {
    public static final int DOWNLOAD_BY_APP = 1003;
    public static final int DOWNLOAD_BY_BROWSER = 1004;


    private boolean checkVersionName;//对比方式  true: 版本Name，false: 版本Code
    private int downloadWay;//下载方式
    private int serverVersionCode = 0;
    private String apkPath = "";//这里应该是一个完整的 路径 (包括 **.apk)
    private String downloadUrl = null;//下载路径
    private String serverVersionName = "";
    private boolean isForce = false; //是否强制更新
    private int localVersionCode = 0;
    private String localVersionName = "";
    private Activity activity;


    public String getApkPath() {
        return apkPath;
    }

    private UpdateAppUtils(Activity activity) {
        this.activity = activity;
        getAPPLocalVersion(activity);
    }

    //获取apk的版本号 currentVersionCode
    private void getAPPLocalVersion(Context ctx) {
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            localVersionName = info.versionName; // 版本名
            localVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static UpdateAppUtils from(Activity activity) {
        return new UpdateAppUtils(activity);
    }

    /**
     * @param checkVersionName true 通过VersionName 对比，
     *                         false 通过VersionCode 对比
     *                         默认 false
     */
    public UpdateAppUtils checkVersionName(boolean checkVersionName) {
        this.checkVersionName = checkVersionName;
        return this;
    }

    /**
     * @param apkPath 下载保存在本地的 文件完整路径地址
     */
    public UpdateAppUtils apkPath(String apkPath) {
        this.apkPath = apkPath;
        return this;
    }

    /**
     * @param downloadWay 下载方式 DownloadManager、浏览器、app
     */
    public UpdateAppUtils downloadWay(int downloadWay) {
        this.downloadWay = downloadWay;
        return this;
    }

    /**
     * @param downloadUrl 下载的 地址
     * @return
     */
    public UpdateAppUtils downloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    /**
     * @param serverVersionCode 服务器端 的 VersionCode
     */
    public UpdateAppUtils serverVersionCode(int serverVersionCode) {
        this.serverVersionCode = serverVersionCode;
        return this;
    }

    /**
     * @param serverVersionName 服务器端 的 VersionName
     * @return
     */
    public UpdateAppUtils serverVersionName(String serverVersionName) {
        this.serverVersionName = serverVersionName;
        return this;
    }

    /**
     * @param isForce 强制更新
     */
    public UpdateAppUtils isForce(boolean isForce) {
        this.isForce = isForce;
        return this;
    }

    /**
     * 开始
     */
    public void update() {
        if (checkVersion()) {
            //需要更新
            if (TextUtils.isEmpty(apkPath)) {
                Toast.makeText(activity, "apkPath is null, 文件路径不为能空", Toast.LENGTH_SHORT).show();
            } else if (downloadUrl == null) {
                Toast.makeText(activity, "downloadUrl is null, 下载地址不为能空", Toast.LENGTH_SHORT).show();
            } else
                downloadApk();
        }
    }


    public void update(OnUpdateListener listener) {
        if (listener == null) return;

        listener.result(checkVersion());
    }

    /**
     * 进行版本比较
     */
    private boolean checkVersion() {
        if (checkVersionName) {
            //通过版本Name来判断 ，不相同 则进行更新
            if (!serverVersionName.equals(localVersionName))
                return true;
        } else {
            //通过版本Code来判断 ，大于Code的 则进行更新
            if (serverVersionCode > localVersionCode)
                return true;
        }
        return false;
    }

    /**
     * 下载Apk
     */
    private void downloadApk() {
        ConfirmDialog dialog = new ConfirmDialog(activity, new DialogListener() {
            @Override
            public void onclick(int flag) {
                switch (flag) {
                    case 0:  //cancle
                        if (isForce)
                            activity.finish();
                        break;
                    case 1:  //sure
                        if (downloadWay == DOWNLOAD_BY_APP) {
                            DownloadApkUtils.downloadForAutoInstall(activity, downloadUrl, apkPath, getApkName(apkPath));
                        } else if (downloadWay == DOWNLOAD_BY_BROWSER) {
                            DownloadApkUtils.downloadForWebView(activity, apkPath);
                        }
                        break;
                }
            }
        });
        dialog.setContent("发现新版本:" + serverVersionName + "\n是否下载更新?");
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 获取 Apk 的文件名
     *
     * @return
     */
    private String getApkName(String apkPath) {
        return apkPath.substring(apkPath.lastIndexOf("/") + 1, apkPath.length());
    }
}
