package yin.style.baselib.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import yin.style.baselib.R;

/**
 * Created by ChneY on 2017/5/6.
 */

public abstract class WebViewFragment extends NormalFragment {
    protected WebView webView;
    protected ProgressBar progressBar;

    @Override
    protected int getViewByXml() {
        return R.layout.base_activity_webview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

//        //支持插件
//        webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能

        webView.setWebChromeClient(new WebChromeClient() {//监听网页加载
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    progressBar.setVisibility(View.GONE);
                    setWebViewComplete();
                } else {
                    // 加载中
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }


            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                openFileChooserImplForAndroid4(valueCallback);
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                openFileChooserImplForAndroid4(valueCallback);
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                openFileChooserImplForAndroid4(valueCallback);
            }

            //文件选择
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
                openFileChooserImplForAndroid5(filePathCallback);
                return true;
            }
        });

        setWebView(webView);
    }

    /**
     * 加载完成
     */
    protected void setWebViewComplete() {
    }

    protected void initData() {
        webView.loadUrl(getUrl());
    }

    protected abstract String getUrl();

    protected abstract void setWebView(WebView webView);

    //改写物理按键——返回的逻辑
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                getActivity().finish();
            }
        }
        return getActivity().onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
    }

    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_4 = 3;
    protected ValueCallback<Uri[]> mUploadMessageForAndroid5;
    protected ValueCallback<Uri> mUploadMessageForAndroid4;

    protected void openFileChooserImplForAndroid4(ValueCallback<Uri> uploadMsg) {
        mUploadMessageForAndroid4 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_4);
    }

    protected void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != mContext.RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(null);
            }
            mUploadMessageForAndroid5 = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_4) {
            if (null == mUploadMessageForAndroid4)
                return;
            Uri result = (intent == null || resultCode != mContext.RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid4.onReceiveValue(result);
            } else {
                mUploadMessageForAndroid4.onReceiveValue(null);
            }
            mUploadMessageForAndroid4 = null;
        }
    }

}
