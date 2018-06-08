package yin.style.baselib.fragment;

import android.annotation.SuppressLint;
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

import org.greenrobot.eventbus.EventBus;

import yin.style.baselib.R;
import yin.style.baselib.activity.view.StatusBarView;
import yin.style.baselib.activity.view.TitleLayout;
import yin.style.baselib.utils.ScreenUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import yin.style.baselib.utils.StatusBarUtils;

/**
 * Created by ChneY on 2017/5/6.
 */

public abstract class NormalFragment extends Fragment {
    private Unbinder unbinder;
    protected Activity mContext;
    protected LinearLayout rootView;
    protected StatusBarView statusBarView;

    protected boolean hasLoad;
    protected boolean hasInit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        //动态加载content
        ViewGroup decorView = (ViewGroup) inflater.inflate(R.layout.base_activity, container, false);
        rootView = (LinearLayout) decorView.findViewById(R.id.base_root);
        statusBarView = decorView.findViewById(R.id.base_status_bar);

        addTitleLayout(rootView);//加载Title布局

        View view = View.inflate(getContext(), getViewByXml(), null);
        rootView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        unbinder = ButterKnife.bind(this, rootView);

        hasInit = true;

        if (!setLazy()) {
            init(savedInstanceState); //设置数据
        }
        if (setLazy() && getUserVisibleHint()) {
            init(savedInstanceState); //设置数据
        }
        return rootView;
    }

    //懒加载
    private void init(Bundle savedInstanceState) {
        //默认不加载EventBus
        if (setEventBus())
            EventBus.getDefault().register(this);

        hasLoad = true;
        initView(savedInstanceState);   //初始化布局
        initData(); //设置数据
    }

    protected void addTitleLayout(ViewGroup rootView) {

    }

    protected abstract int getViewByXml();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @SuppressLint("ResourceType")
    @Nullable
    public final View findViewById(@IdRes int id) {
        if (id <= 0) {
            return null;
        }
        return rootView.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //默认不加载EventBus
        if (setEventBus())
            EventBus.getDefault().unregister(this);
    }

    private boolean setEventBus() {
        return false;
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
            if (hasInit && !hasLoad) {
                init(null);
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
    protected final void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mContext.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 设置沉浸式
     */
    public boolean setStatusBarView(Activity activity, boolean isShowStatus) {
        return statusBarView.setStatusBarView(activity, isShowStatus);
    }

    /**
     * 设置沉浸式 字体颜色
     */
    public boolean setStatusBarText(Activity activity, boolean barTextDark) {
        return statusBarView.setStatusBarText(activity, barTextDark);
    }
}