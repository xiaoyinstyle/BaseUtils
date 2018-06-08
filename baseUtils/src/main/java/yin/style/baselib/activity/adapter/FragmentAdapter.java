package yin.style.baselib.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * ViewPage 的 Fragment 适配器
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<? extends Fragment> mFragments;

    public FragmentAdapter(FragmentManager fm, List<? extends Fragment> fragments) {
        super(fm);
        // TODO Auto-generated constructor stub
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragments.size();
    }
}
