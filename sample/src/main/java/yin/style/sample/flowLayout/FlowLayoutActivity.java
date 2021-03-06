package yin.style.sample.flowLayout;

import android.os.Bundle;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.sample.R;

public class FlowLayoutActivity extends TitleActivity {
    @Override
    protected void setTitle(TitleLayout titleLayout) {
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
