package com.jskingen.baseutils.utils;

import android.os.Bundle;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baseutils.R;

public class mButtonActivity extends TitleActivity {

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_button;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title.setText("自定义样式Button4");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle() {

    }
}
