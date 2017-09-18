package com.jskingen.baselib.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.jskingen.baselib.update.model.DownLoadBean;
import com.jskingen.baselib.utils.FileUtils;
import com.jskingen.baselib.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 注册
 * <action android:name="android.intent.action.DOWNLOAD_COMPLETE" />
 * <action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"/>
 */
public class UpdateAppReceiver extends BroadcastReceiver {
    public static List<DownLoadBean> downloadList = new ArrayList<>();

    public UpdateAppReceiver() {
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
                    Log.e("AAA", "downloadId:" + downloadId + " status:" + status);
                    if (status == DownloadManager.STATUS_FAILED) {
                        ToastUtils.show("下载失败");
                        downloadManager.remove(downloadId);

                        downloadList.remove(i);
                    } else if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        ToastUtils.show("下载完成");
                        if (downloadList.get(i).getPath() != null) {
                            Intent in = new Intent(Intent.ACTION_VIEW);
                            File apkFile = new File(downloadList.get(i).getPath());
                            if (Build.VERSION.SDK_INT >= 24) {
                                in.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Uri contentUri = FileUtils.getUri2File(context, apkFile);
                                in.setDataAndType(contentUri, "application/vnd.android.package-archive");
                            } else {
                                in.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                            }
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(in);

                            downloadList.remove(i);
                        }
                    }
                    c.close();
                }
            }
        }
    }
}
