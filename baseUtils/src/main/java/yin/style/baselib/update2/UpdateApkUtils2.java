package yin.style.baselib.update2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import yin.style.baselib.R;
import yin.style.baselib.update2.inter.OnUpdateCallback;
import yin.style.baselib.update2.service.DownloadReceiver;
import yin.style.baselib.update2.service.DownloadService;
import yin.style.baselib.utils.ToastUtils;

public class UpdateApkUtils2 {

    private static final String TAG = "UpdateApkUtils";

    private Activity mContext;

    private int localVersionCode = 0;
    private String localVersionName = "";

    private boolean checkVersionName;//对比方式  true: 版本Name，false: 版本Code
    private int serverVersionCode = 0;
    private String serverVersionName = "";
    private boolean canIgnore = true;//可以忽略

    private String downloadUrl = null;//下载路径
    private File apkFile = null;//保存路径，这里应该是一个完整的 路径 (包括 **.apk)
    private boolean isForce = false; //是否强制更新


    private CharSequence updateTitle;
    private CharSequence updateMessage;
    private OnUpdateCallback onUpdateCallback;
    private Intent downloadService;
    private MyReceiver receiver;
    ProgressDialog progressDialog;


    public UpdateApkUtils2(Activity activity) {
        this.mContext = activity;
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_RECEIVER");
        //注册
        mContext.registerReceiver(receiver, filter);

        getCurrentVersion(activity);
    }

    /**
     * 获取apk的版本号 currentVersionCode
     *
     * @param ctx
     */
    private void getCurrentVersion(Context ctx) {
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            localVersionName = info.versionName; // 版本名
            localVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "getAppLocalVersion: 获取版本号异常");
        }
    }

    /**
     * @param checkVersionName true 通过VersionName 对比，
     *                         false 通过VersionCode 对比
     *                         默认 false
     */
    public UpdateApkUtils2 checkVersionName(boolean checkVersionName) {
        this.checkVersionName = checkVersionName;
        return this;
    }

    /**
     * @param serverVersionCode 服务器端 的 VersionCode
     */
    public UpdateApkUtils2 serverVersionCode(int serverVersionCode) {
        this.serverVersionCode = serverVersionCode;
        return this;
    }

    /**
     * @param serverVersionName 服务器端 的 VersionName
     * @return
     */
    public UpdateApkUtils2 serverVersionName(String serverVersionName) {
        this.serverVersionName = serverVersionName;
        return this;
    }

    /**
     * @param force 是否强制更新
     */
    public void setForce(boolean force) {
        isForce = force;
    }

    public UpdateApkUtils2 setCanIgnore(boolean canIgnore) {
        this.canIgnore = canIgnore;
        return this;
    }

    private boolean showCustomDialog;//显示自定义Dialog

    //    public UpdateApkUtils2 showCustomDialog() {
//        showCustomDialog = true;
//
//        return this;
//    }
    public UpdateApkUtils2 setDialogHint(CharSequence updateTitle, CharSequence updateMessage) {
        this.updateTitle = updateTitle;
        this.updateMessage = updateMessage;
        return this;
    }

    public UpdateApkUtils2 setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    public UpdateApkUtils2 setApkFilePath(File apkFile) {
        this.apkFile = apkFile;
        return this;
    }

    /**
     * 对比检查
     */
    public void check() {
        check(null);
    }

    public void check(OnUpdateCallback onUpdateCallback) {
        this.onUpdateCallback = onUpdateCallback;
        if (checkVersion()) {
            //需要更新
            if (apkFile == null) {
                Toast.makeText(mContext, "apkPath is null, 文件路径不为能空", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(downloadUrl)) {
                Toast.makeText(mContext, "downloadUrl is null, 下载地址不为能空", Toast.LENGTH_SHORT).show();
            } else
                showUpdateHint();
        }
    }

    /**
     * 取消广播的注册
     */
    public void destroy() {
        //不要忘了这一步
        if (mContext != null && downloadService != null)
            mContext.stopService(downloadService);
        if (mContext != null && receiver != null)
            mContext.unregisterReceiver(receiver);
    }


    /**
     * 显示更新提示DIalog
     */
    private void showUpdateHint() {
        //简约式对话框展示对话信息的方式
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog alertDialog = builder.create();
        if (TextUtils.isEmpty(updateMessage))
            updateMessage = "新版本，欢迎更新";

        if (TextUtils.isEmpty(updateTitle)) {
            updateTitle = "新版本 " + (TextUtils.isEmpty(serverVersionName) ? "" : "v" + serverVersionName);
        }
        alertDialog.setTitle(updateTitle);
        alertDialog.setMessage(updateMessage);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForce) {
                    if (onUpdateCallback != null)
                        onUpdateCallback.exit();
                }
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startUpdate();
            }
        });
        if (canIgnore) {
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "忽略此版本", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //忽略此版本
                    ToastUtils.show("此版本已忽略");
                }
            });
        }
        if (isForce) {
            alertDialog.setCancelable(false);
        }
        alertDialog.show();
        ((TextView) alertDialog.findViewById(android.R.id.message)).setLineSpacing(5, 1);
        Button btnPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnNegative.setTextColor(ContextCompat.getColor(mContext, R.color.base_text_dialog));
        if (canIgnore) {
            Button btnNeutral = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
            btnNeutral.setTextColor(ContextCompat.getColor(mContext, R.color.base_text_dialog));
        }
        btnPositive.setTextColor(ContextCompat.getColor(mContext, R.color.base_text_dialog));
    }

    /**
     * 开始下载
     * 获取权限 Manifest.permission.WRITE_EXTERNAL_STORAGE
     * 判断wifi 可以在这里
     */
    private void startUpdate() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIcon(R.drawable.ic_update_launcher);
            progressDialog.setTitle("正在更新...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条对话框//样式（水平，旋转）
            //进度最大值
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();

        createFileAndDownload(apkFile, downloadUrl);
    }

    //创建文件并下载文件
    private void createFileAndDownload(File file, String downurl) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.createNewFile()) {
                ToastUtils.show("文件创建失败");
            } else {
                //文件创建成功
                downloadService = new Intent(mContext, DownloadService.class);
                downloadService.putExtra("downUrl", downurl);
                downloadService.putExtra("fileName", apkFile.getPath());
                downloadService.putExtra("appName", mContext.getString(R.string.app_name));
                mContext.startService(downloadService);

//                //显示dialog
//                if (showType == AlertDialog.Builder.TYPE_DIALOG) {
//                    progressDialog = new ProgressDialog(mContext);
//                    if (iconRes != 0) {
//                        progressDialog.setIcon(iconRes);
//                    } else {
//                        progressDialog.setIcon(R.mipmap.ic_launcher1);
//                    }
//                    progressDialog.setTitle("正在更新...");
//                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条对话框//样式（水平，旋转）
//                    //进度最大值
//                    progressDialog.setMax(100);
//                    progressDialog.setCancelable(false);
//                    progressDialog.show();
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * 广播接收器
     *
     * @author user
     */
    private class MyReceiver extends DownloadReceiver {
        @Override
        protected void downloadComplete() {
            if (progressDialog != null)
                progressDialog.dismiss();
//            if (showAndDownDialog != null)
//                showAndDownDialog.dismiss();
            try {
                if (mContext != null && downloadService != null)
                    mContext.stopService(downloadService);
                if (mContext != null && receiver != null)
                    mContext.unregisterReceiver(receiver);
            } catch (Exception e) {
            }
        }

        @Override
        protected void downloading(int progress) {
            ToastUtils.show("下载:" + progress);
            if (progressDialog != null)
                progressDialog.setProgress(progress);
//            if (showType == Builder.TYPE_DIALOG) {
//                if (progressDialog != null)
//                    progressDialog.setProgress(progress);
//            } else if (showType == Builder.TYPE_DIALOG_WITH_PROGRESS) {
//                if (showAndDownUpdateProView != null)
//                    showAndDownUpdateProView.setProgress(progress);
//            }
            if (onUpdateCallback != null)
                onUpdateCallback.onProgress((float) progress / 100);
        }

        @Override
        protected void downloadFail(String e) {
//            if (progressDialog != null)
//                progressDialog.dismiss();
            Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }
}