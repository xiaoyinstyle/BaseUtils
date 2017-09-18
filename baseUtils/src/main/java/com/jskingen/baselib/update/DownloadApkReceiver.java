package com.jskingen.baselib.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.jskingen.baselib.update.model.DownLoadBean;
import com.jskingen.baselib.utils.ToastUtils;

import java.util.ArrayList;


/**
 * 注册 下载监听 广播
 * <action android:name="android.intent.action.DOWNLOAD_COMPLETE" />
 * <action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"/>
 */
public class DownloadApkReceiver extends BroadcastReceiver {
    public static ArrayList<DownLoadBean> downloadList = new ArrayList<>();

    public DownloadApkReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 处理下载完成
        Cursor c = null;

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            for (int i = downloadList.size() - 1; i >= 0; i--) {
                long downloadId = downloadList.get(i).getId();
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                DownloadManager downloadManager = (DownloadManager) context
                        .getSystemService(Context.DOWNLOAD_SERVICE);
                c = downloadManager.query(query);
                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_FAILED) {
                        ToastUtils.show("下载失败");
                        downloadManager.remove(downloadId);
                        downloadList.remove(i);
                    } else if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        ToastUtils.show("下载完成");
                        if (downloadList.get(i).getPath() != null) {
                            UpdateApkUtils.installApk(context, downloadList.get(i).getPath());
                            downloadList.remove(i);
                        }
                    }
                    c.close();
                }
            }
        }
    }
}
