package yin.style.baselib.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import yin.style.baselib.activity.model.TabEntity;

import java.util.List;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * TabActivity 适配器
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    private List<TabEntity> tabEntities;

    /**
     * 构造方法
     */
    public TabAdapter(FragmentManager fm,  List<TabEntity> titles) {
        super(fm);
        this.tabEntities = titles;
    }

    /**
     * 返回显示的Fragment总数
     */
    @Override
    public int getCount() {
        return tabEntities.size();
    }

    /**
     * 返回要显示的Fragment的某个实例
     */
    @Override
    public Fragment getItem(int position) {
        return tabEntities.get(position).getFragment();
    }

    /**
     * 返回每个Tab的标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabEntities.get(position).getTitle();
    }
}