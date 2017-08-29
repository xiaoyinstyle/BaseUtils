package com.jskingen.baseutils.http;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.net.HttpHelper;
import com.jskingen.baselib.net.callback.OnHttpCallBack;
import com.jskingen.baselib.net.exception.NetException;
import com.jskingen.baselib.net.processor.OkHttpProcessor;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.R;
import com.jskingen.baseutils.http.model.User;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class mNetworkActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text)
    TextView text;


    @Override
    protected void setTitle() {
        title.setText("网络请求");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_network;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.bt_http, R.id.bt_http2, R.id.bt_http_pro, R.id.bt2_get, R.id.bt2_post, R.id.bt2_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_http:
                //写法一
                break;
            case R.id.bt_http2:
                //写法二
                break;
            case R.id.bt_http_pro:
                //网络请求，带图片
                break;
            //其他用法参考 retrofit2.0 的用法

            case R.id.bt2_get:
                //网络请求，带图片
                http2_get();
                break;
            case R.id.bt2_post:
                //网络请求，带图片
                http2_post();
                break;
            case R.id.bt2_upload:
                //网络请求，带图片
                http2_upload();
                break;
        }
    }

    private void http2_post() {
        HttpHelper.init(new OkHttpProcessor());
        Map<String, String> maps = new HashMap();
        maps.put("fname", "admin");
        maps.put("age", "18");

        HttpHelper.getInstance().post("http://976370887.kinqin.com/php/api/post.php", maps, new OnHttpCallBack<String>(checkbox.isChecked()) {
            @Override
            public void onSuccess(String userHttpResult) {
                ToastUtils.show("网络请求成功");
//                Map map = (Map) userHttpResult.getData();
                text.setText(userHttpResult.toString());
            }

            @Override
            public void onError(NetException exception) {
                ToastUtils.show("网络请求失败");
            }
        });
    }

    private void http2_get() {
        HttpHelper.init(new OkHttpProcessor());
        Map<String, String> maps = new HashMap();
        maps.put("fname", "admin");
        maps.put("age", "18");

        HttpHelper.getInstance().get("http://976370887.kinqin.com/php/api/get.php", maps, new OnHttpCallBack<String>(checkbox.isChecked()) {
            @Override
            public void onSuccess(String userHttpResult) {
                ToastUtils.show("网络请求成功");
//                Map map = (Map) userHttpResult.getData();
                text.setText(userHttpResult.toString());
            }

            @Override
            public void onError(NetException exception) {
                ToastUtils.show("网络请求失败-->" + exception.getMessage());
            }
        });
    }

    private void http2_upload() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "demo/image.jpg");

        HttpHelper.init(new OkHttpProcessor());
        Map<String, Object> maps = new HashMap();
        maps.put("fname", "admin");
        maps.put("age", "18");
        if (file.exists())
            maps.put("file", file);
        else
            ToastUtils.show("文件不存在");

        HttpHelper.getInstance().upload("http://976370887.kinqin.com/php/api/mult.php", maps, new OnHttpCallBack<String>(checkbox.isChecked()) {
            @Override
            public void onSuccess(String userHttpResult) {
                ToastUtils.show("网络请求成功");
                text.setText(userHttpResult.toString());
            }

            @Override
            public void onError(NetException exception) {
                ToastUtils.show("网络请求失败-->" + exception.getMessage());
            }

        });
    }
}
