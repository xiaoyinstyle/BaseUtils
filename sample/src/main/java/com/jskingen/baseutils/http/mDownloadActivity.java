package com.jskingen.baseutils.http;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.net.HttpHelper;
import com.jskingen.baselib.net.callback.HttpProcessor;
import com.jskingen.baselib.net.inter.IHttpProcessor;
import com.jskingen.baselib.network.FileManager;
import com.jskingen.baselib.network.callBack.OnDownLoadCallback;
import com.jskingen.baselib.network.callBack.OnUploadCallback;
import com.jskingen.baselib.utils.LogUtils;
import com.jskingen.baseutils.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Response;

public class mDownloadActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @Override
    protected void setTitle() {
        title.setText("下载与上传");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_download;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        checkbox.setChecked(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果是异步进程，并且返回时需要取消下载，则调用这个方法，取消下载
        FileManager.onCancel();
    }

    @OnClick({R.id.bt2_download, R.id.bt_download, R.id.bt_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_download:
                download();
                break;
            case R.id.bt_upload:
                upload();
                break;

            case R.id.bt2_download:
                download2();
                break;
            case R.id.bt2_upload:
                upload2();
                break;
        }
    }

    //下载
    private void download2() {
        String url = "http://976370887.kinqin.com/php/api/1.apk";
        String file = new File(Environment.getExternalStorageDirectory().getPath(), "demo/a.apk").getAbsolutePath();

        HttpHelper.getInstance().downloadFile(url, new HashMap<String, String>(), file, new HttpProcessor() {

            @Override
            public void onProgress(double per, long current, long total) {
                Log.e("AAA", "per------>" + per);
            }

            @Override
            public void onFinish(boolean success) {

            }
        });
    }

    //上传
    private void upload2() {
        //localhost:8080/upload.do
        String url = "http://976370887.kinqin.com/php/api/mult.php";
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "demo/a.apk");
        Map<String, Object> maps = new HashMap();
        maps.put("file", file);
        HttpHelper.getInstance().uploadFile(url, maps, new HttpProcessor() {
            @Override
            public void onProgress(double per, long current, long total) {
                Log.e("AAA", "per------>" + per);
            }

            @Override
            public void onFinish(boolean success) {

            }
        });
    }

    private void download() {
        String url = "http://p.gdown.baidu.com/f4b25a396162d5cde06b19b4d82f27af4c07423bc926de4431c9ef1d69b06f88b60e6d8dfd2dec478ebe974cf1625724814573cc476eb6df14434356fb562e40ddfd9ab07fbafbb0b0162960091221c864c30dd198705ea91e34a6297c0ee4baba01e5e76dceec4fb9e420d35757d98be3c16189119a8e6df60daa87706ede4411ab2480ab1cd515052821fefec27c74ff1324ca1f0cd484fae93ebcd71585600980f17f123aab8ffeff1ef38569e3c8ce7d2ba59e5795c32ca06a7f117365aec90a05396ddc0b7d52651f73c5d0e63480fe978631423a9c96ebea2adff05c94f1f538b2aaf7a98d53fdc27f43e4fc545696944a24ba03d0e7ad5a10f07d2797551f6e5c169b5e8114cb5297bf41777a55ad61b0162ba7f6";
        String file = new File(Environment.getExternalStorageDirectory().getPath(), "demo/a.apk").getAbsolutePath();
        FileManager.download(url, new OnDownLoadCallback(file, checkbox.isChecked()) {
            @Override
            public void onLoading(int per, long fileSizeDownloaded, long fileSize) {
                LogUtils.e("onLoading", per + "");
            }

            @Override
            public void onFinish(boolean success) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    //上传
    private void upload() {
        //localhost:8080/upload.do
        String url = "http://192.168.0.23:8080/upload.do";
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "demo/a.apk");
        FileManager.upload(url, "file", file, new OnUploadCallback<String>(checkbox.isChecked()) {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
