package yin.style.sample.baseActivity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import yin.style.baselib.activity.base.ViewPagerActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.view.design.XTabLayout;
import yin.style.sample.R;
import yin.style.sample.baseActivity.fragment.TabFragment;

public class mViewPagerActivity extends ViewPagerActivity {

    private List<TabFragment> list = new ArrayList<>();

    @Override
    protected int getViewByXml() {
        return R.layout.activity_m_view_pager;
    }

    //    @Override
    protected void findView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (XTabLayout) findViewById(R.id.tablayout);

//        mTabLayout.setTabTextSize(10);
        mTabLayout.setTabMode(XTabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabLineOffset(40);
        mTabLayout.setMinWidth(true);
        mTabLayout.setTabPadding(0, 0, 0, 0);
        mTabLayout.setSelectedTabIndicatorHeight(8);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.text_grey), getResources().getColor(R.color.text_black));
    }

    @Override
    protected CharSequence[] setTitles() {
        return new String[]{"页面1页面", "页面2"};
    }

    @Override
    protected List<? extends Fragment> setFragments() {
        list = new ArrayList<>();

        list.add(new TabFragment().newInstance(0));
        list.add(new TabFragment().newInstance(1));

        return list;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void setTitle(TitleLayout titleLayout) {
        title.setText("mViewPager");
    }
}
