package yin.style.notes.activity;

import com.jskingen.baselib.activity.base.TabActivity;
import com.jskingen.baselib.activity.model.TabEntity;

import java.util.List;

import yin.style.notes.R;
import yin.style.notes.fragment.ProjectsFragment;
import yin.style.notes.fragment.SettingFragment;
import yin.style.notes.fragment.DetailsFragment;

public class MainActivity extends TabActivity {

    @Override
    protected void initData() {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    protected void addFragment(List<TabEntity> tabEntities) {
        ProjectsFragment projectsFragment = new ProjectsFragment();
        TabEntity tab = new TabEntity();
        tab.setMenuIcon(R.mipmap.ic_tab_projects_, R.mipmap.ic_tab_projects_un);
        tab.setTextColor(getResources().getColor(R.color.tabSelect), getResources().getColor(R.color.tabUnSelect));
        tab.setFragment(projectsFragment);
        tab.setTitle("项目");
        tabEntities.add(tab);

        DetailsFragment detailsFragment = new DetailsFragment();
        tab = new TabEntity();
        tab.setSelectedIcon(R.mipmap.ic_tab_details);
        tab.setUnSelectedIcon(R.mipmap.ic_tab_details_un);
        tab.setSelectedColor(getResources().getColor(R.color.tabSelect));
        tab.setUnSelectedColor(getResources().getColor(R.color.tabUnSelect));
        tab.setFragment(detailsFragment);
        tab.setTitle("详情");
        tabEntities.add(tab);

        SettingFragment settingFragment = new SettingFragment();
        tab = new TabEntity();
        tab.setSelectedIcon(R.mipmap.ic_tab_setting_);
        tab.setUnSelectedIcon(R.mipmap.ic_tab_setting_un);
        tab.setSelectedColor(getResources().getColor(R.color.tabSelect));
        tab.setUnSelectedColor(getResources().getColor(R.color.tabUnSelect));
        tab.setFragment(settingFragment);
        tab.setTitle("设置");
        tabEntities.add(tab);
    }
}
