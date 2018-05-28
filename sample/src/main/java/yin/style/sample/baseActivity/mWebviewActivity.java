package yin.style.sample.baseActivity;

import android.webkit.WebView;

import yin.style.baselib.activity.base.WebViewActivity;
import yin.style.baselib.activity.view.TitleLayout;

public class mWebviewActivity extends WebViewActivity {

    @Override
    protected void setTitle(TitleLayout titleLayout) {
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
