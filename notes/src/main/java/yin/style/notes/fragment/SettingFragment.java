package yin.style.notes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jskingen.baselib.fragment.TitleFragment;

import yin.style.notes.R;
import yin.style.notes.activity.LockActivity;

/**
 * 1、数据库的  保存 与恢复 （合并、取新、不保存）
 * 2、九宫格密码（设置、修改）
 */
public class SettingFragment extends TitleFragment {
    LinearLayout llSetLock;

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
        llSetLock = (LinearLayout) findViewById(R.id.ll_set_lock);
        llSetLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LockActivity.class));

            }
        });
    }

    @Override
    protected void initData() {

    }

}
