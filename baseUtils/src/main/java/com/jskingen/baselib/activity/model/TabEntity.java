package com.jskingen.baselib.activity.model;

import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;

/**
 * TabActivity 的导航条
 */
public class TabEntity {
    private String title;
    private int selectedIcon;
    private int unSelectedIcon;
    @ColorInt
    private int selectedColor;
    @ColorInt
    private int unSelectedColor;
    private Fragment fragment;

    public TabEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public int getUnSelectedIcon() {
        return unSelectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(@ColorInt int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getUnSelectedColor() {
        return unSelectedColor;
    }

    public void setUnSelectedColor(@ColorInt int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
