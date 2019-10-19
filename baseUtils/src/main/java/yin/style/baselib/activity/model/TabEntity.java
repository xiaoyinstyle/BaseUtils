package yin.style.baselib.activity.model;

import androidx.annotation.ColorInt;
import androidx.fragment.app.Fragment;

/**
 * TabActivity 的导航条
 * @author Chne
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

    @Deprecated
    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }


    public int getUnSelectedIcon() {
        return unSelectedIcon;
    }

    @Deprecated
    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    @Deprecated
    public void setSelectedColor(@ColorInt int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getUnSelectedColor() {
        return unSelectedColor;
    }

    @Deprecated
    public void setUnSelectedColor(@ColorInt int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setMenuIcon(int selectedIcon, int unSelectedIcon) {
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public void setTextColor(@ColorInt int selectedColor, @ColorInt int unSelectedColor) {
        this.selectedColor = selectedColor;
        this.unSelectedColor = unSelectedColor;
    }
}
