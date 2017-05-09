package com.jskingen.baseutils.baseactivity;


import android.graphics.Color;
import android.support.v4.app.Fragment;

import com.jskingen.baselib.activity.base.TabActivity;
import com.jskingen.baseutils.R;

import java.util.List;

public class mTabActivity extends TabActivity {

    @Override
    protected void addFragment(List<Fragment> mFragments) {
        setCanScroll(false);

        for (int i = 0; i < 3; i++) {
            mFragments.add(TabFragment.newInstance(i));
        }
    }

    @Override
    protected String[] getTitles() {
        return new String[]{"测试一", "测试二", "测试三"};
    }

    @Override
    protected int[] getIconUnselectIds() {
        return new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    }

    @Override
    protected int[] getIconSelectIds() {
        return new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    }

    @Override
    protected int getTabTextColorSelect() {
        return Color.parseColor("#01B200");
    }

    @Override
    protected int getTabTextColorUnSelect() {
        return Color.parseColor("#A9B7B7");
    }

    @Override
    protected void initData() {
        super.initData();

        showPoint(1, true);//显示小红点
        showPoint(2, 3, true);//显示带数字的小红点
    }
}
