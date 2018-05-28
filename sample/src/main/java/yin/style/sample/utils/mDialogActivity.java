package yin.style.sample.utils;

import android.os.Bundle;
import android.view.View;

import yin.style.baselib.activity.base.TitleActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.DialogUtils;
import yin.style.sample.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class mDialogActivity extends TitleActivity {

    @Override
    protected void setTitle(TitleLayout title) {
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


    @OnClick({R.id.loadingDialog, R.id.loading_ok, R.id.loading_error, R.id.loading_waring, R.id.loading_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loadingDialog:
                DialogUtils.showBlackLoading(this, "loadingDialog");
                break;
            case R.id.loading_ok:
                DialogUtils.showCompleDialog(this, "加载成功");
                break;
            case R.id.loading_error:
                DialogUtils.showErrorDialog(this, "加载失败");
                break;
            case R.id.loading_waring:
                DialogUtils.showWaringDialog(this, "提示信息");
                break;
            case R.id.loading_other:
                DialogUtils.showAutoCancelDialog(this, "提示信息", R.drawable.ic_launcher, 5 * 1000);
                break;
        }
    }
}
