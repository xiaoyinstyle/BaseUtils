package com.jskingen.baselib.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.jskingen.baselib.R;
import com.jskingen.baselib.activity.base.NormalAcitivity;
import com.jskingen.baselib.utils.ScreenUtil;
import com.jskingen.baselib.utils.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChneY on 2017/5/6.
 */

public abstract class NormalFragment extends Fragment {
    private Unbinder unbinder;
    protected Activity mContext;
    protected LinearLayout rootView;
    protected View titleView;

    private boolean hasLoad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        //动态加载content
        rootView = (LinearLayout) inflater.inflate(R.layout.base_activity, container, false);
        addTitleLayout(rootView);//加载Title布局
        setStatusView();//沉浸式

        View view = View.inflate(getContext(), getViewByXml(), null);
        rootView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        unbinder = ButterKnife.bind(this, rootView);

        initView(savedInstanceState);   //初始化布局

        if (!setLazy()) {
            initData(); //设置数据
        }
        if (setLazy() && getUserVisibleHint()) {
            initData(); //设置数据
        }
        return rootView;
    }

    protected void addTitleLayout(LinearLayout rootView) {
        titleView = View.inflate(mContext, R.layout.base_title, null);
        rootView.addView(titleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 设置沉浸式
     */
    private void setStatusView() {
        if (mContext instanceof NormalAcitivity) {
            ((NormalAcitivity) mContext).hideStatusView();
            showStatusView();
        }
    }

    protected abstract int getViewByXml();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @Nullable
    public View findViewById(@IdRes int id) {
        if (id <= 0) {
            return null;
        }
        return rootView.findViewById(id);
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

    //关闭键盘
    protected void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mContext.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 沉浸式 隐藏
     */
    public void hideStatusView() {

        if (titleView == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompat.compat(mContext, Color.TRANSPARENT);
            titleView.setPadding(0, 0, 0, 0);
        }
    }

    /**
     * 沉浸式 显示
     */
    public void showStatusView() {
        if (titleView == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompat.compat(mContext, getResources().getColor(R.color.colorPrimaryDark));
            titleView.setPadding(0, ScreenUtil.getStatusHeight(mContext), 0, 0);
        }
    }
}