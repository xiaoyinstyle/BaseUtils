package yin.style.sample.http;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.ToastUtils;
import yin.style.sample.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mNetworkActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text)
    TextView text;


    @Override
    protected void setTitle(TitleLayout titleLayout) {
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

    @OnClick({R.id.bt2_get, R.id.bt2_post, R.id.bt2_upload, R.id.bt2_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.bt2_test:
                http2_test();
                break;
        }
    }

    private void http2_test() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url("http://www.baidu.com")
                        .get().build();
                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    Log.e("AAA", "" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void http2_post() {
//        HttpHelper.init(new OkHttpProcessor());
        Map<String, String> maps = new HashMap();
        maps.put("fname", "admin");
        maps.put("age", "18");

//        HttpHelper.getInstance().post("http://976370887.kinqin.com/php/api/post.php", maps, new OnHttpCallBack<String>(checkbox.isChecked()) {
//            @Override
//            public void onSuccess(String userHttpResult) {
//                ToastUtils.show("网络请求成功");
////                Map map = (Map) userHttpResult.getData();
//                text.setText(userHttpResult.toString());
//            }
//
//            @Override
//            public void onError(NetException exception) {
//                ToastUtils.show("网络请求失败:" + exception.getMessage());
//            }
//        });
    }

    private void http2_get() {
//        HttpHelper.init(new OkHttpProcessor());
        Map<String, String> maps = new HashMap();
        maps.put("fname", "admin");
        maps.put("age", "18");

//        HttpHelper.getInstance().get("http://976370887.kinqin.com/php/api/get.php", maps, new OnHttpCallBack<String>(checkbox.isChecked()) {
//            @Override
//            public void onSuccess(String userHttpResult) {
//                ToastUtils.show("网络请求成功");
////                Map map = (Map) userHttpResult.getData();
//                text.setText(userHttpResult.toString());
//            }
//
//            @Override
//            public void onError(NetException exception) {
//                ToastUtils.show("网络请求失败-->" + exception.getMessage());
//            }
//        });
    }

    private void http2_upload() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "demo/image.jpg");

//        HttpHelper.init(new OkHttpProcessor());
        Map<String, Object> maps = new HashMap();
        maps.put("fname", "admin");
        maps.put("age", "18");
        if (file.exists())
            maps.put("file", file);
        else
            ToastUtils.show("文件不存在");

//        HttpHelper.getInstance().upload("http://976370887.kinqin.com/php/api/mult.php", maps, new OnHttpCallBack<String>(checkbox.isChecked()) {
//            @Override
//            public void onSuccess(String userHttpResult) {
//                ToastUtils.show("网络请求成功");
//                text.setText(userHttpResult.toString());
//            }
//
//            @Override
//            public void onError(NetException exception) {
//                ToastUtils.show("网络请求失败-->" + exception.getMessage());
//            }
//
//        });
    }
}
