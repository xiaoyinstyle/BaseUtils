package yin.style.notes.activity;

import android.os.Bundle;
import android.view.View;

import com.jskingen.baselib.activity.base.TitleActivity;

import yin.style.notes.R;

public class AddMainActivity extends TitleActivity {

    @Override
    protected void setTitle() {
        title.setText("创建新项目");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("保存");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_add_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }
}
