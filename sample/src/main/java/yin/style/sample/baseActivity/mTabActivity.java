package yin.style.sample.baseActivity;


import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.View;

import yin.style.baselib.utils.ToastUtils;
import yin.style.sample.baseActivity.fragment.HeadViewFragment;
import yin.style.baselib.activity.base.TabActivity;
import yin.style.baselib.activity.model.TabEntity;
import yin.style.sample.R;

import yin.style.sample.baseActivity.fragment.TabFragment;

import java.util.List;

public class mTabActivity extends TabActivity {
    private String[] titles = new String[]{"测试一测试一测试一测试一测试一", "", "测试三"};
    private int[] iconUnSelectIds = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private int[] iconSelectIds = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    protected void addFragment(List<TabEntity> tabEntities) {
        setStatusBarView(mContext, false, 0, true);

        TabEntity tabEntity0 = new TabEntity();
        tabEntity0.setFragment(new HeadViewFragment());
        tabEntity0.setTitle(titles[0]);

        tabEntity0.setMenuIcon(iconSelectIds[0], iconUnSelectIds[0]);
        tabEntity0.setTextColor(Color.parseColor("#01B200"), Color.parseColor("#A9B7B7"));

        tabEntities.add(tabEntity0);


        for (int i = 1; i < 3; i++) {
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
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ToastUtils.show("onTabSelected:" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ToastUtils.show("onTabUnselected:" + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                ToastUtils.show("onTabReselected:" + tab.getPosition());
            }
        });

        setCurrentItem(1);

        setCanScroll(false);
        showPoint(1, true);//显示小红点
        showPoint(2, 333, true);//显示带数字的小红点
    }
}
