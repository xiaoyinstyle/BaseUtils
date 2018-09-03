package yin.style.sample.utilsUI;

import android.os.Bundle;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.sample.R;

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
    protected void setTitle(TitleLayout titleLayout) {

    }
}
