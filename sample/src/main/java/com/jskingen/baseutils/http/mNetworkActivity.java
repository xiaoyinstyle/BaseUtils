package com.jskingen.baseutils.http;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.net.HttpHelper;
import com.jskingen.baselib.net.callback.OnHttpCallBack;
import com.jskingen.baselib.net.processor.OkHttpProcessor;
import com.jskingen.baselib.network.ServiceManager;
import com.jskingen.baselib.network.callBack.OnResponseCallback;
import com.jskingen.baselib.network.exception.MyException;
import com.jskingen.baselib.network.model.HttpResult;
import com.jskingen.baselib.network.utils.RequestMultipart;
import com.jskingen.baselib.utils.GsonUtils;
import com.jskingen.baselib.utils.ToastUtils;
import com.jskingen.baseutils.Constant;
import com.jskingen.baseutils.R;
import com.jskingen.baseutils.http.api.DemoService;
import com.jskingen.baseutils.http.model.User;
import com.jskingen.baseutils.http.model.UserBean;

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

    private DemoService service;

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
        service = ServiceManager.create(DemoService.class);
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
                http();
                break;
            case R.id.bt_http2:
                //写法二
                http2();
                break;
            case R.id.bt_http_pro:
                //网络请求，带图片
                http_pro();
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
            public void onError(String result) {
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
            public void onError(String result) {
                ToastUtils.show("网络请求失败");
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
            public void onError(String result) {
                ToastUtils.show("网络请求失败-->" + result);
            }
        });
    }

    private void http() {
        Map<String, String> maps = new HashMap();
        maps.put("username", "admin");
        maps.put("password", "123456");

        service.login(maps).enqueue(new OnResponseCallback<HttpResult<User>>(checkbox.isChecked()) {
            //        service.login(maps).enqueue(new OnResponseCallback<HttpResult<User>>() {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                ToastUtils.show("网络请求成功");
                User user = userHttpResult.getData();
                text.setText(user.getUsername());
            }

            @Override
            public void onError(MyException e) {
                ToastUtils.show(e.getDetailMessage());
            }
        });
    }

    private void http2() {
        service.login2("admin", "123456").enqueue(new OnResponseCallback<HttpResult<User>>(checkbox.isChecked()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                ToastUtils.show("网络请求成功");
                User user = userHttpResult.getData();
                text.setText(user.getUsername());
            }

            @Override
            public void onError(MyException e) {
                ToastUtils.show(e.getDetailMessage());
            }
        });
    }

    private void http_pro() {
        File file = new File("");

        RequestBody body = new RequestMultipart()
                .add("file", file)
                .add("project", "project")
                .build();

        service.upload(body).enqueue(new OnResponseCallback<HttpResult>(checkbox.isChecked()) {
            @Override
            public void onSuccess(HttpResult result) {
                ToastUtils.show("网络请求成功");
            }

            @Override
            public void onError(MyException e) {
                ToastUtils.show(e.getDetailMessage() + "");
            }
        });
    }

}
