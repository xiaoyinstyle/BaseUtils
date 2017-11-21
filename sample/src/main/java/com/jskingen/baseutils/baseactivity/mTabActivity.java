package com.jskingen.baseutils.baseactivity;


import android.graphics.Color;

import com.jskingen.baselib.activity.base.TabActivity;
import com.jskingen.baselib.activity.model.TabEntity;
import com.jskingen.baseutils.R;

import java.util.List;

public class mTabActivity extends TabActivity {
    private String[] titles = new String[]{"测试一", "测试二", "测试三"};
    private int[] iconUnSelectIds = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private int[] iconSelectIds = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    protected void addFragment(List<TabEntity> tabEntities) {

        for (int i = 0; i < 3; i++) {
            TabEntity tabEntity = new TabEntity();
            tabEntity.setFragment(TabFragment.newInstance(i));
            tabEntity.setTitle(titles[i]);

            tabEntity.setMenuIcon(iconSelectIds[i], iconUnSelectIds[i]);
            tabEntity.setTextColor(Color.parseColor("#01B200"), Color.parseColor("#A9B7B7"));

            tabEntities.add(tabEntity);
        }
    }

    @Override
    protected void initData() {
        setCurrentItem(1);

        setCanScroll(false);
        showPoint(1, true);//显示小红点
        showPoint(2, 3, true);//显示带数字的小红点
    }


}
