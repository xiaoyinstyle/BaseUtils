package yin.style.baselib.update;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import yin.style.baselib.update.inter.OnUpdateListener;
import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.view.IOSDialog;

import java.io.File;

/**
 * Created by Chne on 2017/8/31.
 * <p>
 * 封装的 软件更新
 */

public class UpdateApkUtils {
    private final int DOWNLOAD_BY_SYSTEM = 1003;
    private final int DOWNLOAD_BY_BROWSER = 1004;
    private final int DOWNLOAD_BY_OTHER = 1005;


    private Activity activity;

    private int localVersionCode = 0;
    private String localVersionName = "";

    private boolean checkVersionName;//对比方式  true: 版本Name，false: 版本Code
    private int serverVersionCode = 0;
    private String serverVersionName = "";

    private String apkPath = "";//这里应该是一个完整的 路径 (包括 **.apk)
    private String downloadUrl = null;//下载路径
    private boolean isForce = false; //是否强制更新

    private SystemDownload systemDownload;
    private BrowserDownload browserDownload;
    private OtherDownload otherDownload;

    private UpdateApkUtils(Activity activity) {
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

    public static UpdateApkUtils from(Activity activity) {
        return new UpdateApkUtils(activity);
    }

    /**
     * @param checkVersionName true 通过VersionName 对比，
     *                         false 通过VersionCode 对比
     *                         默认 false
     */
    public UpdateApkUtils checkVersionName(boolean checkVersionName) {
        this.checkVersionName = checkVersionName;
        return this;
    }

    /**
     * @param serverVersionCode 服务器端 的 VersionCode
     */
    public UpdateApkUtils serverVersionCode(int serverVersionCode) {
        this.serverVersionCode = serverVersionCode;
        return this;
    }

    /**
     * @param serverVersionName 服务器端 的 VersionName
     * @return
     */
    public UpdateApkUtils serverVersionName(String serverVersionName) {
        this.serverVersionName = serverVersionName;
        return this;
    }

    //系统下载
    public SystemDownload systemDownload(String downloadUrl, String apkPath, boolean isForce) {
        systemDownload = new SystemDownload(this);
        this.downloadUrl = downloadUrl;
        this.apkPath = apkPath;
        this.isForce = isForce;
        return systemDownload;
    }

    //浏览器下载
    public BrowserDownload browserDownload(String downloadUrl, boolean isForce) {
        browserDownload = new BrowserDownload(this);
        this.downloadUrl = downloadUrl;
        this.isForce = isForce;
        return browserDownload;
    }

    //应用下载/其他下载
    public OtherDownload otherDownload(boolean isForce) {
        otherDownload = new OtherDownload(this);
        this.isForce = isForce;
        return otherDownload;
    }

    public void update(OnUpdateListener listener) {
        if (listener != null)
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
    private void downloadApk(final int downloadWay, final OnUpdateListener listener) {
        IOSDialog dialog = new IOSDialog(activity)
                .setTitle("更新提示")
                .setMessage("发现新版本:" + serverVersionName + "\n是否下载更新?")
                .setNegativeButton("取消", new IOSDialog.OnClickListener() {
                    @Override
                    public boolean onClick(Dialog dialog, View view) {
                        if (isForce)
                            activity.finish();
                        return false;
                    }
                }).setPositiveButton("更新", new IOSDialog.OnClickListener() {
                    @Override
                    public boolean onClick(Dialog dialog, View view) {
                        if (downloadWay == DOWNLOAD_BY_SYSTEM) {
                            DownloadApkUtils.downloadForAutoInstall(activity, downloadUrl, apkPath, getApkName(apkPath));
                        } else if (downloadWay == DOWNLOAD_BY_BROWSER) {
                            DownloadApkUtils.downloadForWebView(activity, apkPath);
                        } else {
                            listener.result(true);
                        }
                        return false;
                    }
                });

        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * @return 获取 Apk 的文件名
     */
    private String getApkName(String apkPath) {
        return apkPath.substring(apkPath.lastIndexOf("/") + 1, apkPath.length());
    }

    /**
     * 系统下载方式
     */
    public class SystemDownload {
        private UpdateApkUtils utils;

        SystemDownload(UpdateApkUtils updateApkUtils) {
            utils = updateApkUtils;
        }

//        // apkPath 下载保存在本地的 文件完整路径地址
//        public SystemDownload apkPath(String apkPath) {
//            utils.apkPath = apkPath;
//            return this;
//        }
//
//        // downloadUrl 下载的 地址
//        public SystemDownload downloadUrl(String downloadUrl) {
//            utils.downloadUrl = downloadUrl;
//            return this;
//        }
//
//
//        // isForce 强制更新
//        public SystemDownload isForce(boolean isForce) {
//            utils.isForce = isForce;
//            return this;
//        }

        // 开始
        public void update() {
            if (checkVersion()) {
                //需要更新
                if (TextUtils.isEmpty(utils.apkPath)) {
                    Toast.makeText(activity, "apkPath is null, 文件路径不为能空", Toast.LENGTH_SHORT).show();
                } else if (utils.downloadUrl == null) {
                    Toast.makeText(activity, "downloadUrl is null, 下载地址不为能空", Toast.LENGTH_SHORT).show();
                } else
                    utils.downloadApk(DOWNLOAD_BY_SYSTEM, null);
            }
        }
    }

    /**
     * 跳转浏览器 下载方式
     */
    public class BrowserDownload {
        private UpdateApkUtils utils;

        BrowserDownload(UpdateApkUtils updateApkUtils) {
            utils = updateApkUtils;
        }

//        // downloadUrl 下载的 地址
//        public BrowserDownload downloadUrl(String downloadUrl) {
//            utils.downloadUrl = downloadUrl;
//            return this;
//        }
//
//
//        // isForce 强制更新
//        public BrowserDownload isForce(boolean isForce) {
//            utils.isForce = isForce;
//            return this;
//        }

        // 开始
        public void update() {
            if (checkVersion()) {
                //需要更新
             /*   if (TextUtils.isEmpty(utils.apkPath)) {
                    Toast.makeText(activity, "apkPath is null, 文件路径不为能空", Toast.LENGTH_SHORT).show();
                } else */
                if (utils.downloadUrl == null) {
                    Toast.makeText(activity, "downloadUrl is null, 下载地址不为能空", Toast.LENGTH_SHORT).show();
                } else
                    utils.downloadApk(DOWNLOAD_BY_BROWSER, null);
            }
        }
    }

    /**
     * 跳转浏览器 下载方式
     */
    public class OtherDownload {
        private UpdateApkUtils utils;

        OtherDownload(UpdateApkUtils updateApkUtils) {
            utils = updateApkUtils;
        }
//
//        // isForce 强制更新
//        public OtherDownload isForce(boolean isForce) {
//            utils.isForce = isForce;
//            return this;
//        }

        // 开始
        public void update(OnUpdateListener listener) {
            if (checkVersion()) {
//                //需要更新
//                if (TextUtils.isEmpty(utils.apkPath)) {
//                    Toast.makeText(activity, "apkPath is null, 文件路径不为能空", Toast.LENGTH_SHORT).show();
//                } else if (utils.downloadUrl == null) {
//                    Toast.makeText(activity, "downloadUrl is null, 下载地址不为能空", Toast.LENGTH_SHORT).show();
//                } else
                utils.downloadApk(DOWNLOAD_BY_OTHER, listener);
            }
        }
    }

    /**
     * 安装
     */
    public static void installApk(Context context, String apkPath) {
        try {
            String[] args2 = {"chmod", "777", apkPath};
            Runtime.getRuntime().exec(args2);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent in = new Intent(Intent.ACTION_VIEW);
        File apkFile = new File(apkPath);
        if (Build.VERSION.SDK_INT >= 24) {
            in.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileUtils.getUri2File(context, apkFile);
            in.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            in.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);

    }
}
