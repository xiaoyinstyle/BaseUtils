package yin.style.notes.activity;

import android.os.Bundle;

import com.jskingen.baselib.activity.base.TitleActivity;

import yin.style.notes.R;

public class AboutActivity extends TitleActivity {

    @Override
    protected int getViewByXml() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle() {
        title.setText("关于应用");
    }
}
