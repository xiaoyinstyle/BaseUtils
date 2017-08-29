package com.jskingen.baseutils.http;

import android.os.Bundle;
import android.widget.CheckBox;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baseutils.R;

import butterknife.BindView;
import butterknife.OnClick;

public class mUpdataActivity extends TitleActivity {

    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_updata;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        checkUpload();
    }

    private void checkUpload() {
    }
}
