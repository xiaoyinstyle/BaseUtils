package com.cretin.www.cretinautoupdatelibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.www.cretinautoupdatelibrary.interfaces.ForceExitCallBack;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateInterface;
import com.cretin.www.cretinautoupdatelibrary.utils.DownloadReceiver;
import com.cretin.www.cretinautoupdatelibrary.utils.DownloadService;
import com.cretin.www.cretinautoupdatelibrary.utils.NetWorkUtils;
import com.cretin.www.cretinautoupdatelibrary.view.ProgressView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cretin on 2017/3/13.
 */

public class AutoUpdateUtils {
    //广播接受者
    private static MyReceiver receiver;
    private static AutoUpdateUtils cretinAutoUpdateUtils;

    //定义一个展示下载进度的进度条
    private ProgressDialog progressDialog;

    private Context mContext;

    private ForceExitCallBack forceCallBack;

    //展示下载进度的方式 对话框模式 通知栏进度条模式
    private int showType = Builder.TYPE_DIALOG;
    //是否展示忽略此版本的选项 默认开启
    private boolean canIgnoreThisVersion = true;
    //app图标
    private int iconRes;
    //appName
    private String appName;

    //自定义对话框的所有控件的引用
    private AlertDialog showAndDownDialog;
    private AlertDialog showAndBackDownDialog;

    //绿色可爱型
    private TextView showAndDownTvMsg;
    private TextView showAndDownTvBtn1;
    private TextView showAndDownTvBtn2;
    private TextView showAndDownTvTitle;
    private LinearLayout showAndDownLlProgress;
    private ImageView showAndDownIvClose;
    private ProgressView showAndDownUpdateProView;

    //前台展示后台下载
    private TextView showAndBackDownMsg;
    private ImageView showAndBackDownClose;
    private TextView showAndBackDownUpdate;

    //私有化构造方法
    private AutoUpdateUtils(Context context) {
        mContext = context;
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_RECEIVER");
        //注册
        mContext.registerReceiver(receiver, filter);
    }

    /**
     * 检查更新
     */
    public void check(UpdateInterface data) {
        check(data, null);
    }

    public void check(UpdateInterface data, ForceExitCallBack forceCallBack) {
        cretinAutoUpdateUtils.forceCallBack = forceCallBack;
        if (data == null) {
            throw new RuntimeException("checkUrl is null. You must call init before using the cretin checking library.");
        } else {
            if (checkVersion(data)) {
                if (data.getIsForceUpdates()) {
                    //所有旧版本强制更新
                    showUpdateDialog(data, true, false);
                } else {
                    showUpdateDialog(data, false, true);
                }
            }
        }
    }

    /**
     * 进行版本比较
     *
     * @param data
     */
    private boolean checkVersion(UpdateInterface data) {
//        if (checkVersionName) {
//            //通过版本Name来判断 ，不相同 则进行更新
//            if (!serverVersionName.equals(localVersionName))
//                return true;
//        } else {
        //通过版本Code来判断 ，大于Code的 则进行更新
        if (data.getVersionCodes() > getVersionCode(cretinAutoUpdateUtils.mContext))
            return true;
//        }
        return false;
    }

    /**
     * getInstance()
     *
     * @param builder
     * @return
     */

    public static AutoUpdateUtils getInstance(Builder builder) {
        if (cretinAutoUpdateUtils == null) {
            cretinAutoUpdateUtils = new AutoUpdateUtils(builder.context);
        }
        return cretinAutoUpdateUtils;
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
        if (cretinAutoUpdateUtils != null) {
            if (mContext != null && intent != null)
                mContext.stopService(intent);
            if (mContext != null && receiver != null)
                mContext.unregisterReceiver(receiver);
        }
    }

    /**
     * 显示更新对话框
     *
     * @param data
     */
    private void showUpdateDialog(final UpdateInterface data, final boolean isForceUpdate, boolean showIgnore) {
        if (showType == Builder.TYPE_DIALOG || showType == Builder.TYPE_NOTIFICATION) {
            //简约式对话框展示对话信息的方式
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            AlertDialog alertDialog = builder.create();
            String updateLog = data.getUpdateLogs();
            if (TextUtils.isEmpty(updateLog))
                updateLog = "新版本，欢迎更新";
            String versionName = data.getVersionNames();
            if (TextUtils.isEmpty(versionName)) {
                versionName = "1.1";
            }
            alertDialog.setTitle("新版本v" + versionName);
            alertDialog.setMessage(updateLog);
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isForceUpdate) {
                        if (forceCallBack != null)
                            forceCallBack.exit();
                    }
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startUpdate(data);
                }
            });
            if (canIgnoreThisVersion && showIgnore) {
                final String finalVersionName = versionName;
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "忽略此版本", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //忽略此版本
                        List listCodes = loadArray();
                        if (listCodes != null) {
                            listCodes.add(finalVersionName);
                        } else {
                            listCodes = new ArrayList();
                            listCodes.add(finalVersionName);
                        }
                        saveArray(listCodes);
                        Toast.makeText(mContext, "此版本已忽略", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (isForceUpdate) {
                alertDialog.setCancelable(false);
            }
            alertDialog.show();
            ((TextView) alertDialog.findViewById(android.R.id.message)).setLineSpacing(5, 1);
            Button btnPositive =
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button btnNegative =
                    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            btnNegative.setTextColor(Color.parseColor("#16b2f5"));
            if (canIgnoreThisVersion && showIgnore) {
                Button btnNeutral =
                        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                btnNeutral.setTextColor(Color.parseColor("#16b2f5"));
            }
            btnPositive.setTextColor(Color.parseColor("#16b2f5"));
        } else if (showType == Builder.TYPE_DIALOG_WITH_PROGRESS) {
            //在一个对话框中展示信息和下载进度
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.dialog);
            View view = View.inflate(mContext, R.layout.download_dialog, null);
            builder.setView(view);
            showAndDownTvBtn1 = (TextView) view.findViewById(R.id.tv_btn1);
            showAndDownTvBtn2 = (TextView) view.findViewById(R.id.tv_btn2);
            showAndDownTvTitle = (TextView) view.findViewById(R.id.tv_title);
            showAndDownTvMsg = (TextView) view.findViewById(R.id.tv_msg);
            showAndDownIvClose = (ImageView) view.findViewById(R.id.iv_close);
            showAndDownLlProgress = (LinearLayout) view.findViewById(R.id.ll_progress);
            showAndDownUpdateProView = (ProgressView) showAndDownLlProgress.findViewById(R.id.progressView);
            String updateLog = data.getUpdateLogs();
            if (TextUtils.isEmpty(updateLog))
                updateLog = "新版本，欢迎更新";
            showAndDownTvMsg.setText(updateLog);
            showAndDownDialog = builder.show();

            showAndDownTvBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String btnStr = showAndDownTvBtn2.getText().toString();
                    if (btnStr.equals("立即更新")) {
                        //点更新
                        showAndDownTvMsg.setVisibility(View.GONE);
                        showAndDownLlProgress.setVisibility(View.VISIBLE);
                        showAndDownTvTitle.setText("正在更新...");
                        showAndDownTvBtn2.setText("取消更新");
                        showAndDownTvBtn1.setText("隐藏窗口");
                        showAndDownIvClose.setVisibility(View.GONE);
                        startUpdate(data);
                    } else {
                        //点取消更新
                        showAndDownDialog.dismiss();
                        //取消更新 ？
                        destroy(mContext);
                    }
                }
            });

            showAndDownTvBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String btnStr = showAndDownTvBtn1.getText().toString();
                    if (btnStr.equals("下次再说") || btnStr.equals("退出")) {
                        //点下次再说
                        if (isForceUpdate) {
                            if (forceCallBack != null)
                                forceCallBack.exit();
                        } else {
                            showAndDownDialog.dismiss();
                        }
                    } else if (btnStr.equals("隐藏窗口")) {
                        //点隐藏窗口
                        showAndDownDialog.dismiss();
                    }
                }
            });

            showAndDownIvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //关闭按钮
                    showAndDownDialog.dismiss();
                    if (isForceUpdate) {
                        if (forceCallBack != null)
                            forceCallBack.exit();
                    }
                }
            });

            if (isForceUpdate) {
                //强制更新
                showAndDownTvBtn1.setText("退出");
            }
        } else if (showType == Builder.TYPE_DIALOG_WITH_BACK_DOWN) {
            //前台展示 后台下载
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.dialog);
            View view = View.inflate(mContext, R.layout.download_dialog_super, null);
            builder.setView(view);
            showAndBackDownMsg = (TextView) view.findViewById(R.id.tv_content);
            showAndBackDownClose = (ImageView) view.findViewById(R.id.iv_close);
            showAndBackDownUpdate = (TextView) view.findViewById(R.id.tv_update);
            String updateLog = data.getUpdateLogs();
            if (TextUtils.isEmpty(updateLog))
                updateLog = "新版本，欢迎更新";
            showAndBackDownMsg.setText(updateLog);
            showAndBackDownDialog = builder.show();

            showAndBackDownUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点更新
                    startUpdate(data);
                    showAndBackDownDialog.dismiss();
                }
            });

            showAndBackDownClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAndBackDownDialog.dismiss();
                    if (isForceUpdate) {
                        if (forceCallBack != null)
                            forceCallBack.exit();
                    }
                }
            });
        }
    }


    private static Intent intent;

    //创建文件并下载文件
    private void createFileAndDownload(File file, String downurl) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.createNewFile()) {
                Toast.makeText(mContext, "文件创建失败", Toast.LENGTH_SHORT).show();
            } else {
                //文件创建成功
                intent = new Intent(mContext, DownloadService.class);
                intent.putExtra("downUrl", downurl);
                intent.putExtra("appName", mContext.getString(R.string.app_name));
                intent.putExtra("type", showType);
                if (iconRes != 0)
                    intent.putExtra("icRes", iconRes);
                mContext.startService(intent);

                //显示dialog
                if (showType == Builder.TYPE_DIALOG) {
                    progressDialog = new ProgressDialog(mContext);
                    if (iconRes != 0) {
                        progressDialog.setIcon(iconRes);
                    } else {
                        progressDialog.setIcon(R.mipmap.ic_launcher1);
                    }
                    progressDialog.setTitle("正在更新...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条对话框//样式（水平，旋转）
                    //进度最大值
                    progressDialog.setMax(100);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始更新操作
     */
    public void startUpdate(final UpdateInterface data) {
        if (data != null) {
            if (!TextUtils.isEmpty(data.getDownUrls())) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    try {
                        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        final String fileName = filePath + "/" + getPackgeName(mContext) + "-v" + getVersionName(mContext) + ".apk";
                        final File file = new File(fileName);
                        //如果不存在
                        if (data.getApkSizes() > 0 && file.exists() && file.length() == data.getApkSizes()) {
                            installApkFile(mContext, file);
                            showAndDownDialog.dismiss();
                            return;
                        } else {
                            if (!NetWorkUtils.getCurrentNetType(mContext).equals("wifi")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("提示");
                                builder.setMessage("当前处于非WIFI连接，是否继续？");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        file.delete();
                                        createFileAndDownload(file, data.getDownUrls());
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showAndDownLlProgress.setVisibility(View.GONE);
                                        showAndDownTvMsg.setVisibility(View.VISIBLE);
                                        showAndDownTvBtn2.setText("立即更新");
                                        showAndDownTvBtn1.setText("下次再说");
                                        showAndDownTvTitle.setText("发现新版本...");
                                        showAndDownIvClose.setVisibility(View.VISIBLE);
                                    }
                                });
                                builder.show();
                            } else {
                                file.delete();
                                createFileAndDownload(file, data.getDownUrls());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "没有挂载的SD卡", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "下载路径为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 广播接收器
     *
     * @author user
     */
    private static class MyReceiver extends DownloadReceiver {
        @Override
        protected void downloadComplete() {
            if (cretinAutoUpdateUtils != null) {
                if (cretinAutoUpdateUtils.progressDialog != null)
                    cretinAutoUpdateUtils.progressDialog.dismiss();
                if (cretinAutoUpdateUtils.showAndDownDialog != null)
                    cretinAutoUpdateUtils.showAndDownDialog.dismiss();
                try {
                    if (cretinAutoUpdateUtils.mContext != null && intent != null)
                        cretinAutoUpdateUtils.mContext.stopService(intent);
                    if (cretinAutoUpdateUtils.mContext != null && cretinAutoUpdateUtils.receiver != null)
                        cretinAutoUpdateUtils.mContext.unregisterReceiver(cretinAutoUpdateUtils.receiver);
                } catch (Exception e) {
                }
            }
        }

        @Override
        protected void downloading(int progress) {
            if (cretinAutoUpdateUtils != null) {
                if (cretinAutoUpdateUtils.showType == Builder.TYPE_DIALOG) {
                    if (cretinAutoUpdateUtils.progressDialog != null)
                        cretinAutoUpdateUtils.progressDialog.setProgress(progress);
                } else if (cretinAutoUpdateUtils.showType == Builder.TYPE_DIALOG_WITH_PROGRESS) {
                    if (cretinAutoUpdateUtils.showAndDownUpdateProView != null)
                        cretinAutoUpdateUtils.showAndDownUpdateProView.setProgress(progress);
                }
            }
        }

        @Override
        protected void downloadFail(String e) {
            if (cretinAutoUpdateUtils != null) {
                if (cretinAutoUpdateUtils.progressDialog != null)
                    cretinAutoUpdateUtils.progressDialog.dismiss();
                Toast.makeText(cretinAutoUpdateUtils.mContext, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 安装app
     *
     * @param context
     * @param file
     */
    public static void installApkFile(Context context, File file) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            intent1.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent1.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (context.getPackageManager().queryIntentActivities(intent1, 0).size() > 0) {
            context.startActivity(intent1);
        }
    }

    /**
     * 获得apkPackgeName
     *
     * @param context
     * @return
     */
    public String getPackgeName(Context context) {
        String packName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            packName = packInfo.packageName;
        }
        return packName;
    }

    private String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    /**
     * 获得apk版本号
     *
     * @param context
     * @return
     */
    public int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionCode = packInfo.versionCode;
        }
        return versionCode;
    }


    /**
     * 获得apkinfo
     *
     * @param context
     * @return
     */
    public PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    //建造者模式
    public static final class Builder {
        private int showType = TYPE_DIALOG;
        //是否显示忽略此版本 true 是 false 否
        private boolean canIgnoreThisVersion = true;
        //在通知栏显示进度
        public static final int TYPE_NOTIFICATION = 1;
        //对话框显示进度
        public static final int TYPE_DIALOG = 2;
        //对话框展示提示和下载进度
        public static final int TYPE_DIALOG_WITH_PROGRESS = 3;
        //对话框展示提示后台下载
        public static final int TYPE_DIALOG_WITH_BACK_DOWN = 4;

        //显示的app资源图
        private int iconRes;
        //显示的app名
        private String appName;
        //显示log日志
        private boolean showLog;
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public final AutoUpdateUtils.Builder showLog(boolean showLog) {
            this.showLog = showLog;
            return this;
        }

        public final AutoUpdateUtils.Builder setShowType(int showType) {
            this.showType = showType;
            return this;
        }

        public final AutoUpdateUtils.Builder setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public final AutoUpdateUtils.Builder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public final AutoUpdateUtils.Builder setIgnoreThisVersion(boolean canIgnoreThisVersion) {
            this.canIgnoreThisVersion = canIgnoreThisVersion;
            return this;
        }

        public final Builder build() {
            return this;
        }
    }

    public boolean saveArray(List<String> list) {
        SharedPreferences sp = mContext.getSharedPreferences("ingoreList", mContext.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i));
        }
        return mEdit1.commit();
    }

    public List loadArray() {
        List<String> list = new ArrayList<>();
        SharedPreferences mSharedPreference1 = mContext.getSharedPreferences("ingoreList", mContext.MODE_PRIVATE);
        list.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);
        for (int i = 0; i < size; i++) {
            list.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return list;
    }
}
