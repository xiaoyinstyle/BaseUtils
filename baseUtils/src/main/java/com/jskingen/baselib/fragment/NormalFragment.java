package com.jskingen.baselib.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jskingen.baselib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChneY on 2017/5/6.
 */

public abstract class NormalFragment extends Fragment {
    private Unbinder unbinder;
    protected View view;
    protected Activity mContext;

    private boolean hasLoad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        //动态加载content
        LinearLayout ll_root = (LinearLayout) inflater.inflate(R.layout.base_activity, container, false);
        addTitleLayout(ll_root);//加载Title布局
        view = View.inflate(getContext(), getViewByXml(), null);
        ll_root.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        unbinder = ButterKnife.bind(this, ll_root);

        initView(savedInstanceState);   //初始化布局
//        LogUtils.e("AAA", "onCreateView--" + getUserVisibleHint());
//        LogUtils.e("AAA", "onCreateView--" + isVisible());

        if (!setLazy()) {
            initData(); //设置数据
        }
        if (setLazy() && getUserVisibleHint()) {
            initData(); //设置数据
        }
        return ll_root;
    }

    protected void addTitleLayout(LinearLayout ll_root) {

    }

    protected abstract int getViewByXml();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @Nullable
    public View findViewById(@IdRes int id) {
        if (id < 0) {
            return null;
        }
        return view.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (setLazy() && getUserVisibleHint()) {
//            LogUtils.e("AAA", "setUserVisibleHint--" + isVisibleToUser);
            if (!hasLoad) {
                hasLoad = true;
                initData();
            }
        }
    }

    /**
     * 默认开启懒加载
     *
     * @return
     */
    protected boolean setLazy() {
        return true;
    }

    ;
}
