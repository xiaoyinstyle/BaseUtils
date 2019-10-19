package yin.style.baselib.activity.adapter;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ChneY on 2017/4/22.
 * <p>
 * ViewPage 的 View 适配器
 */
public class ViewAdapter extends PagerAdapter {
    private List<View> mViewList;
    private List<String> mTitleList;

    public ViewAdapter(List<View> mViewList, List<String> mTitleList) {
        this.mViewList = mViewList;
        this.mTitleList = mTitleList;
    }

    public ViewAdapter(List<View> mViewList) {
        this.mViewList = mViewList;
        this.mTitleList = null;
    }

    @Override
    public int getCount() {
        return mViewList.size();//页卡数
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;//官方推荐写法
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));//添加页卡
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));//删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (mTitleList == null || mTitleList.size() == 0) ? "" : mTitleList.get(position);//页卡标题
    }

}
