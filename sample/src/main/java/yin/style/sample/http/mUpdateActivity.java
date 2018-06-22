package yin.style.sample.http;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.cretin.www.cretinautoupdatelibrary.AutoUpdateUtils;
import com.cretin.www.cretinautoupdatelibrary.interfaces.ForceExitCallBack;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.update.UpdateApkUtils;
import yin.style.baselib.update.inter.DialogListener;
import yin.style.baselib.update.inter.OnUpdateListener;
import yin.style.baselib.update.dailog.NumberProgressDialog;
import yin.style.baselib.utils.FileUtils;
import yin.style.sample.R;

import butterknife.BindView;
import butterknife.OnClick;
import yin.style.sample.http.model.VersionBean;

public class mUpdateActivity extends TitleActivity {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;

    String downloadUrl = "";
    String apkPath = "";

    @Override
    protected void setTitle(TitleLayout titleLayout) {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AutoUpdateUtils.destroy(this);
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                VersionBean bean = new VersionBean();
                bean.setCode(2);
                bean.setIs_update(1);
                bean.setUrl("http://appcdn.fanyi.baidu.com/app/v7.2.0/app-webbutton-release.apk");
                bean.setVersion("2.1");
                bean.setVersion_desc("测试更新");

                AutoUpdateUtils.getInstance(new AutoUpdateUtils.Builder(this)
                        .setIgnoreThisVersion(true)
                        .setShowType(AutoUpdateUtils.Builder.TYPE_DIALOG_WITH_BACK_DOWN)
                        .setIconRes(R.mipmap.ic_launcher)
                        .showLog(true)
                        .build()).check(bean, new ForceExitCallBack() {
                    @Override
                    public void exit() {

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
            default:
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
//                    HttpHelper.getInstance().cancel(downloadUrl);
                }
            });
        }
        dialog.show();
//        HttpHelper.getInstance().downloadFile(downloadUrl, null, apkPath, new HttpProcessor() {
//            @Override
//            public void onProgress(float per, long fileSizeDownloaded, long fileSize) {
//                Log.e("AAA", "per:" + per);
//                dialog.setNumberProgress((int) (per * 100));
//            }
//
//            @Override
//            public void onFinish(boolean success) {
//                dialog.dismiss();
//                if (success) {
//                    ToastUtils.show("x");
//                    UpdateApkUtils.installApk(mContext, apkPath);
//                }
//            }
//
//            @Override
//            public void onError(String result) {
//
//            }
//        });
    }
}
