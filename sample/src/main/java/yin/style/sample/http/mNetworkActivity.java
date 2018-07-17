package yin.style.sample.http;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
//import yin.style.baselib.net.HttpHelper;
//import yin.style.baselib.net.adapter.IObserver;
//import yin.style.baselib.net.inter.ICallBack;
//import yin.style.baselib.net.inter.OnBaseResult;
//import yin.style.baselib.net.inter.OnBitmapResult;
//import yin.style.baselib.net.inter.OnFileResult;
//import yin.style.baselib.net.utils.BHUtils;
import yin.style.baselib.utils.FileUtils;
import yin.style.baselib.rxbus.RxBus;
import yin.style.baselib.utils.ToastUtils;
import yin.style.sample.R;
import yin.style.sample.http.model.TempBean2;

public class mNetworkActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    File temp = null;

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
        temp = FileUtils.getImageFile(mContext, "abc.png");
    }

    @Override
    protected void initData() {

        RxBus.getInstance()
                .toFlowable(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.bt2_get, R.id.bt2_post, R.id.bt2_upload, R.id.bt2_bitmap, R.id.bt2_download, R.id.bt2_rx})
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
                //网络请求，带文件
                http2_upload();
                break;
            case R.id.bt2_bitmap:
                //保存 bitmap
                http2_bitmap();
                break;
            case R.id.bt2_download:
                //下载
                http2_download();
                break;
            case R.id.bt2_rx:
                //RxJava
                http2_rx();
                break;
        }
    }

    private void http2_rx() {
//        Map<String, String> maps = new HashMap();
//        maps.put("mobile", "17625064050");
//        maps.put("password", "123456");
//        HttpHelper.init("http://stage.chengpai.net.cn/doctor/login/login")
//                .post(maps)
//                .subscribe(new IObserver<TempBean2>() {
//
//                    @Override
//                    public void onSuccess(TempBean2 response) {
//                        Log.e(TAG, "onSuccess: " + response);
//                        ToastUtils.show( response.getMessage());
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        Log.e(TAG, "onComplete: 111111111111");
////                        RxBus.getInstance().post("111");
//                    }
//                });

    }

    private void http2_post() {
//        Map<String, String> maps = new HashMap();
//        maps.put("mobile", "17625064050");
//        maps.put("password", "123456");
//        HttpHelper.init("http://stage.chengpai.net.cn/doctor/login/login")
//                .post(maps)
//                .callBack(new ICallBack<String>() {
//
//                    @Override
//                    public void onSuccess(String response, String p) {
//                        ToastUtils.show("网络请求成功");
//                        text.setText(BHUtils.unicodeStringDecode(response));
//                    }
//                });
    }

    private void http2_get() {
//        Map<String, String> maps = new HashMap();
//        maps.put("mobile", "17625064050");
//        maps.put("password", "123456");
//        HttpHelper.init("http://stage.chengpai.net.cn/doctor/login/login")
//                .get(maps)
//                .callBack(new ICallBack<TempBean2>() {
//
//                    @Override
//                    public void onSuccess(TempBean2 response, String p) {
//                        ToastUtils.show("网络请求成功");
////                        text.setText(response.getMessage());
//                    }
//                });
    }

    private void http2_upload() {
//        if (temp == null || !temp.exists()) {
//            ToastUtils.show("请先点击下载按钮");
//            return;
//        }
//
//        Map<String, Object> maps = new HashMap();
//        maps.put("files[0]", temp);
//        HttpHelper.init("http://stage.chengpai.net.cn/rest/upload/uploadAll")
//                .upload(maps)
//                .callBack(new OnBaseResult<String>() {
//                    @Override
//                    public void onSuccess(String response, String p) {
//                        ToastUtils.show("网络请求成功");
//                        text.setText(BHUtils.unicodeStringDecode(response));
//                    }
//
//                    @Override
//                    public void uploadProgress(float progress, long currentSize, long allSize) {
//                        super.uploadProgress(progress, currentSize, allSize);
//                        Log.e(TAG, "uploadProgress: " + progress);
//                    }
//
//                });
    }

    private void http2_download() {
//        HttpHelper.init("https://codeload.github.com/AndroidKnife/RxBus/zip/master")
////        HttpHelper.init("https://img-blog.csdn.net/20170625011730956?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM2MjEwNjk4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast")
//                .callBack(new OnFileResult(temp, true) {
//                    @Override
//                    public void onSuccess(File response, String p) {
//                        ToastUtils.show("网络请求成功");
////                        GlideUtil.getInstance().setView(ivPic, response);
//                    }
//
//                    @Override
//                    public void uploadProgress(float progress, long currentSize, long allSize) {
//                        super.uploadProgress(progress, currentSize, allSize);
//                        Log.e(TAG, "uploadProgress: " + progress);
//                    }
//
//                    @Override
//                    public void downloadProgress(float progress, long currentSize, long allSize) {
//                        text.setText(progress + "");
//                        Log.e(TAG, "downloadProgress: " + progress);
//                        setLoadText(progress + "");
//                    }
//                });
    }

    private void http2_bitmap() {

//        HttpHelper.init("https://img-blog.csdn.net/20170625011730956?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM2MjEwNjk4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast")
//                .callBack(new OnBitmapResult() {
//                    @Override
//                    public void onSuccess(Bitmap response, String p) {
//                        ToastUtils.show("网络请求成功");
//                        ivPic.setImageBitmap(response);
//                    }
//                });
    }


}
