package yin.style.notes.activity;

import android.os.Bundle;
import android.view.View;

import com.jskingen.baselib.activity.base.TitleActivity;

import yin.style.notes.R;

public class AddDetailsActivity extends TitleActivity {

    private int detailsId;

    @Override
    protected void setTitle() {
        detailsId = getIntent().getIntExtra("detailsId", 0);
        if (detailsId == 0)
            title.setText("创建明细");
        else
            title.setText("修改明细");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("保存");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_add_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }
}
