package yin.style.baselib.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;

import yin.style.baselib.activity.adapter.FragmentAdapter;
import yin.style.baselib.utils.ToastUtils;

/**
 * Created by User on 2018/5/21.
 */

public abstract class ViewPagerActivity extends TitleActivity {
    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;

    protected FragmentAdapter fragmentAdapter;
    protected List fragments;

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragments = setFragments();
        if (fragments == null || fragments.size() == 0) {
            ToastUtils.show("请先调用setFragments（）方法，进行初始化");
            return;
        }

//ViewPager的适配器
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments) {
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return setTitles()[position];
            }
        };

        findView();

        if (mViewPager == null || fragments == null || fragments.size() == 0) {
            ToastUtils.show("请先设置mViewPager与mTabLayout的findViewById()，进行初始化");
            return;
        }

        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());

        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
//        tabLayout.setTabTextColors(getResources().getColor(R.color.text_grey), getResources().getColor(R.color.text_black));
        }
    }

    protected abstract void findView();

    protected abstract CharSequence[] setTitles();

    protected abstract List<? extends Fragment> setFragments();

}
