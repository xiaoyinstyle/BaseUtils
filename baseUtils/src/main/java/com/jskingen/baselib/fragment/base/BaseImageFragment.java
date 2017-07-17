package com.jskingen.baselib.fragment.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jskingen.baselib.R;
import com.jskingen.baselib.fragment.NormalFragment;
import com.jskingen.baselib.fragment.inter.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

public class BaseImageFragment extends NormalFragment {
    private ViewPager viewPager;
    private SimpleFragmentAdapter adapter;
    private List<String> lists = new ArrayList<>();
    private boolean touchFinish = true;
    private int currentItem = 0;

    private OnPageChangeListener listener;

    @Override
    protected int getViewByXml() {
        return R.layout.activity_image_base;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        adapter = new SimpleFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (listener != null)
                    listener.onChange(position, lists.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.setCurrentItem(currentItem);
        if (listener != null)
            listener.onChange(currentItem, lists.size());
    }

    @Override
    protected void initData() {
    }

    /**
     * 点击关闭
     */
    public void setTouchFinish(boolean touchFinish) {
        this.touchFinish = touchFinish;
    }

    public void setListener(OnPageChangeListener listener) {
        this.listener = listener;
    }

    /**
     *
     */
    public void setData(List<String> newlist, int currentItem) {
        lists.clear();
        lists.addAll(newlist);

        this.currentItem = currentItem;
    }


    public class SimpleFragmentAdapter extends FragmentPagerAdapter {

        public SimpleFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String path = lists.get(position);
            ImageviewFragment fragment = ImageviewFragment.getInstance(path, touchFinish);
            return fragment;
        }

        @Override
        public int getCount() {
            return lists.size();
        }
    }
}
