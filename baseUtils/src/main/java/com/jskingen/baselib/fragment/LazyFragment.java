package com.jskingen.baselib.fragment;


import android.support.v4.app.Fragment;

import com.jskingen.baselib.utils.LogUtils;

/**
 * Created by Chne on 2017/11/20.
 */

public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;
    protected boolean hasLoad;

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            LogUtils.e("AAA", "setUserVisibleHint--" + isVisibleToUser);
            if (!hasLoad) {
                hasLoad = true;
                lazyLoad();
            }
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (!hasLoad) {
                hasLoad = true;
                lazyLoad();
            }
            LogUtils.e("AAA", "onHiddenChanged--" + hidden);
        }
    }
}
