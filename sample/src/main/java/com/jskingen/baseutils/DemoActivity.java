package com.jskingen.baseutils;

import android.os.Bundle;

import com.jskingen.baselib.activity.base.TitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoActivity extends TitleActivity {
    @BindView(R.id.chatView)
    BrokenLineChartView chatView;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_demo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        chatView.drawBrokenLine();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
