package com.jskingen.baselib.activity.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jskingen.baselib.R;
import com.jskingen.baselib.activity.adapter.TabAdapter;
import com.jskingen.baselib.activity.model.TabEntity;
import com.jskingen.baselib.view.MyViewPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChneY on 2017/4/26.
 * <p>
 * TabActivity
 */

public abstract class TabActivity extends NormalAcitivity {
    protected TabLayout mTabLayout;
    protected TabAdapter adapter;
    //Tab标题集合
    protected List<TabEntity> tabEntities = new ArrayList<>();
    protected MyViewPage mViewPager;

    @Override
    protected int getViewByXml() {
        return R.layout.base_activity_tab;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewPager = (MyViewPage) findViewById(R.id.viewPage);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

        addFragment(tabEntities);

        adapter = new TabAdapter(getSupportFragmentManager(), tabEntities);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.setSelectedTabIndicatorHeight(0);
        for (int i = 0; i < tabEntities.size(); i++) {
            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);
            if (itemTab != null) {
                setTabItem(itemTab, false);
            }
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabItem(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabItem(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (tabEntities.size() > 0) {
            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
            setTabItem(mTabLayout.getTabAt(0), true);
        }
    }

    public void setCurrentItem(int position){
        mViewPager.setCurrentItem(position);
    }
    /**
     * 设置 itemTab
     */
    private void setTabItem(TabLayout.Tab itemTab, boolean isSelected) {
        int position = itemTab.getPosition();
        ViewHolder holder;

        if (null == itemTab.getCustomView()) {
            itemTab = itemTab.setCustomView(R.layout.base_item_tab);
            holder = new ViewHolder();
            holder.text = (TextView) itemTab.getCustomView().findViewById(R.id.tv_name);
            holder.icon = (ImageView) itemTab.getCustomView().findViewById(R.id.iv_img);

            itemTab.setTag(holder);
        } else {
            holder = (ViewHolder) itemTab.getTag();
        }

        holder.text.setText(tabEntities.get(position).getTitle());

        if (isSelected) {
            holder.text.setTextColor(tabEntities.get(position).getSelectedColor());
            holder.icon.setImageResource(tabEntities.get(position).getSelectedIcon());
        } else {
            holder.text.setTextColor(tabEntities.get(position).getUnSelectedColor());
            holder.icon.setImageResource(tabEntities.get(position).getUnSelectedIcon());
        }
    }

    private class ViewHolder {
        private TextView text;
        private ImageView icon;
    }

    /**
     * 显示小红点
     *
     * @param pos
     * @param isVisible
     */
    protected void showPoint(int pos, boolean isVisible) {
        if (null == mTabLayout || pos >= mTabLayout.getTabCount())
            return;
        View view = mTabLayout.getTabAt(pos).getCustomView().findViewById(R.id.v_red_point);
        view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 显示带数字的小红点
     *
     * @param pos
     * @param pos
     * @param number
     * @param isVisible
     */
    protected void showPoint(int pos, int number, boolean isVisible) {
        if (null == mTabLayout || pos >= mTabLayout.getTabCount())
            return;
        TextView textView = (TextView) mTabLayout.getTabAt(pos).getCustomView().findViewById(R.id.tv_red_point);
        textView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        textView.setText((number > 99 ? 99 : number) + "");
    }

    /**
     * @param pos
     * @return 带文字的 小红点
     */
    protected TextView getPointTextView(int pos) {
        if (null == mTabLayout || pos >= mTabLayout.getTabCount())
            return null;
        return (TextView) mTabLayout.getTabAt(pos).getCustomView().findViewById(R.id.tv_red_point);
    }

    /**
     * @param pos
     * @return小红点
     */
    protected View getPointView(int pos) {
        if (null == mTabLayout || pos >= mTabLayout.getTabCount())
            return null;
        return mTabLayout.getTabAt(pos).getCustomView().findViewById(R.id.v_red_point);
    }

    protected abstract void addFragment(List<TabEntity> tabEntities);

//    protected abstract String[] getTitles();
//
//    protected abstract int[] getIconUnselectIds();
//
//    protected abstract int[] getIconSelectIds();
//
//    @ColorInt
//    protected abstract int getTabTextColorSelect();
//
//    @ColorInt
//    protected abstract int getTabTextColorUnSelect();

    /**
     * @param canScroll 是否可以左右滑动
     */
    public void setCanScroll(boolean canScroll) {
        if (mViewPager != null)
            mViewPager.setCanScroll(canScroll);
    }
}