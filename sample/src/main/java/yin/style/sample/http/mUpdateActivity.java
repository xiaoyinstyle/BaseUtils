package yin.style.sample.http;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.cretin.www.cretinautoupdatelibrary.AutoUpdateUtils;
import com.cretin.www.cretinautoupdatelibrary.interfaces.ForceExitCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.update.UpdateApkUtils;
import yin.style.baselib.update.inter.DialogListener;
import yin.style.baselib.update.inter.OnUpdateListener;
import yin.style.baselib.update.dailog.NumberProgressDialog;
import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.utils.ToastUtils;
import yin.style.sample.R;

import butterknife.BindView;
import butterknife.OnClick;
import yin.style.sample.http.model.VersionBean;
import yin.style.sample.http.okgo.BaseResult;

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
                                downloadFile(checkbox.isChecked());
                            }
                        });
                break;
            default:
                break;
        }
    }

    NumberProgressDialog dialog;

    private void downloadFile(final boolean isForce) {
        if (dialog == null) {
            dialog = new NumberProgressDialog(this, new DialogListener() {
                @Override
                public void onclick(int flag) {
                    Log.e("AAA", "onclick:" + flag);
                    //取消按钮
//                    HttpHelper.getInstance().cancel(downloadUrl);
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    Log.e(TAG, "onDismiss: ");
                    OkGo.getInstance().cancelTag(mContext);
                }
            });
        }
        dialog.show();

        OkGo.<File>get(downloadUrl)
                .tag(mContext)
                .execute(new BaseResult<File>() {
                    @Override
                    protected void FileCallback(String destFileDir, String destFileName) {
                        super.FileCallback(FileUtils.getDownloadFile(mContext).getPath(), "abc.apk");
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        ToastUtils.show("下载请求成功");
                        Log.e(TAG, "onSuccess: ");
                        UpdateApkUtils.installApk(mContext, apkPath);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        ToastUtils.show("网络请求失败");
                        Log.e(TAG, "onError: " + response.getException().getMessage());
                        if (isForce)
                            finish();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        dialog.setNumberProgress((int) (progress.fraction * 100));
                        Log.e(TAG, "downloadProgress: " + progress.fraction);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dialog.dismiss();
                    }
                });
    }
}
