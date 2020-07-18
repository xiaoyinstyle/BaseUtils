package yin.style.sample.layoutManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yin.style.baselib.activity.base.ViewPagerActivity;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.view.CommonViewPage;
import yin.style.baselib.view.design.XTabLayout;
import yin.style.sample.R;

/**
 * 自定义LayoutManager
 */
public class LayoutActivity extends ViewPagerActivity {


    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewPage)
    CommonViewPage viewPage;

    @Override
    protected void findView() {
        mTabLayout = tabLayout;
        mViewPager = viewPage;

        viewPage.setCanScroll(false);
    }

    @Override
    protected CharSequence[] setTitles() {
        return new CharSequence[]{"垂直滚动", "水平滚动", "循环滚动"};
    }

    @Override
    protected List<? extends Fragment> setFragments() {
        return Arrays.asList(
                new LayoutFragment().setFlag(0),
                new LayoutFragment().setFlag(1),
                new LayoutFragment().setFlag(2)
        );
    }

    @Override
    protected void setTitle(TitleLayout titleLayout) {

    }

    @Override
    protected int getViewByXml() {
        return R.layout.activity_layout;
    }

    @Override
    protected void initData() {

    }
}