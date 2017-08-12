package yin.style.notes.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jskingen.baselib.activity.base.TitleActivity;
import com.jskingen.baselib.fragment.RecyclerViewFragment;
import com.jskingen.baselib.fragment.TitleFragment;

import yin.style.notes.R;

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
}
