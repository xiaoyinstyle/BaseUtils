package com.bangdu.classnotice.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangdu.classnotice.R;
import com.bangdu.classnotice.f1.ClassStyleFragment;
import com.bangdu.classnotice.f1.TodayClassFragment;
import com.bangdu.classnotice.view.TitleClassView;
import com.jskingen.baselib.activity.model.TabEntity;
import com.jskingen.baselib.fragment.NormalFragment;
import com.jskingen.baselib.utils.DpUtil;
import com.jskingen.baselib.view.CommonViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;


/**
 * Created by BangDu on 2017/11/7.
 */

public class ClassSummaryFragment extends NormalFragment {
    private String[] titles = new String[]{" 今日班级", " 班级风采", " 班级相册"};
    private int[] iconSelectIds = new int[]{R.mipmap.ic_f1_jrbj_, R.mipmap.ic_f1_bjfc_, R.mipmap.ic_f1_bjxc_};
    private int[] iconUnSelectIds = new int[]{R.mipmap.ic_f1_jrbj, R.mipmap.ic_f1_bjfc, R.mipmap.ic_f1_bjxc};

    @BindView(R.id.title_class_view)
    TitleClassView titleClassView;
    @BindView(R.id.tl_f1)
    VerticalTabLayout tlF1;
    @BindView(R.id.vp_f1)
    CommonViewPage vpF1;
    @BindView(R.id.tv_f1_1)
    TextView tvF11;
    @BindView(R.id.tv_f1_2)
    TextView tvF12;
    @BindView(R.id.iv_f1_1)
    ImageView ivF11;
    @BindView(R.id.tv_f1_3)
    TextView tvF13;
    @BindView(R.id.tv_f1_4)
    TextView tvF14;

    //Tab标题集合
    protected List<TabEntity> tabEntities = new ArrayList<>();
    protected ViewPagerAdapter adapter;

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_summary;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setFragment();

        tlF1.setTabAdapter(new MyTabAdapter());
        adapter = new ViewPagerAdapter(getChildFragmentManager(), tabEntities);
        vpF1.setAdapter(adapter);
        vpF1.setCanScroll(false);
        tlF1.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                vpF1.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        titleClassView.setText("三年级<40>班");
    }

    private void setFragment() {
        TabEntity tabEntity0 = new TabEntity();
        tabEntity0.setFragment(new TodayClassFragment());
        tabEntity0.setTitle(titles[0]);
        tabEntity0.setSelectedIcon(iconSelectIds[0]);
        tabEntity0.setUnSelectedIcon(iconUnSelectIds[0]);
        tabEntity0.setSelectedColor(Color.BLACK);
        tabEntity0.setUnSelectedColor(Color.BLACK);
        tabEntities.add(tabEntity0);

        TabEntity tabEntity1 = new TabEntity();
//        tabEntity1.setFragment(new ClassStyleFragment());
        tabEntity1.setFragment(new TodayClassFragment());
        tabEntity1.setTitle(titles[1]);
        tabEntity1.setSelectedIcon(iconSelectIds[1]);
        tabEntity1.setUnSelectedIcon(iconUnSelectIds[1]);
        tabEntity1.setSelectedColor(Color.BLACK);
        tabEntity1.setUnSelectedColor(Color.BLACK);
        tabEntities.add(tabEntity1);

        TabEntity tabEntity2 = new TabEntity();
        tabEntity2.setFragment(new TodayClassFragment());
        tabEntity2.setTitle(titles[2]);
        tabEntity2.setSelectedIcon(iconSelectIds[2]);
        tabEntity2.setUnSelectedIcon(iconUnSelectIds[2]);
        tabEntity2.setSelectedColor(Color.BLACK);
        tabEntity2.setUnSelectedColor(Color.BLACK);
        tabEntities.add(tabEntity2);
    }

    @Override
    protected void initData() {

    }

    class MyTabAdapter implements TabAdapter {
        @Override
        public int getCount() {
            return tabEntities.size();
        }

        @Override
        public ITabView.TabBadge getBadge(int position) {
            return null;
        }

        @Override
        public ITabView.TabIcon getIcon(int position) {
            return new TabView.TabIcon.Builder()
                    .setIcon(tabEntities.get(position).getSelectedIcon(), tabEntities.get(position).getUnSelectedIcon())
                    .setIconGravity(Gravity.START)
                    .setIconMargin((int) DpUtil.dp2px(mContext, 5))
                    .setIconSize((int) DpUtil.dp2px(mContext, getResources().getDimension(R.dimen.f1_icon_size)),
                            (int) DpUtil.dp2px(mContext, getResources().getDimension(R.dimen.f1_icon_size)))
                    .build();
        }

        @Override
        public ITabView.TabTitle getTitle(int position) {
            return new TabView.TabTitle.Builder()
                    .setContent(tabEntities.get(position).getTitle())
                    .setTextSize(28)
                    .setTextColor(tabEntities.get(position).getSelectedColor(), tabEntities.get(position).getUnSelectedColor())
                    .build();
        }

        @Override
        public int getBackground(int position) {
            return -1;
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<TabEntity> tabEntities;

        public ViewPagerAdapter(FragmentManager fm, List<TabEntity> titles) {
            super(fm);
            this.tabEntities = titles;
        }

        @Override
        public int getCount() {
            return tabEntities.size();
        }

        @Override
        public Fragment getItem(int position) {
            return tabEntities.get(position).getFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabEntities.get(position).getTitle();
        }
    }

}
