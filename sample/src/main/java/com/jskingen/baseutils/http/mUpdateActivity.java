package com.jskingen.baseutils.http;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.net.HttpHelper;
import com.jskingen.baselib.net.callback.HttpProcessor;
import com.jskingen.baselib.update.UpdateApkUtils;
import com.jskingen.baselib.update.dailog.NumberProgressDialog;
import com.jskingen.baselib.update.inter.DialogListener;
import com.jskingen.baselib.update.inter.OnUpdateListener;
import com.jskingen.baselib.utils.FileUtils;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;

import butterknife.BindView;
import butterknife.OnClick;

public class mUpdateActivity extends TitleActivity {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;

    String downloadUrl = "";
    String apkPath = "";

    @Override
    protected void setTitle() {
        title.setText("软件更新");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_updata;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        downloadUrl = "http://appcdn.fanyi.baidu.com/app/v7.2.0/app-webbutton-release.apk";
        apkPath = FileUtils.getDownloadFile(this) + "/demo.apk";
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                UpdateApkUtils.from(this)
                        .serverVersionName("2.0")
                        .serverVersionCode(2)
                        .update(new OnUpdateListener() {
                            @Override
                            public void result(boolean mustUpdate) {
                                ToastUtils.show(mustUpdate + "");
                            }
                        });
                break;
            case R.id.bt2:
                UpdateApkUtils.from(this)
                        .serverVersionName("2.0")
                        .serverVersionCode(2)
                        .checkVersionName(false)
                        .systemDownload(downloadUrl, apkPath, checkbox.isChecked())
                        .update();
                break;
            case R.id.bt3:
                UpdateApkUtils.from(this)
                        .serverVersionName("2.0")
                        .serverVersionCode(2)
                        .checkVersionName(false)
                        .browserDownload(downloadUrl, checkbox.isChecked())
                        .update();
                break;
            case R.id.bt4:
                UpdateApkUtils.from(this)
                        .serverVersionName("2.0")
                        .serverVersionCode(2)
                        .checkVersionName(false)
                        .otherDownload(checkbox.isChecked())
                        .update(new OnUpdateListener() {
                            @Override
                            public void result(boolean mustUpdate) {
                                downloadFile();
                            }
                        });
                break;
        }
    }


    NumberProgressDialog dialog;

    private void downloadFile() {
        if (dialog == null) {
            dialog = new NumberProgressDialog(this, new DialogListener() {
                @Override
                public void onclick(int flag) {
                    Log.e("AAA", "onclick:" + flag);
                    //取消按钮
                    HttpHelper.getInstance().cancel(downloadUrl);
                }
            });
        }
        dialog.show();
        HttpHelper.getInstance().downloadFile(downloadUrl, null, apkPath, new HttpProcessor() {
            @Override
            public void onProgress(float per, long fileSizeDownloaded, long fileSize) {
                Log.e("AAA", "per:" + per);
                dialog.setNumberProgress((int) (per * 100));
            }

            @Override
            public void onFinish(boolean success) {
                dialog.dismiss();
                if (success) {
                    ToastUtils.show("x");
                    UpdateApkUtils.installApk(mContext, apkPath);
                }
            }

            @Override
            public void onError(String result) {

            }
        });
    }
}
