package com.jskingen.baselib.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jskingen.baselib.activity.model.TabEntity;

import java.util.List;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * TabActivity 适配器
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    public List<Fragment> list;
    private List<TabEntity> titles;

    /**
     * 构造方法
     */
    public TabAdapter(FragmentManager fm, List<Fragment> list, List<TabEntity> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    /**
     * 返回显示的Fragment总数
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 返回要显示的Fragment的某个实例
     */
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    /**
     * 返回每个Tab的标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getTitle();
    }
}