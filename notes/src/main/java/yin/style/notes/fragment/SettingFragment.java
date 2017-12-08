package yin.style.notes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yin.style.baselib.fragment.TitleFragment;
import yin.style.baselib.utils.ToastUtils;

import butterknife.OnClick;
import yin.style.notes.R;
import yin.style.notes.activity.AboutActivity;
import yin.style.notes.activity.ExportHistoryActivity;
import yin.style.notes.activity.LockActivity;

/**
 * 1、数据库的  保存 与恢复 （合并、取新、不保存）
 * 2、九宫格密码（设置、修改）
 */
public class SettingFragment extends TitleFragment {

    @Override
    protected void setTitle() {
        title.setText("设置");
        hiddenBackButton();
    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_set_lock, R.id.ll_set_history, R.id.ll_set_backup, R.id.ll_set_recovery, R.id.ll_set_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_set_lock:
                startActivity(new Intent(mContext, LockActivity.class));
                break;
            case R.id.ll_set_history:
                startActivity(new Intent(mContext, ExportHistoryActivity.class));
                break;
            case R.id.ll_set_backup:
                ToastUtils.show("正在开发..");
                break;
            case R.id.ll_set_recovery:
                ToastUtils.show("正在开发..");
                break;
            case R.id.ll_set_about:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
        }
    }
}
