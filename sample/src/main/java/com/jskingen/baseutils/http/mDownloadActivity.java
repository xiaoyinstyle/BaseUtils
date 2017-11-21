package com.jskingen.baseutils.http;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.net.HttpHelper;
import com.jskingen.baselib.net.callback.HttpProcessor;
import com.jskingen.baselib.utils.LogUtils;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.App;
import com.jskingen.baseutils.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class mDownloadActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text)
    TextView text;

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
    }

    @OnClick({R.id.bt2_download, R.id.bt2_upload, R.id.bt2_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt2_download:
                download2();
                break;
            case R.id.bt2_upload:
                upload2();
                break;
            case R.id.bt2_cancel:
                HttpHelper.getInstance().cancel(App.downloadUrl);
                HttpHelper.getInstance().cancel(url);
                break;
            default:
                break;
        }
    }

    //下载
    private void download2() {
        String file = new File(Environment.getExternalStorageDirectory().getPath(), "demo/" + UUID.randomUUID().toString() + ".apk").getAbsolutePath();

        HttpHelper.getInstance().downloadFile(App.downloadUrl, new HashMap<String, String>(), file, new HttpProcessor(checkbox.isChecked()) {

            @Override
            public void onProgress(float per, long current, long total) {
                Log.e("AAA", "per------>" + per);
                text.setText(per + "");
            }

            @Override
            public void onFinish(boolean success) {
                text.setText(success + "");
                Log.e("AAA", "onFinish------>" + success);
            }


            @Override
            public void onError(String e) {
                text.setText("onError------>" + e);
                Log.e("AAA", "onError------>" + e);
            }


        });
    }
    String url = "http://976370887.kinqin.com/php/api/mult.php";

    //上传
    private void upload2() {
        //localhost:8080/upload.do
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "demo/a.apk");
        Map<String, Object> maps = new HashMap();
        maps.put("fname", "admin");
        maps.put("age", "18");
        if (!file.exists()) {
            ToastUtils.show("文件不存在");
            return;
        }
        maps.put("file", file);

        HttpHelper.getInstance().uploadFile(url, maps, new HttpProcessor() {
            @Override
            public void onSuccess(String result) {
                Log.e("AAA", "result------>" + result);

            }

            @Override
            public void onProgress(float per, long current, long total) {
                Log.e("AAA", "per------>" + per);
                text.setText(per + "");
            }

            @Override
            public void onFinish(boolean success) {
                Log.e("AAA", "onFinish------>" + success);
            }

            @Override
            public void onError(String e) {
                Log.e("AAA", "onError------>" + e);
                text.setText("onError------>" + e);
            }
        });
    }


}
