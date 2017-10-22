package com.jskingen.baseutils.view;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baseutils.R;

import butterknife.BindView;

public class RadioButtonActivity extends TitleActivity {

    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_radio_button;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rb1.setText("测试按钮");
        rb1.setButtonDrawable(new RoundDrawable("A"));
        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
//        rb1.setText("测试按钮");
//        rb1.setButtonDrawable(new RoundDrawable("A"));


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle() {

    }

}
