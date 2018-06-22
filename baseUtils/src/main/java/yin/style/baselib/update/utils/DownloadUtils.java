package yin.style.baselib.update.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import yin.style.baselib.R;

public class DownloadUtils {
    private static MyReceiver receiver;
    private static Intent intent;

    public static void start() {
//        intent = new Intent(mContext, DownloadService.class);
//        intent.putExtra("downUrl", downurl);
//        intent.putExtra("appName", mContext.getString(R.string.app_name));
//        intent.putExtra("type", showType);
//        if (iconRes != 0)
//            intent.putExtra("icRes", iconRes);
//        mContext.startService(intent);
    }

    /**
     * 取消广播的注册
     */
    public static void destroy(Context mContext) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        //不要忘了这一步
        if (mContext != null && intent != null)
            mContext.stopService(intent);
        if (mContext != null && receiver != null)
            mContext.unregisterReceiver(receiver);

    }


    /**
     * 广播接收器
     *
     * @author user
     */
    private static class MyReceiver extends DownloadReceiver {
        @Override
        protected void downloadComplete() {

        }

        @Override
        protected void downloading(int progress) {
//            if (cretinAutoUpdateUtils != null) {
//                if (cretinAutoUpdateUtils.showType == Builder.TYPE_DIALOG) {
//                    if (cretinAutoUpdateUtils.progressDialog != null)
//                        cretinAutoUpdateUtils.progressDialog.setProgress(progress);
//                } else if (cretinAutoUpdateUtils.showType == Builder.TYPE_DIALOG_WITH_PROGRESS) {
//                    if (cretinAutoUpdateUtils.showAndDownUpdateProView != null)
//                        cretinAutoUpdateUtils.showAndDownUpdateProView.setProgress(progress);
//                }
//            }
        }

        @Override
        protected void downloadFail(String e) {
//            if (cretinAutoUpdateUtils.progressDialog != null)
//                cretinAutoUpdateUtils.progressDialog.dismiss();
//            Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }

}
