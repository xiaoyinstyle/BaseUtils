package yin.style.sample.baseActivity;

import android.webkit.WebView;

import yin.style.baselib.activity.base.WebViewActivity;

public class mWebviewActivity extends WebViewActivity {

    @Override
    protected void setTitle() {
title.setText("WebviewActivity");
    }

    @Override
    protected String getUrl() {
//        return "https://github.com/huanghaibin-dev/CalendarView";
        return "https://www.baidu.com/";
    }

    @Override
    protected void setWebView(WebView webView) {

    }
}
