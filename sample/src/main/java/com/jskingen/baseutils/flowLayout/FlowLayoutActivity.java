package com.jskingen.baseutils.flowLayout;

import android.os.Bundle;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baseutils.R;

public class FlowLayoutActivity extends TitleActivity {
    @Override
    protected void setTitle() {
        title.setText("流式布局");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_flowlayout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getSupportFragmentManager().beginTransaction().add(R.id.fm_content, new FlowLayoutFragment(), FlowLayoutFragment.class.getName()).commit();
    }

    @Override
    protected void initData() {

    }
}
