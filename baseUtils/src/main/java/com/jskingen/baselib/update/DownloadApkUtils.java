package com.jskingen.baselib.update;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.jskingen.baselib.update.model.DownLoadBean;
import com.jskingen.baselib.utils.FileUtils;

import java.io.File;

/**
 * Created by ChneY on 2017/8/31.
 */

public class DownloadApkUtils {
    /**
     * 通过浏览器下载APK包
     *
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "浏览器打开失败！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 下载更新apk包
     * 权限:1,<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
     *
     * @param context
     * @param url
     */
    public static void downloadForAutoInstall(Context context, String url, String filePath, String title) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            //在通知栏中显示
            request.setVisibleInDownloadsUi(true);
            request.setTitle(title);

            File fileParent = new File(filePath).getParentFile();
            if (!fileParent.exists() && fileParent.mkdirs())
                return;
            // 若存在，则删除
            File file = new File(filePath);
            file.delete();

            Uri fileUri = Uri.fromFile(new File(filePath));
            request.setDestinationUri(fileUri);
            long downloadUpdateApkId = downloadManager.enqueue(request);
            DownloadApkReceiver.downloadList.add(new DownLoadBean(downloadUpdateApkId, filePath));
        } catch (Exception e) {
            e.printStackTrace();
            downloadForWebView(context, url);
        } finally {
//            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }


}
