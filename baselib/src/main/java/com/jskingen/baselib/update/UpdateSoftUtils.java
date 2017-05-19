package com.jskingen.baselib.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.jskingen.baselib.BaseHelp;
import com.jskingen.baselib.R;
import com.jskingen.baselib.network.FileManager;
import com.jskingen.baselib.network.ServiceManager;
import com.jskingen.baselib.network.callBack.OnDownLoadCallback;
import com.jskingen.baselib.network.callBack.OnResponseCallback;
import com.jskingen.baselib.network.exception.DjException;
import com.jskingen.baselib.update.model.UpdateBean;
import com.jskingen.baselib.utils.AppUtil;
import com.jskingen.baselib.utils.FileUtils;
import com.jskingen.baselib.utils.ToastUtils;

import java.io.File;

/**
 * Created by ChneY on 2017/4/13.
 */

public class UpdateSoftUtils {
    private UpdateService service;
    private AlertDialog promptDialog;

    private Context context;
    private boolean isShowToast = false;//是否显示Toast
    private String downloadUrl = ""; //下载地址
    private String updataUrl = BaseHelp.getInstance().getConfiguration().baseUrl + "down/meters_android_version.txt";//下载地址
    private boolean isWifiUpdata = false;

    private String apkName = "apk";

    private UpdateSoftUtils(Context context) {
        this.context = context;
    }

    /**
     * 版本对比
     */
    private void compareVersion() {
        if (service == null)
            service = ServiceManager.create(UpdateService.class);

        service.update(updataUrl).enqueue(new OnResponseCallback<UpdateBean>() {
            @Override
            public void onSuccess(UpdateBean updateBean) {
                if (checkUpdata(updateBean.getCODE())) {
//                if (!TextUtils.equals(AppUtil.getVersionName(context), updateBean.getCODE())) {
                    //有更新
                    apkName = context.getString(R.string.app_name) + "_v" + updateBean.getCODE();
                    showPromptDialog(updateBean.getMSG());
                } else if (isShowToast) {
                    ToastUtils.show("已是最新版本");
                }
            }

            @Override
            public void onError(DjException e) {

            }
        });
    }

    /**
     * 下载
     */
    private void download(String fileName) {
        final File file = new File(FileUtils.getDownloadFile(context), fileName + ".apk");
//        final File tempfile;
//        try {
//            tempfile = File.createTempFile(fileName, ".apk", FileUtils.getDownloadFile(context));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (file.exists()) {
//            installApk(context, file);
            file.delete();
            return;
        }

//        installApk(context, file);
        FileManager.download(downloadUrl, new OnDownLoadCallback(file.getPath(), true) {
            @Override
            public void onLoading(int per, long fileSizeDownloaded, long fileSize) {

            }

            @Override
            public void onFinish(boolean b) {
                if (b) {
//                    if (file.exists())
//                        file.delete();
//                    tempfile.renameTo(file);
                    installApk(context, file);
                } else
                    ToastUtils.show("下载失败");
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.show("下载失败");
            }
        });
    }

    //安装apk
    private void installApk(Context context, File apkFile) {
        if (!apkFile.exists()) {
            return;
        }
        if (Build.VERSION.SDK_INT < 24) {//24 android 7.0
            Intent i = new Intent("android.intent.action.VIEW");
            i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
            context.startActivity(i);
        } else {
//            Uri imageUri = FileProvider.getUriForFile(context, App.getInstance().getPackageName(), apkFile);
            Uri imageUri = FileUtils.getUri2File(context, apkFile);
            // 通过Intent安装APK文件
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.setDataAndType(imageUri, "application/vnd.android.package-archive");
            context.startActivity(i);
        }
    }

    /**
     * 更新提示弹出框
     */
    private void showPromptDialog(String message) {
//        if (isWifiUpdata)
//            return;
        if (promptDialog == null) {
            promptDialog = new AlertDialog.Builder(context)
                    .setTitle("更新提示")
//                    .setView(editText)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("下载", null)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            promptDialog.dismiss();
                        }
                    }).create();
        }
        promptDialog.show();
        promptDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(apkName);
                promptDialog.dismiss();
            }
        });
    }

    /**
     * 检测 网络版本 对比
     *
     * @param NetCode
     * @return
     */
    private boolean checkUpdata(String NetCode) {
        String AppCode = AppUtil.getVersionName(context);
        try {
            return Float.parseFloat(NetCode) > Float.parseFloat(AppCode);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class Builder {
        private UpdateSoftUtils updateSoftUtils;

        public Builder(@NonNull Context context) {
            updateSoftUtils = new UpdateSoftUtils(context);
        }

        public Builder promptDialog(AlertDialog promptDialog) {
            if (promptDialog != null)
                updateSoftUtils.promptDialog = promptDialog;
            return this;
        }

        public Builder isWifiUpdata(boolean isWifiUpdata) {
            updateSoftUtils.isWifiUpdata = isWifiUpdata;
            return this;
        }

        public Builder showToast(boolean showToast) {
            updateSoftUtils.isShowToast = showToast;
            return this;
        }

        public Builder downloadUrl(String downloadUrl) {
            updateSoftUtils.downloadUrl = downloadUrl;
            return this;
        }

        public Builder updataUrl(String updataUrl) {
            updateSoftUtils.updataUrl = updataUrl;
            return this;
        }

        public void start() {
            if (TextUtils.isEmpty(updateSoftUtils.downloadUrl))
                throw new NullPointerException("The download url is empty and cannot be updated");
            updateSoftUtils.compareVersion();
        }

    }
}
