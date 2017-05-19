package com.jskingen.baseutils.utils;

import android.os.Bundle;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.view.LoadingDialog;
import com.jskingen.baseutils.R;

import butterknife.OnClick;

public class mDialogActivity extends TitleActivity {

    @Override
    protected void setTitle() {
        title.setText("Dialog");
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_dialog;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.LoadingDialog)
    public void onViewClicked() {
        LoadingDialog progress = new LoadingDialog(this);
        progress.setMessage("6666");
        progress.show();

        new LoadingDialog.Builder(this)
                .setMessage("6666")
                .create()
                .show();
    }
}
