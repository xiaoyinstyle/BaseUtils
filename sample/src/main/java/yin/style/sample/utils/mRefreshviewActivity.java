package yin.style.sample.utils;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import yin.style.sample.utils.view.HeaderView;
import yin.style.baselib.utils.ToastUtils;
import yin.style.baselib.view.refreshView.RefreshLayout;
import yin.style.sample.R;

import java.util.ArrayList;
import java.util.List;

import yin.style.recyclerlib.adapter.BaseQuickAdapter;
import yin.style.recyclerlib.holder.BaseViewHolder;

public class mRefreshviewActivity extends AppCompatActivity {
    RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_refreshview);

        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();
                        ToastUtils.show("6666");
                    }
                }, 3000);
            }
        });
        HeaderView headerView = new HeaderView(this);
        refreshLayout.setRefreshHeader(headerView);

//        addListView();
//        addRecyclerView();
        addWebView();
    }

    private void addWebView() {
//        ScrollView linearLayout = new ScrollView(this);

        WebView webview = new WebView(this);
//        webview.setScrollContainer(false);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("AA", "url" + url);
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                Log.e("AA", "isDialog" + isDialog);
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }
        });
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
//        linearLayout.addView(webview);
        refreshLayout.addView(webview);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://baidu.com");
    }

    private void addRecyclerView() {
        LinearLayout linearLayout = new LinearLayout(this);

        RecyclerView recyclerView = new RecyclerView(this);
        linearLayout.addView(recyclerView);
        refreshLayout.addView(linearLayout);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("No." + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<String>(this, list) {
            @Override
            protected int getLayoutResId() {
                return android.R.layout.simple_list_item_1;
            }

            @Override
            protected void setViewHolder(BaseViewHolder baseViewHolder, String s, int position) {
                baseViewHolder.setText(android.R.id.text1, s);
            }
        });
    }


    private void addListView() {
        ListView listView = new ListView(this);
        refreshLayout.addView(listView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("No." + i);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
