package yin.style.notes.activity;


import android.graphics.Color;

import com.jskingen.baselib.activity.base.TabActivity;
import com.jskingen.baselib.activity.model.TabEntity;

import java.util.List;

import yin.style.notes.R;
import yin.style.notes.fragment.ProjectsFragment;
import yin.style.notes.fragment.SettingFragment;
import yin.style.notes.fragment.StaffFragment;

public class MainActivity extends TabActivity {

    @Override
    protected void initData() {

    }

    @Override
    protected void addFragment(List<TabEntity> tabEntities) {
        ProjectsFragment projectsFragment = new ProjectsFragment();
        TabEntity tab1 = new TabEntity();
        tab1.setSelectedIcon(R.drawable.ic_launcher);
        tab1.setUnSelectedIcon(R.drawable.ic_launcher);
        tab1.setSelectedColor(getResources().getColor(R.color.colorPrimary));
        tab1.setUnSelectedColor(getResources().getColor(R.color.colorPrimaryDark));
        tab1.setFragment(projectsFragment);
        tab1.setTitle("项目");
        tabEntities.add(tab1);

        StaffFragment staffFragment = new StaffFragment();
        TabEntity tab2 = new TabEntity();
        tab2.setSelectedIcon(R.drawable.ic_launcher);
        tab2.setUnSelectedIcon(R.drawable.ic_launcher);
        tab2.setSelectedColor(getResources().getColor(R.color.colorPrimary));
        tab2.setUnSelectedColor(getResources().getColor(R.color.colorPrimaryDark));
        tab2.setFragment(staffFragment);
        tab2.setTitle("员工");
        tabEntities.add(tab2);

        SettingFragment settingFragment = new SettingFragment();
        TabEntity tab3 = new TabEntity();
        tab3.setSelectedIcon(R.drawable.ic_launcher);
        tab3.setUnSelectedIcon(R.drawable.ic_launcher);
        tab3.setSelectedColor(getResources().getColor(R.color.colorPrimary));
        tab3.setUnSelectedColor(getResources().getColor(R.color.colorPrimaryDark));
        tab3.setFragment(settingFragment);
        tab3.setTitle("设置");
        tabEntities.add(tab3);
    }
}
